package com.ksnk.tiktokdownloader.data.model

import android.net.Uri

data class Media(
    val name: String,
    val uri: Uri,
    val duration: String,
    val size: String
)

