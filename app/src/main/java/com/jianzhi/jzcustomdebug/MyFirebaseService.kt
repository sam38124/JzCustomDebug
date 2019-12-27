package com.jianzhi.jzcustomdebug


import android.content.Context
import android.os.Handler
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jzsql.lib.mmySql.ItemDAO

class MyFirebaseService : FirebaseMessagingService() {
    val handler=Handler()
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handler.post {
            var data=remoteMessage.data["data"]
            var type=remoteMessage.data["type"]
            var time=remoteMessage.data["time"]
            var dbname=this.applicationContext.getSharedPreferences("Setting", Context.MODE_PRIVATE).getString("package","nodata")

            Log.i("MyFirebaseService", "收到")
            Log.i("MyFirebaseService", "data " + data)
            Log.i("MyFirebaseService", "type " + type)
            Log.i("MyFirebaseService", "time " + time)
            val item = ItemDAO(this.applicationContext, "debug.db").create()
            item.ExSql(
                "CREATE TABLE IF NOT EXISTS `$dbname` (\n" +
                        "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    type VARCHAR NOT NULL,\n" +
                        "    data VARCHAR NOT NULL,\n" +
                        "    time VARCHAR NOT NULL\n" +
                        ");\n"
            )
            item.ExSql("insert into `$dbname` (type,data,time) values ('${type}','${remoteMessage.data["data"]}','${remoteMessage.data["time"]}')")
            item.close()
            Log.i("comple","complete")
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

}