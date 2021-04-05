package com.project.todolistproject.jasleen.activities

/**
 * Created by Jasleen Kaur on 25-02-2021.
 */
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityGroceryListBinding
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap


class GroceryListActivity : BaseFunActivity() {
    private lateinit var groceryBinding: ActivityGroceryListBinding
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        groceryBinding = DataBindingUtil.setContentView(this, R.layout.activity_grocery_list)

        setSupportActionBar(groceryBinding.groceryTb.findViewById(R.id.list_toolbar))

        headerFunctions()
        bottomFunctions()
    }
    //Toolbar views Functions
    private fun headerFunctions() {

        //  WHEN CLICK ON BACK BUTTON
        groceryBinding.groceryTb.findViewById<Button>(R.id.TB_back_BTN)
            .setOnClickListener(View.OnClickListener {
                startActivity(Intent(this, HomeLandingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            })

        //  WHEN CLICK ON SAVE BUTTON
        groceryBinding.groceryTb.findViewById<Button>(R.id.save_list_BTN)
            .setOnClickListener(View.OnClickListener {
                when {
                    TextUtils.isEmpty(groceryBinding.groceryTitleET.text.toString().trim()) -> {
                        baseBinding.dialogBoxBtn(
                            "Empty field",
                            "Are you sure you want to save the note without title?",
                            "Yes",
                            "Cancel"
                        )
                    }
                    TextUtils.isEmpty(groceryBinding.groceryBodyET.text.toString().trim()) -> {
                        baseBinding.dialogBoxBtn(
                            "Empty field",
                            "Are you sure you want to save the note without text?",
                            "Yes",
                            "Cancel"
                        )

                    }
                    else -> {
                        fireStoreAddData(
                            title = groceryBinding.groceryTitleET.text.toString().trim(),
                            body = groceryBinding.groceryBodyET.text.toString().trim()
                        )
                        val homeGIntent = Intent(this, HomeLandingActivity::class.java)
                        homeGIntent.putExtra("title", groceryBinding.groceryTitleET.text.toString())
                        homeGIntent.putExtra("body", groceryBinding.groceryBodyET.text.toString())
                        startActivity(homeGIntent)
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        finish()
                    }
                }
            })

        //  WHEN CLICK ON CANCEL BUTTON
        groceryBinding.groceryTb.findViewById<Button>(R.id.cancel_list_BTN)
            .setOnClickListener(View.OnClickListener {
                startActivity(Intent(this@GroceryListActivity, HomeLandingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            })

        //  WHEN CLICK ON MENU BUTTON
//        groceryBinding.groceryTb.findViewById<Button>(R.id.setting_list_BTN)
//            .setOnClickListener(View.OnClickListener {
//                baseBinding.onClickDotMenu(groceryBinding.groceryTb.findViewById<Button>(R.id.setting_list_BTN))
//            })

    }

    //  to add data into firebase
    private fun fireStoreAddData(title: String, body: String) {

        var getCurrentDate: Date = Calendar.getInstance().time
        var formatDate: String = DateFormat.getDateInstance().format(getCurrentDate)
        var simpleList: MutableMap<String, Any> = HashMap()
        simpleList["listTitle"] = title //-> list.put("listTitle", title)
        simpleList["listBody"] = body  //->list.put("listBody", body)
        simpleList["listTime"] = formatDate
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email
        simpleList["email"] = personEmail.toString()
        db.collection("simpleListData")
            .add(simpleList)
            .addOnSuccessListener {
                baseBinding.logFun("fireBase add success", "Data saved")
                Toast.makeText(this@GroceryListActivity, "Sucessfully Added", Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(this@GroceryListActivity, "Fail", Toast.LENGTH_LONG).show()
                baseBinding.logFun("fireBase add failure", "Couldn't save")

            }
    }

    //Bottom navigation views Functions
    private fun bottomFunctions() {

        //change text(body) color of the note
        groceryBinding.bodyClrChangeBTN.setOnClickListener(View.OnClickListener {
            changeTxtColor(groceryBinding.groceryBodyET, "Change Text Color")
        })

        //change title color of the note
        groceryBinding.titleClrChangeBTN.setOnClickListener(View.OnClickListener {
            changeTxtColor(groceryBinding.groceryTitleET, "Change title Color")
        })

        //change text font of the note
        groceryBinding.bodyTxtChangeBTN.setOnClickListener(View.OnClickListener {
            baseBinding.textFontChangeDialog(groceryBinding.groceryBodyET)
        })

        //voice note
        groceryBinding.groceryVoiceNoteBTN.setOnClickListener(View.OnClickListener {
            getSpeechInput()
        })
    }

    //change color of the title and body
    private fun changeTxtColor(
        editText: EditText,
        title: String,
        colorCode: Int = getColor(R.color.txt_clr1)
    ) {
        groceryBinding.grcryChangeLL.visibility = View.VISIBLE
        groceryBinding.grcryChangeTitleTV.text = title
        var whichOption: Int? = null
        var getClrChange = colorCode
        when (whichOption) {

            whichOption -> {
                groceryBinding.grcryChngeOpt1.setOnClickListener {
                    editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr1))
                    getClrChange = getColor(R.color.txt_clr1)
                    groceryBinding.grcryChangeLL.visibility = View.GONE
                }

                groceryBinding.grcryChngeOpt2.setOnClickListener {
                    editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr2))
                    getClrChange = getColor(R.color.txt_clr2)
                    groceryBinding.grcryChangeLL.visibility = View.GONE
                }
                groceryBinding.grcryChngeOpt3.setOnClickListener {
                    editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr3))
                    getClrChange = getColor(R.color.txt_clr3)
                    groceryBinding.grcryChangeLL.visibility = View.GONE
                }
                groceryBinding.grcryChngeOpt4.setOnClickListener {
                    editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr4))
                    getClrChange = getColor(R.color.txt_clr4)
                    groceryBinding.grcryChangeLL.visibility = View.GONE
                }
                groceryBinding.grcryChngeOpt5.setOnClickListener {
                    editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr5))
                    getClrChange = getColor(R.color.txt_clr5)
                    groceryBinding.grcryChangeLL.visibility = View.GONE
                }
                groceryBinding.grcryChngeOpt6.setOnClickListener {
                    editText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr6))
                    getClrChange = getColor(R.color.txt_clr6)
                    groceryBinding.grcryChangeLL.visibility = View.GONE
                }
            }
        }

    }

    //for speech to text
    fun getSpeechInput() {
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
        super.onActivityResult(
            requestCode,
            resultCode, data
        )
        when (requestCode) {
            10 -> if (resultCode == RESULT_OK &&
                data != null
            ) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val voiceTextView: TextView = findViewById(R.id.list_body_text)
                voiceTextView.text = result?.get(index = 0)
            }
        }
    }
}