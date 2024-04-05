package com.ksnk.tiktokdownloader.ui.player

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.base.BaseFragment
import com.ksnk.tiktokdownloader.databinding.FragmentPlayerBinding
import com.ksnk.tiktokdownloader.ui.download.DownloadFragment.Companion.DOWNLOAD_VIDEOS_DIRECTORY
import com.ksnk.tiktokdownloader.ui.share.ShareFragment
import java.io.File


class PlayerFragment : BaseFragment(R.layout.fragment_player) {

    private val viewBinding by viewBinding(FragmentPlayerBinding::bind)
    private var player: ExoPlayer? = null
    private var isAllFabVisible: Boolean = false
    private val deleteResultLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            navigation.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBlackColorInStatusBar()
        with(viewBinding) {
            arguments?.getBoolean(KEY_SHOW_BUTTON, true)?.let {
                if (!it) floatingActionButton.visibility = View.GONE
            }
            arguments?.getString(ShareFragment.KEY_FILE_URI)?.let { videoUri ->
                hideBottomNav()
                player = ExoPlayer.Builder(requireContext()).build()
                playerView.player = player
                player?.setMediaItem(MediaItem.fromUri(videoUri))
                player?.prepare()
                player?.play()
            }

            arguments?.getString(KEY_FILE_NAME)?.let { name ->
                floatingActionButtonShare.setOnClickListener {
                    player?.pause()
                    shareFile(File(DOWNLOAD_VIDEOS_DIRECTORY, name).absolutePath)
                }

                floatingActionButtonDelete.setOnClickListener {
                    val pendingIntent = MediaStore.createDeleteRequest(
                        requireContext().contentResolver,
                        listOf(getImageDeleteUri(requireContext(), File(DOWNLOAD_VIDEOS_DIRECTORY, name).path))
                    )
                    deleteResultLauncher.launch(IntentSenderRequest.Builder(pendingIntent.intentSender).build())
                }
            }

            buttonBack.setOnClickListener {
                navigation.popBackStack()
            }

            floatingActionButton.setOnClickListener {
                isAllFabVisible = if (!isAllFabVisible) {
                    floatingActionButtonShare.show()
                    floatingActionButtonDelete.show()
                    true
                } else {
                    floatingActionButtonShare.hide()
                    floatingActionButtonDelete.hide()
                    false
                }
            }
        }
    }

    private fun getImageDeleteUri(context: Context, path: String): Uri? {
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + " = ?",
            arrayOf(path),
            null
        )
        val uri = if (cursor != null && cursor.moveToFirst())
            ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
            ) else null
        cursor?.close()
        return uri
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.stop()
        viewBinding.playerView.setControllerVisibilityListener(PlayerView.ControllerVisibilityListener {})
    }

    override fun onPause() {
        super.onPause()
        player?.stop()
    }

    private fun shareFile(fileUri: String) {
        val file = File(fileUri)
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${context?.packageName}${ShareFragment.FILE_PROVIDER}",
            file
        )

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = ShareFragment.SHARE_TYPE
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, context?.getString(R.string.send_file_as)))
    }

    companion object {
        private const val KEY_FILE_NAME = "fileName"
        private const val KEY_SHOW_BUTTON = "showButton"
    }
}