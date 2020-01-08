package com.jianzhi.jzcustomdebug.Frag

import com.jianzhi.jzcustomdebug.R
import com.jzsql.lib.mmySql.ItemDAO
import com.jzsql.lib.mmySql.Sql_Result
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import kotlinx.android.synthetic.main.error_detail.view.*

class Frag_Detail(val id:String,var bundle:String):JzFragement(R.layout.error_detail){

    override fun viewInit() {
        val item = ItemDAO(act, "debug.db").create()
        item.query("select * from `${bundle}` where id='$id'", Sql_Result {
            val result=it.getString(2)
            val make=it.getString(1)
            val time=it.getString(3)
            handler.post {
                rootview.error_text.text=result
                rootview.time.text=time
                rootview.make.text=make
            }
        })
        rootview.back.setOnClickListener {JzActivity.getControlInstance().goBack()
        }
    }

}