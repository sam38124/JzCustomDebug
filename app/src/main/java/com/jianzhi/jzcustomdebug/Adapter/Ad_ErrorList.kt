package com.jianzhi.jzcustomdebug.Adapter

import com.jianzhi.jzcustomdebug.Beans.Be_Errors
import com.jianzhi.jzcustomdebug.Frag.Frag_Detail
import com.jianzhi.jzcustomdebug.Frag.Frag_error
import com.jianzhi.jzcustomdebug.R
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzAdapter
import kotlinx.android.synthetic.main.error_list_ad.view.*

class Ad_ErrorList(var list: ArrayList<Be_Errors>) : JzAdapter(R.layout.error_list_ad) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mView.textView6.text=list[position].type
        holder.mView.time.text=list[position].time
        holder.mView.setOnClickListener {
            var a=ArrayList<String>()
            JzActivity.getControlInstance().changePage(Frag_Detail(list[position].id,(JzActivity.getControlInstance().getNowPage() as Frag_error).bundle),"Frag_Detail",true)
        }
    }

    override fun sizeInit(): Int {
        return list.size
    }

}