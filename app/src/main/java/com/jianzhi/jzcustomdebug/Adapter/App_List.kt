package com.jianzhi.jzcustomdebug.Adapter

import com.google.firebase.messaging.FirebaseMessaging
import com.jianzhi.jzcustomdebug.Frag.Frag_ShowApp
import com.jianzhi.jzcustomdebug.Frag.Frag_error
import com.jianzhi.jzcustomdebug.R
import com.jzsql.lib.mmySql.ItemDAO
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzAdapter
import kotlinx.android.synthetic.main.app_list.view.*

class App_List(var appname: ArrayList<String>,var bundle: ArrayList<String>) : JzAdapter(R.layout.app_list) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mView.textView13.text = appname[position]
        holder.mView.textView14.text = bundle[position]
        holder.mView.setOnClickListener{
            JzActivity.getControlInstance().changePage(Frag_error(bundle[position],appname[position]),"Frag_error",true)
        }
        holder.mView.delete.setOnClickListener {
            val item = ItemDAO(JzActivity.getControlInstance().getRootActivity(), "debug.db").create()
            item.exsql("delete from `App` where bundle='${bundle[position]}'")
            item.close()
            JzActivity.getControlInstance().toast("正在取消註冊")
            FirebaseMessaging.getInstance().unsubscribeFromTopic(bundle[position])
                .addOnCompleteListener {
                    JzActivity.getControlInstance().setPro("package", "nodata")
                }
            (JzActivity.getControlInstance().getNowPage() as Frag_ShowApp).dataloading()
        }
    }
    override fun sizeInit(): Int {
        return appname.size
    }

}