package com.project.todolistproject.jasleen.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityEventBinding
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap


class EventActivity : BaseFunActivity() {
    private lateinit var eventBinding: ActivityEventBinding
    private var db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventBinding = DataBindingUtil.setContentView(this, R.layout.activity_event)
        setSupportActionBar(eventBinding.eventListTB.findViewById(R.id.list_toolbar))
        headerFunctions()
    }

    //Toolbar views Functions
    private fun headerFunctions() {

        //  WHEN CLICK ON BACK BUTTON
        eventBinding.eventListTB.findViewById<Button>(R.id.TB_back_BTN)
            .setOnClickListener(View.OnClickListener {
                startActivity(Intent(this, HomeLandingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            })

        //  WHEN CLICK ON SAVE BUTTON
        eventBinding.eventListTB.findViewById<Button>(R.id.save_list_BTN)
            .setOnClickListener(View.OnClickListener {
                when {
                    TextUtils.isEmpty(eventBinding.dialogEditeventEt.text.toString().trim()) -> {
                        baseBinding.dialogBoxBtn(
                            "Empty field",
                            "Are you sure you want to save the note without title?",
                            "Yes",
                            "Cancel"
                        )
                    }

                    else -> {
                        fireStoreAddData(
                            date = eventBinding.DialogShowdateTv.dayOfMonth.toString().trim(),
                            body = eventBinding.dialogEditeventEt.text.toString().trim()
                        )
                        val homeIntent = Intent(this, HomeLandingActivity::class.java)
                        homeIntent.putExtra("date", eventBinding.DialogShowdateTv.dayOfMonth.toString())
                        homeIntent.putExtra("body", eventBinding.dialogEditeventEt.text.toString())
                        startActivity(homeIntent)
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        finish()
                    }
                }

            })

        //  WHEN CLICK ON CANCEL BUTTON
        eventBinding.eventListTB.findViewById<Button>(R.id.cancel_list_BTN)
            .setOnClickListener(View.OnClickListener {
                startActivity(Intent(this, HomeLandingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            })

        //  WHEN CLICK ON MENU BUTTON
        eventBinding.eventListTB.findViewById<Button>(R.id.secret_note_BTN).visibility=View.GONE

    }

    private fun fireStoreAddData(date: String, body: String) {
        var getCurrentDate: Date = Calendar.getInstance().time
        var formatDate: String = DateFormat.getDateInstance().format(getCurrentDate)
        var EventList: MutableMap<String, Any> = HashMap()
        EventList["date"] = date //-> list.put("date", date)
        EventList["eventBody"] = body  //->list.put("eventBody", body)
        EventList["time"] = formatDate
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email
        EventList["email"] = personEmail.toString()
        db.collection("ToDoListData")
            .add(EventList)
            .addOnSuccessListener {
                baseBinding.logFun("fireBase add success", "Data saved on $date")
                baseBinding.toastMessage("Event Successfully Saved for $date")
            }
            .addOnFailureListener {
                baseBinding.toastMessage("Error. Can't save the event")
                baseBinding.logFun("fireBase add failure", "Couldn't save")

            }
    }

}