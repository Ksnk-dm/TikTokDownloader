package com.ksnk.tiktokdownloader.ui.permission

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.base.BaseFragment
import com.ksnk.tiktokdownloader.databinding.FragmentPermissionBinding

class PermissionFragment : BaseFragment(R.layout.fragment_permission) {

    private val viewBinding by viewBinding(FragmentPermissionBinding::bind)
    private val requestPermissionWriteExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) navigation.popBackStack()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNav()
        with(viewBinding) {
            toolbar.setNavigationOnClickListener {
                navigation.openDownloadFragmentFromPermission()
            }
            buttonAllow.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requestPermissionWriteExternalStorage.launch(Manifest.permission.READ_MEDIA_VIDEO)
                else requestPermissionWriteExternalStorage.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
}