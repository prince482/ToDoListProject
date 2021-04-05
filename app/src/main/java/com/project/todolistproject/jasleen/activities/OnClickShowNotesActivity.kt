package com.project.todolistproject.jasleen.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityOnClickShowNotesBinding
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap

class OnClickShowNotesActivity : BaseFunActivity() {
    private lateinit var showDetailBinding: ActivityOnClickShowNotesBinding
    private var db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_on_click_show_notes)
        setSupportActionBar(showDetailBinding.onClickListTB)
        showDetailBinding.ShowTitleTv.text = getIntent().getStringExtra("title")
        showDetailBinding.showBodyTv.text = getIntent().getStringExtra("body")
        showDetailBinding.updtUpdteBTN.setOnClickListener(View.OnClickListener {
            showDetailBinding.updtTitleTv.visibility=View.VISIBLE
            showDetailBinding.updtTitleSepLL.visibility=View.VISIBLE
            showDetailBinding.updteBodyEt.visibility=View.VISIBLE

            showDetailBinding.ShowTitleTv.visibility=View.GONE
            showDetailBinding.titleSepLL.visibility=View.GONE
            showDetailBinding.showBodyTv.visibility=View.GONE
        })

        showDetailBinding.updteSaveBTN.setOnClickListener{
            when {
                TextUtils.isEmpty(showDetailBinding.updteBodyEt.text.toString().trim()) -> {
                    baseBinding.dialogBoxBtn(
                        "Empty field",
                        "Are you sure you want to save the note without text?",
                        "Yes",
                        "Cancel"
                    )
                }
                else -> {
                    updateDocument(
                        Title = showDetailBinding.updtTitleTv.text.toString().trim(),
                        body = showDetailBinding.updteBodyEt.text.toString().trim()
//                            textColor = textColor.toInt()
                    )
                    //getDataOneTime()
                    showDetailBinding.updtTitleTv.visibility=View.GONE
                    showDetailBinding.updtTitleSepLL.visibility=View.GONE
                    showDetailBinding.updteBodyEt.visibility=View.GONE

                    showDetailBinding.ShowTitleTv.visibility=View.VISIBLE
                    showDetailBinding.titleSepLL.visibility=View.VISIBLE
                    showDetailBinding.showBodyTv.visibility=View.VISIBLE
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                }
            }
        }
    }
    private fun updateDocument(Title: String, body: String)
    {
        var getCurrentDate: Date = Calendar.getInstance().time
        var formatDate: String = DateFormat.getDateInstance().format(getCurrentDate)
        var simpleList: MutableMap<String, Any> = HashMap()
        simpleList["listTitle"] = Title //-> list.put("listTitle", title)
        simpleList["listBody"] = body  //->list.put("listBody", body)
        simpleList["listTime"] = formatDate
        var id : String? = intent.getStringExtra("docId")
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email
        simpleList["email"] = personEmail.toString()
        val updateRef=db.collection("simpleListData").document(id!!)
        updateRef.update("listBody" ,body)
        updateRef.update("listTitle",Title)
            .addOnSuccessListener{
                val homeIntent = Intent(this, HomeLandingActivity::class.java)
                homeIntent.putExtra("title",showDetailBinding.updtTitleTv.text.toString())
                homeIntent.putExtra("body", showDetailBinding.updteBodyEt.text.toString())
                startActivity(homeIntent)
                Toast.makeText(this@OnClickShowNotesActivity, "Successfully Added", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(this@OnClickShowNotesActivity, "failure", Toast.LENGTH_LONG).show()
            }
    }

    fun onClickUpdateBack(view: View) {
        onBackPressed()
    }

}