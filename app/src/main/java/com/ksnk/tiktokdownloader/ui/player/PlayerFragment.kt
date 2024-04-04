package com.ksnk.tiktokdownloader.ui.player

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    private var isAllFabsVisible: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBlackColorInStatusBar()
        with(viewBinding) {
            arguments?.getString(ShareFragment.KEY_FILE_URI)?.let { videoUri ->
                hideBottomNav()
                player = ExoPlayer.Builder(requireContext()).build()
                viewBinding.playerView.player = player
                player?.setMediaItem(MediaItem.fromUri(videoUri))
                player?.prepare()
                player?.play()
            }

            viewBinding.playerView.setControllerVisibilityListener(PlayerView.ControllerVisibilityListener {
                if (it == 0) viewBinding.floatingActionButton.visibility = View.VISIBLE
                else viewBinding.floatingActionButton.visibility = View.GONE
            })

            arguments?.getString("fileName")?.let { name ->
               floatingActionButtonShare.setOnClickListener {
                    player?.pause()
                    shareFile(File(DOWNLOAD_VIDEOS_DIRECTORY, name).absolutePath)
                }

                floatingActionButtonDelete.setOnClickListener {
                    if (File(DOWNLOAD_VIDEOS_DIRECTORY, name).exists()){
                        File(DOWNLOAD_VIDEOS_DIRECTORY, name).deleteRecursively()
                    }
                    navigation.popBackStack()
                }
            }

            viewBinding.floatingActionButton.setOnClickListener {
                if (!isAllFabsVisible) {
                    floatingActionButtonShare.show()
                    floatingActionButtonDelete.show()
                    isAllFabsVisible = true
                } else {
                    floatingActionButtonShare.hide()
                    floatingActionButtonDelete.hide()
                    isAllFabsVisible = false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.stop()
        viewBinding.playerView.setControllerVisibilityListener(PlayerView.ControllerVisibilityListener {
        })
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
}