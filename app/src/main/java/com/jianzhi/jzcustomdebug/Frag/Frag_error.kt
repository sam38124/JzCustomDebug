package com.jianzhi.jzcustomdebug.Frag

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.messaging.FirebaseMessaging
import com.jianzhi.jzcustomdebug.Adapter.Ad_ErrorList
import com.jianzhi.jzcustomdebug.Beans.Be_Errors
import com.jianzhi.jzcustomdebug.R
import com.jzsql.lib.mmySql.ItemDAO
import com.jzsql.lib.mmySql.Sql_Result
import com.orange.jzchi.jzframework.RootFragement
import kotlinx.android.synthetic.main.error_list.view.*

class Frag_error : RootFragement(R.layout.error_list) {
    var beans = ArrayList<Be_Errors>()
    var adapter: Ad_ErrorList = Ad_ErrorList(beans)
    override fun ViewInit() {
        rootview.re.layoutManager = LinearLayoutManager(act)
        rootview.re.adapter = adapter
        rootview.exit.setOnClickListener {
            act.Toast("正在取消註冊")
            FirebaseMessaging.getInstance().subscribeToTopic(rootshare.GetPro("package", "nodata"))
                .addOnCompleteListener {
                    rootshare.SetPro("package", "nodata")
                    act.SetHome(Frag_Enroll(), "Frag_Enroll")
                }
        }
        val item = ItemDAO(act, "debug.db").create()
        item.ExSql(
            "CREATE TABLE IF NOT EXISTS `${rootshare.GetPro("package", "nodata")}` (\n" +
                    "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    type VARCHAR NOT NULL,\n" +
                    "    data VARCHAR NOT NULL,\n" +
                    "    time VARCHAR NOT NULL\n" +
                    ");\n"
        )
        item.Query("select * from `${rootshare.GetPro("package", "nodata")}` order by id desc", Sql_Result {
            Log.e("data", "sss")
            val bean = Be_Errors()
            bean.id = it.getString(0)
            bean.type = it.getString(1)
            bean.data = it.getString(2)
            bean.time = it.getString(3)
            Log.e("data", bean.type)
            beans.add(bean)
            handler.post {
                rootview.no.visibility = View.GONE
                adapter.notifyDataSetChanged()
            }
        });

    }

}