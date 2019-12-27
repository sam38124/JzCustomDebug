package com.jianzhi.jzcustomdebug.Adapter

import com.jianzhi.jzcustomdebug.Beans.Be_Errors
import com.jianzhi.jzcustomdebug.Frag.Frag_Detail
import com.jianzhi.jzcustomdebug.R
import com.orange.jzchi.jzframework.RootAdapter
import kotlinx.android.synthetic.main.error_list_ad.view.*

class Ad_ErrorList(var list: ArrayList<Be_Errors>) : RootAdapter(R.layout.error_list_ad) {
    override fun SizeInit(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mView.textView6.text=list[position].type
        holder.mView.time.text=list[position].time
        holder.mView.setOnClickListener {
            act.ChangePage(Frag_Detail(list[position].id),"Frag_Detail",true)
        }
    }

}