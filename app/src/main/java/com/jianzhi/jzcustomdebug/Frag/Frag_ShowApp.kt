package com.jianzhi.jzcustomdebug.Frag

import android.app.Dialog
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.messaging.FirebaseMessaging
import com.jianzhi.jzcustomdebug.Adapter.App_List
import com.jianzhi.jzcustomdebug.R
import com.jzsql.lib.mmySql.ItemDAO
import com.jzsql.lib.mmySql.Sql_Result
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzchi.jzframework.callback.SetupDialog
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.add_app.*
import kotlinx.android.synthetic.main.enroll_app.view.*

class Frag_ShowApp : JzFragement(R.layout.enroll_app) {
    var app = ArrayList<String>()
    var bundle = ArrayList<String>()
    var adapter = App_List(app,bundle)
    override fun viewInit() {
        rootview.re.layoutManager = LinearLayoutManager(act)
        rootview.re.adapter = adapter
        dataloading()
        rootview.add.setOnClickListener {
            JzActivity.getControlInstance()
                .showDiaLog(R.layout.add_app, true, false, object : SetupDialog {
                    override fun dismess() {
                        dataloading()
                    }

                    override fun keyevent(event: KeyEvent): Boolean {
                        return false
                    }

                    override fun setup(rootview: Dialog) {
                        rootview.ok.setOnClickListener {
                            if(app.contains(rootview.editText.text.toString())){
                                JzActivity.getControlInstance().toast("已有此應用")
                                return@setOnClickListener
                            }
                            if(rootview.editText.text.isEmpty()||rootview.editText2.text.isEmpty()){
                                JzActivity.getControlInstance().toast("名稱和包名不得為空")
                                return@setOnClickListener
                            }
                            JzActivity.getControlInstance().toast("正在註冊")
                            JzActivity.getControlInstance().closeDiaLog()
                            JzActivity.getControlInstance().showDiaLog(R.layout.loading,true,false)
                            FirebaseMessaging.getInstance().subscribeToTopic(rootview.editText2.text.toString()).addOnCompleteListener {
                                val item = ItemDAO(JzActivity.getControlInstance().getRootActivity(), "debug.db").create()
                                item.exsql("insert into `App` (app,bundle) values ('${rootview.editText.text}','${rootview.editText2.text}')")
                                item.close()
                                JzActivity.getControlInstance().closeDiaLog()
                                dataloading()
                            }
                        }
                    }
                })
        }
    }
fun dataloading(){
    app.clear()
    bundle.clear()
    val item = ItemDAO(JzActivity.getControlInstance().getRootActivity(), "debug.db").create()
    item.exsql(
        "CREATE TABLE IF NOT EXISTS `App` (\n" +
                "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    app VARCHAR NOT NULL,\n" +
                "    bundle VARCHAR NOT NULL)"
    );
    item.query("select * from `App`", Sql_Result {
        app.add(it.getString(1))
        bundle.add(it.getString(2))
    })
    item.close()
    if(app.size>0){rootview.no.visibility= View.GONE}else{
        rootview.no.visibility=View.VISIBLE
    }
    adapter.notifyDataSetChanged()
}
}