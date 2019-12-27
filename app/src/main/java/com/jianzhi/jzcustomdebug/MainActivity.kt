package com.jianzhi.jzcustomdebug

import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.jianzhi.jzcustomdebug.Frag.Frag_Enroll
import com.orange.jzchi.jzframework.RootActivity

class MainActivity : RootActivity() {
    override fun ChangePageListener(tag: String, frag: Fragment) {

    }

    override fun KeyLinsten(event: KeyEvent) {

    }

    override fun ViewInit(rootview: View) {
        SetHome(Frag_Enroll(), "Frag_Enroll")
    }
}
