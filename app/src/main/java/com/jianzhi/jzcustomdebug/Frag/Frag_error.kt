package com.jianzhi.jzcustomdebug.Frag

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jianzhi.jzcustomdebug.Adapter.Ad_ErrorList
import com.jianzhi.jzcustomdebug.Beans.Be_Errors
import com.jianzhi.jzcustomdebug.R
import com.jianzhi.jzcustomdebug.event.MessageEvent
import com.jzsql.lib.mmySql.ItemDAO
import com.jzsql.lib.mmySql.Sql_Result
import com.orange.jzchi.jzframework.JzActivity.Companion.getControlInstance
import com.orange.jzchi.jzframework.JzFragement
import kotlinx.android.synthetic.main.error_list.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class Frag_error(var bundle:String,var appname:String) : JzFragement(R.layout.error_list) {
    var beans = ArrayList<Be_Errors>()
    var adapter: Ad_ErrorList = Ad_ErrorList(beans)
    override fun viewInit() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);}
        beans.clear()
        rootview.textView5.text=appname
        rootview.back.setOnClickListener { getControlInstance().goBack() }
        rootview.re.layoutManager = LinearLayoutManager(getControlInstance().getRootActivity())
        rootview.re.adapter = adapter
        val item = ItemDAO(act, "debug.db").create()
        item.exsql(
            "CREATE TABLE IF NOT EXISTS `${bundle}` (\n" +
                    "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    type VARCHAR NOT NULL,\n" +
                    "    data VARCHAR NOT NULL,\n" +
                    "    time VARCHAR NOT NULL\n" +
                    ");\n"
        )
        item.query("select * from `${bundle}` order by id desc", Sql_Result {
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

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) {
        viewInit()
    }
}