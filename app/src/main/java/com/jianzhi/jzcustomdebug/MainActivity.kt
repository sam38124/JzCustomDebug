package com.jianzhi.jzcustomdebug

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.jianzhi.jzcustomdebug.Frag.Frag_ShowApp
import com.orange.jzchi.jzframework.JzActivity


class MainActivity : JzActivity() {
    override fun changePageListener(tag: String, frag: Fragment) {

    }

    override fun keyEventListener(event: KeyEvent): Boolean {
        return true
    }

    override fun viewInit(rootview: View) {
      getControlInstance().setHome(Frag_ShowApp(), "Frag_ShowApp")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Create channel to show notifications.
            val channelId = "default_notification_channel_id"
            val channelName = "default_notification_channel_name"
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW
                )
            )
        }
    }

}
