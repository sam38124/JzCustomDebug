package com.jianzhi.jzcustomdebug.Frag

import com.google.firebase.messaging.FirebaseMessaging
import com.jianzhi.jzcustomdebug.R
import com.orange.jzchi.jzframework.RootFragement
import kotlinx.android.synthetic.main.activity_main.view.*

class Frag_Enroll : RootFragement(R.layout.activity_main) {
    override fun ViewInit() {
        if(rootshare.GetPro("package", "nodata")=="nodata"){
            rootview.enter.setOnClickListener {
                if(rootview.pkname.text.isEmpty()){
                    act.Toast("包名不得為空")
                    return@setOnClickListener
                }
                rootshare.SetPro("package",rootview.pkname.text.toString())
                FirebaseMessaging.getInstance().subscribeToTopic(rootview.pkname.text.toString()).addOnCompleteListener {
                    act.SetHome(Frag_error(),"Frag_error")
                }
            }
        }else{
            act.SetHome(Frag_error(),"Frag_error")
        }

    }

}