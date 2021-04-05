package com.project.todolistproject.jasleen.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.project.todolistproject.R

class NeedHelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_help)
    }

    fun googleSignHelp(view: View) {

        var googleIntent= Intent(Intent.ACTION_VIEW, Uri.parse("https://support.google.com/accounts/answer/112802?co=GENIE.Platform%3DAndroid&hl=en"))
        startActivity(googleIntent)
    }
    fun fbSignInHelp(view: View) {
        var facebookIntent= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/help/1058033620955509/log-in-to-your-account/?helpref=hc_fnav"))
        startActivity(facebookIntent)
    }
    fun onClickBackHelp(view: View) {
        onBackPressed()
    }
}