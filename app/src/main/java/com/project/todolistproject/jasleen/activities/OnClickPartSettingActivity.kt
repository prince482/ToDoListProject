package com.project.todolistproject.jasleen.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityOnClickPartSettingBinding
import java.util.*

class OnClickPartSettingActivity : AppCompatActivity() {
    private lateinit var partSettingBinding: ActivityOnClickPartSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        partSettingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_on_click_part_setting)
        showUserDetails()

    }

    //  to show user details
    private fun showUserDetails() {

        var profile:ImageView=findViewById(R.id.person_images)
        partSettingBinding.settingTypeTV.text = intent.getStringExtra("user")
        partSettingBinding.userLL.visibility = View.VISIBLE
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl
            Glide.with(this).load(personPhoto).placeholder(R.drawable.ic_login_logo).into(profile)
            partSettingBinding.userLL.findViewById<TextView>(R.id.personName_TV).text = personName
            partSettingBinding.userLL.findViewById<TextView>(R.id.personEmail_Tv).text = personEmail



        }

    }

    fun onBackClick(view: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

    }


}