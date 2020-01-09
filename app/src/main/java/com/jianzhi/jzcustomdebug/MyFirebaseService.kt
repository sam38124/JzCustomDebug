package com.jianzhi.jzcustomdebug

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jianzhi.jzcustomdebug.event.MessageEvent
import com.jzsql.lib.mmySql.ItemDAO
import org.greenrobot.eventbus.EventBus


class MyFirebaseService : FirebaseMessagingService() {
    val handler = Handler()
    val channelid = "com.jianzhi.jzcustomdebug"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handler.post {
            Log.e("type", remoteMessage.from)
            var data = remoteMessage.data["data"]
            var type = remoteMessage.data["type"]
            var time = remoteMessage.data["time"]
            var dbname = remoteMessage.from.toString().replace("/topics/", "")
            Log.i("MyFirebaseService", "收到" + dbname)
            Log.i("MyFirebaseService", "data " + data)
            Log.i("MyFirebaseService", "type " + type)
            Log.i("MyFirebaseService", "time " + time)
            val item = ItemDAO(this.applicationContext, "debug.db").create()
            item.exsql(
                "CREATE TABLE IF NOT EXISTS `$dbname` (\n" +
                        "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    type VARCHAR NOT NULL,\n" +
                        "    data VARCHAR NOT NULL,\n" +
                        "    time VARCHAR NOT NULL\n" +
                        ");\n"
            )
            item.exsql("insert into `$dbname` (type,data,time) values ('${type}','${remoteMessage.data["data"]}','${remoteMessage.data["time"]}')")
            item.close()
            Log.i("comple", "complete")
            EventBus.getDefault().post(MessageEvent())
            AddAdvice("發生崩潰", type.toString())
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MyFirebaseService", "close")
    }

    override fun onNewToken(s: String?) {
        super.onNewToken(s)
        Log.i("MyFirebaseService", "token " + s!!)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_app_name)
            val descriptionText = getString(R.string.error)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelid, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun AddAdvice(title: String, content: String) {
        createNotificationChannel()
        var pi = PendingIntent.getActivity(
            this,
            100,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        var builder = NotificationCompat.Builder(this, channelid)
            .setSmallIcon(R.mipmap.erroricon)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            .setContentIntent(pi)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(10, builder.build())
        }
    }
}