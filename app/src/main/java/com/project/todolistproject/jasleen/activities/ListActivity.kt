package com.project.todolistproject.jasleen.activities

/**
 * Created by Jasleen Kaur on 25-02-2021.
 */

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityListBinding
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap


class ListActivity : BaseFunActivity() {
    private lateinit var listBinding: ActivityListBinding
    private var db = FirebaseFirestore.getInstance()
    private var txtClrCode: Int? = null
    private var ttlClrCode: Int? = null
    var defaultColor: String = "#093637"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listBinding = DataBindingUtil.setContentView(this, R.layout.activity_list)

        setSupportActionBar(listBinding.customListTB.findViewById(R.id.list_toolbar))

        headerFunctions()
        bottomFunctions()
    }

    //change the color of title and body text
    private fun changeTxtColor(
        editText: EditText,
        title: String,
        color: Int = Color.parseColor(defaultColor), isTxtClr: Boolean = true
    ): Int {
        listBinding.listChangeLL.visibility = View.VISIBLE
        listBinding.changeTitleTV.text = title
        val whichOption: Int? = null
        if (isTxtClr == true) {
            editText == listBinding.listBodyText
            txtClrCode = color

            when (whichOption) {
                /*  String white = "#ffffff";*/
                whichOption/*  int whiteInt = Color.parseColor(white);*/ -> {
                    listBinding.chngeOpt1.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr1))
                        var color1: String = defaultColor
                        txtClrCode = Color.parseColor(color1)
                        listBinding.listChangeLL.visibility = View.GONE
                    }

                    listBinding.chngeOpt2.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr2))
                        var color2 = "#000000"
                        txtClrCode = Color.parseColor(color2)
                        listBinding.listChangeLL.visibility = View.GONE
                    }
                    listBinding.chngeOpt3.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr3))
                        var color3 = "#FF0000"
                        txtClrCode = Color.parseColor(color3)
                        listBinding.listChangeLL.visibility = View.GONE
                    }

                    listBinding.chngeOpt4.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr4))
                        var color4 = "#4B0082"
                        txtClrCode = Color.parseColor(color4)
                        listBinding.listChangeLL.visibility = View.GONE
                    }

                    listBinding.chngeOpt5.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr5))
                        var color5 = "#FF7F00"
                        txtClrCode = Color.parseColor(color5)
                        listBinding.listChangeLL.visibility = View.GONE
                    }

                    listBinding.chngeOpt6.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr6))
                        var color6 = "#660033"
                        txtClrCode = Color.parseColor(color6)
                        listBinding.listChangeLL.visibility = View.GONE
                    }

                }
            }
        } else {
            editText == listBinding.listTitleET
            ttlClrCode = color

            when (whichOption) {

                whichOption -> {
                    listBinding.chngeOpt1.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr1))
                        var color1: String = defaultColor
                        ttlClrCode = Color.parseColor(color1)
                        listBinding.listChangeLL.visibility = View.GONE
                    }

                    listBinding.chngeOpt2.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr2))
                        var color2 = "#000000"
                        ttlClrCode = Color.parseColor(color2)
                        listBinding.listChangeLL.visibility = View.GONE
                    }
                    listBinding.chngeOpt3.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr3))
                        var color3 = "#FF0000"
                        ttlClrCode = Color.parseColor(color3)
                        listBinding.listChangeLL.visibility = View.GONE
                    }

                    listBinding.chngeOpt4.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr4))
                        var color4 = "#4B0082"
                        ttlClrCode = Color.parseColor(color4)
                        listBinding.listChangeLL.visibility = View.GONE
                    }

                    listBinding.chngeOpt5.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr5))
                        var color5 = "#FF7F00"
                        ttlClrCode = Color.parseColor(color5)
                        listBinding.listChangeLL.visibility = View.GONE
                    }

                    listBinding.chngeOpt6.setOnClickListener {
                        editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr6))
                        var color6 = "#660033"
                        ttlClrCode = Color.parseColor(color6)
                        listBinding.listChangeLL.visibility = View.GONE
                    }
                }
            }
        }
        return color
    }

    //Toolbar views Functions
    private fun headerFunctions() {

        //  WHEN CLICK ON BACK BUTTON
        listBinding.customListTB.findViewById<Button>(R.id.TB_back_BTN)
            .setOnClickListener {
                startActivity(Intent(this, HomeLandingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            }

        //  WHEN CLICK ON SAVE BUTTON
        listBinding.customListTB.findViewById<Button>(R.id.save_list_BTN)
            .setOnClickListener {
                when {
                    TextUtils.isEmpty(listBinding.listTitleET.text.toString().trim()) -> {
                        baseBinding.dialogBoxBtn(
                            "Empty field",
                            "Are you sure you want to save the note without title?\nList may not get saved without the title.",
                            "Yes",
                            "Cancel"
                        )
                        fireStoreAddData(
                            title = listBinding.listTitleET.text.toString().trim(),
                            body = listBinding.listBodyText.text.toString().trim(),
                            titleClr = ttlClrCode!!,
                            txtClr = txtClrCode!!

                        )
                        startActivity(Intent(this, HomeLandingActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        finish()
                    }
                    TextUtils.isEmpty(listBinding.listBodyText.text.toString().trim()) -> {
                        baseBinding.dialogBoxBtn(
                            "Empty field",
                            "Are you sure you want to save the note without text?\nList may not get saved without the body.",
                            "Yes",
                            "Cancel"
                        )
                        fireStoreAddData(
                            title = listBinding.listTitleET.text.toString().trim(),
                            body = listBinding.listBodyText.text.toString().trim(),
                            titleClr = ttlClrCode!!,
                            txtClr = txtClrCode!!,
                        )
                        startActivity(Intent(this, HomeLandingActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        finish()

                    }
                    else -> {
                        if (txtClrCode == null && ttlClrCode == null) {
                            ttlClrCode = Color.parseColor(defaultColor)
                            txtClrCode = Color.parseColor(defaultColor)
                            fireStoreAddData(
                                title = listBinding.listTitleET.text.toString().trim(),
                                body = listBinding.listBodyText.text.toString().trim(),
                                titleClr = ttlClrCode!!,
                                txtClr = txtClrCode!!
                            )
                            startActivity(Intent(this, HomeLandingActivity::class.java))
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                            finish()

                        } else if (ttlClrCode == null) {
                            ttlClrCode = Color.parseColor(defaultColor)
                            fireStoreAddData(
                                title = listBinding.listTitleET.text.toString().trim(),
                                body = listBinding.listBodyText.text.toString().trim(),
                                titleClr = ttlClrCode!!,
                                txtClr = txtClrCode!!
                            )
                            startActivity(Intent(this, HomeLandingActivity::class.java))
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                            finish()

                        } else if (txtClrCode == null) {
                            txtClrCode = Color.parseColor(defaultColor)
                            fireStoreAddData(
                                title = listBinding.listTitleET.text.toString().trim(),
                                body = listBinding.listBodyText.text.toString().trim(),
                                titleClr = ttlClrCode!!,
                                txtClr = txtClrCode!!
                            )
                            startActivity(Intent(this, HomeLandingActivity::class.java))
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                            finish()

                        } else {
                            fireStoreAddData(
                                title = listBinding.listTitleET.text.toString().trim(),
                                body = listBinding.listBodyText.text.toString().trim(),
                                titleClr = ttlClrCode!!,
                                txtClr = txtClrCode!!
                            )
                            startActivity(Intent(this, HomeLandingActivity::class.java))
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                            finish()

                        }
                    }
                }

            }

        //  WHEN CLICK ON CANCEL BUTTON
        listBinding.customListTB.findViewById<Button>(R.id.cancel_list_BTN)
            .setOnClickListener {
                startActivity(Intent(this@ListActivity, HomeLandingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            }

        //  WILL MAKE SECRET NOTE
        listBinding.customListTB.findViewById<Button>(R.id.secret_note_BTN)
            .setOnClickListener {
                baseBinding.addSecretNote(
                    title = listBinding.listTitleET.text.toString().trim(),
                    body = listBinding.listBodyText.text.toString().trim()
                )
                startActivity(Intent(this, HomeLandingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()

            }

    }

    //add list activity data into firestore
//    , textColor: Int
    private fun fireStoreAddData(title: String, body: String, titleClr: Int, txtClr: Int) {

        val getCurrentDate: Date = Calendar.getInstance().time
        val formatDate: String = DateFormat.getDateInstance().format(getCurrentDate)
        val simpleList: MutableMap<String, Any> = HashMap()
        simpleList["listTitle"] = title //-> list.put("listTitle", title)
        simpleList["listBody"] = body  //->list.put("listBody", body)
        simpleList["listTime"] = formatDate
        simpleList["textColor"] = txtClr
        simpleList["titleColor"] = titleClr
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email
        simpleList["email"] = personEmail.toString()
        db.collection("simpleListData")
            .add(simpleList)
            .addOnSuccessListener {
                baseBinding.logFun("Data in fireBase", "Data saved")
                Toast.makeText(this@ListActivity, "Sucessfully Added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@ListActivity, "Fail", Toast.LENGTH_SHORT).show()
                baseBinding.logFun("Data not in firebase", "Couldn't save")

            }
    }

    //Bottom Views Functions
    //Bottom navigation views Functions
    private fun bottomFunctions() {

        //change  title color of the note
        listBinding.changeTextClrBTN.setOnClickListener {
            changeTxtColor(listBinding.listTitleET, "Change Title Color", isTxtClr = false)
        }

        //change text(body) color of the note
        listBinding.changeTitleClrBTN.setOnClickListener {
            changeTxtColor(listBinding.listBodyText, "Change text Color")
        }

        //change text font of the note
        listBinding.changeTextFontBTN.setOnClickListener {
            baseBinding.textFontChangeDialog(listBinding.listBodyText)
        }

        listBinding.voiceNotes.setOnClickListener {
            getSpeechInput()
        }
    }

    //for speech to text
    private fun getSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 10)
        } else {
            Toast.makeText(this, "Your Device Doesn't Support Speech Input", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode === 0) {
            if (resultCode === RESULT_OK) {
                val result: String? = data?.getStringExtra("result")
            }
            if (resultCode === RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        /* when (requestCode) {
             10 -> if (resultCode == RESULT_OK &&
                 data != null
             ) {
                 val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                 val voiceTextView: TextView = findViewById(R.id.list_body_text)
                 voiceTextView.text = result?.get(index = 0)
             }
         }*/


    }

}