package com.project.todolistproject.jasleen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityOnClickPartSettingBinding
import com.project.todolistproject.databinding.ActivityOnClickShowSecretListBinding

class OnClickShowSecretListActivity : AppCompatActivity() {
    private lateinit var shwScrtLstBinding:ActivityOnClickShowSecretListBinding
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shwScrtLstBinding=DataBindingUtil.setContentView(this,R.layout.activity_on_click_show_secret_list)
        /* showDetailBinding.ShowTitleTv.text = getIntent().getStringExtra("title")
        showDetailBinding.showBodyTv.text = getIntent().getStringExtra("body")*/
        shwScrtLstBinding.scrtShowTitleTV.text=intent.getStringExtra("secretTitle")
        shwScrtLstBinding.showScrtBdyTV.text=intent.getStringExtra("secretBody")
    }

    fun onShowScrtBack(view: View) {
        onBackPressed()
    }
}