package com.ksnk.tiktokdownloader.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ksnk.tiktokdownloader.events.DownloadEvent
import org.greenrobot.eventbus.EventBus

class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
            EventBus.getDefault().post(DownloadEvent(true, "message"))
        }
    }
}