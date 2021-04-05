package com.project.todolistproject.jasleen.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.jasleen.adapter.BottomNavigationAdapter
import com.project.todolistproject.jasleen.model.BottomNavigationData
import com.project.todolistproject.jasleen.model.SecretListData
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap


/**
 * Created by Jasleen Kaur on 21-02-2021.
 */
open class BaseFunActivity : AppCompatActivity() {

    private var progress: ProgressDialog?=null
    lateinit var baseBinding: BaseFunActivity
    private lateinit var isdialog: android.app.AlertDialog
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = this
        //progressBarDialog()


    }

    //dialog box with single button
    fun dialogBoxBtn(errorTitle: String, errorMessage: String, buttonText: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_box, null)
        val alertBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        var dialogTitle: TextView = dialogView.findViewById(R.id.error_title_Tv)
        var dialogMessage: TextView = dialogView.findViewById(R.id.error_msg_Tv)
        var dialogButton1: Button = dialogView.findViewById(R.id.dialog_BTN1)
        var dialogButton2: Button = dialogView.findViewById(R.id.dialog_BTN2)
        dialogButton2.visibility = View.GONE
        dialogTitle.text = errorTitle
        dialogMessage.text = errorMessage
        dialogButton1.text = buttonText
        var alertDialogBuilder = alertBuilder.create()
        dialogButton1.setOnClickListener(View.OnClickListener
        {
            alertDialogBuilder.dismiss()
        })
        alertDialogBuilder.show()
    }

    //dialog box with two button
    fun dialogBoxBtn(errorTitle: String, errorMessage: String, btn1: String, btn2: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_box, null)
        val alertBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        var dialogTitle: TextView = dialogView.findViewById(R.id.error_title_Tv)
        var dialogMessage: TextView = dialogView.findViewById(R.id.error_msg_Tv)
        var dialogButton1: Button = dialogView.findViewById(R.id.dialog_BTN1)
        var dialogButton2: Button = dialogView.findViewById(R.id.dialog_BTN2)
        dialogButton2.visibility = View.VISIBLE
        dialogTitle.text = errorTitle
        dialogMessage.text = errorMessage
        dialogButton1.text = btn1
        dialogButton2.text = btn2
        var alertDialogBuilder = alertBuilder.create()
        dialogButton1.setOnClickListener(View.OnClickListener
        {
            startActivity(Intent(this, HomeLandingActivity::class.java))
            finish()
        })
        dialogButton2.setOnClickListener(View.OnClickListener
        {
            alertDialogBuilder.dismiss()
        })
        alertDialogBuilder.show()
    }

    fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun logFun(title: String, message: String) {
        Log.e(title, message)
    }

    //  to change fonts of the body text
    private var textFontList = BottomNavigationAdapter(
        this,
        listOf(
            BottomNavigationData(1, 1, "DancingScript Regular"),
            BottomNavigationData(1, 2, "Lovely mugs"),
            BottomNavigationData(1, 3, "Mangnolia Light"),
            BottomNavigationData(1, 4, "Mommy Cupcakes"),
            BottomNavigationData(1, 5, "Montserrat Medium"),
            BottomNavigationData(1, 6, "Montserrat SemiBold"),
            BottomNavigationData(1, 7, "Montserrat Regular"),
            BottomNavigationData(1, 8, "Play flair Black Italic"),
            BottomNavigationData(1, 9, "Play flair Black Regular")
        )
    )

    fun textFontChangeDialog(bodyText: EditText) {
        val textChangeView = LayoutInflater.from(this).inflate(R.layout.dialog_change, null)
        val alertBuilder = AlertDialog.Builder(this)
            .setView(textChangeView)
        var alertDialogBuilder = alertBuilder.create()

        val textTitle: TextView = textChangeView.findViewById(R.id.change_title)
        textTitle.visibility = View.VISIBLE
        textTitle.text = "Change text Font"
        val textRecyclerView = textChangeView.findViewById<RecyclerView>(R.id.change_RV)
        textRecyclerView.adapter = textFontList
        val changeSet: Button = textChangeView.findViewById(R.id.change_set)
        changeSet.setOnClickListener(View.OnClickListener
        {
            val typeface = ResourcesCompat.getFont(this, R.font.montserrat_medium)
            bodyText.setTypeface(typeface)
            var textID: Int = 0
            when (textID) {
                1 -> {
                    bodyText.setTypeface(
                        ResourcesCompat.getFont(
                            this,
                            R.font.dancingscript_regular
                        )
                    )
                }
                2 -> {
                    bodyText.setTypeface(
                        ResourcesCompat.getFont(
                            this,
                            R.font.lovely_mugs
                        )
                    )
                }
                3 -> {

                    bodyText.setTypeface(
                        ResourcesCompat.getFont(
                            this,
                            R.font.magnolia_light
                        )
                    )
                }
                4 -> {

                    bodyText.setTypeface(
                        ResourcesCompat.getFont(
                            this,
                            R.font.mommy_cupcakes
                        )
                    )
                }
                5 -> {

                    bodyText.setTypeface(
                        ResourcesCompat.getFont(
                            this,
                            R.font.montserrat_medium
                        )
                    )
                }
                6 -> {

                    bodyText.setTypeface(
                        ResourcesCompat.getFont(
                            this,
                            R.font.montserrat_semibold
                        )
                    )
                }
                7 -> {

                    bodyText.setTypeface(
                        ResourcesCompat.getFont(
                            this,
                            R.font.montserrat_regular
                        )
                    )
                }
                8 -> {

                    bodyText.setTypeface(
                        ResourcesCompat.getFont(
                            this,
                            R.font.playfair_display_black_italic
                        )
                    )
                }
                9 -> {

                    bodyText.setTypeface(
                        ResourcesCompat.getFont(
                            this,
                            R.font.playfair_display_regular
                        )
                    )
                }
            }
            alertDialogBuilder.dismiss()

        })
        val changeCancel: Button = textChangeView.findViewById(R.id.change_cancel)
        changeCancel.setOnClickListener(View.OnClickListener {
            alertDialogBuilder.dismiss()
        })
        alertDialogBuilder.show()
    }

    //   to change background color of the list
    private var bgColor = BottomNavigationAdapter(
        this,
        listOf(
            BottomNavigationData(2, 1, R.color.bg_clr1),
            BottomNavigationData(2, 1, R.color.bg_clr2),
            BottomNavigationData(2, 1, R.color.bg_clr3),
            BottomNavigationData(2, 1, R.color.bg_clr4),
            BottomNavigationData(2, 1, R.color.bg_clr5),
            BottomNavigationData(2, 1, R.color.bg_clr6),
            BottomNavigationData(2, 1, R.color.bg_clr7),
            BottomNavigationData(2, 1, R.color.bg_clr8)

        )
    )

    fun changeBGColorDialog(bodyBG: ConstraintLayout, titleBG: EditText, textBG: EditText) {
        var changeDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change, null)
        var changeAlertBuilder = AlertDialog.Builder(this)
            .setView(changeDialogView)
        var alertDialogBuilder = changeAlertBuilder.create()

        val textTitle: TextView = changeDialogView.findViewById(R.id.change_title)
        textTitle.visibility = View.VISIBLE
        textTitle.text = "Change Background Color"
        val bgRecyclerView = changeDialogView.findViewById<RecyclerView>(R.id.change_RV)
        bgRecyclerView.adapter = bgColor
        bgRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        bgRecyclerView.setHasFixedSize(true)
        val changeSet: Button = changeDialogView.findViewById(R.id.change_set)
        changeSet.setOnClickListener(View.OnClickListener
        {

            var colorId: Int = 0
            when (colorId) {
                1 -> {
                    bodyBG.setBackgroundResource(R.color.bg_clr1)
                    titleBG.setBackgroundResource(R.color.bg_clr1)
                    textBG.setBackgroundResource(R.color.bg_clr1)
                }
                2 -> {
                    bodyBG.setBackgroundResource(R.color.bg_clr2)
                    titleBG.setBackgroundResource(R.color.bg_clr2)
                    textBG.setBackgroundResource(R.color.bg_clr2)
                }
                3 -> {
                    bodyBG.setBackgroundResource(R.color.bg_clr3)
                    titleBG.setBackgroundResource(R.color.bg_clr3)
                    textBG.setBackgroundResource(R.color.bg_clr3)
                }
                4 -> {
                    bodyBG.setBackgroundResource(R.color.bg_clr4)
                    titleBG.setBackgroundResource(R.color.bg_clr4)
                    textBG.setBackgroundResource(R.color.bg_clr4)
                }
                5 -> {
                    bodyBG.setBackgroundResource(R.color.bg_clr5)
                    titleBG.setBackgroundResource(R.color.bg_clr5)
                    textBG.setBackgroundResource(R.color.bg_clr5)
                }
                6 -> {
                    bodyBG.setBackgroundResource(R.color.bg_clr6)
                    titleBG.setBackgroundResource(R.color.bg_clr6)
                    textBG.setBackgroundResource(R.color.bg_clr6)
                }
                7 -> {
                    bodyBG.setBackgroundResource(R.color.bg_clr7)
                    titleBG.setBackgroundResource(R.color.bg_clr7)
                    textBG.setBackgroundResource(R.color.bg_clr7)
                }
                8 -> {
                    bodyBG.setBackgroundResource(R.color.bg_clr8)
                    titleBG.setBackgroundResource(R.color.bg_clr8)
                    textBG.setBackgroundResource(R.color.bg_clr8)
                }
                else -> {
                    bodyBG.setBackgroundResource(R.color.bg_clr1)
                    titleBG.setBackgroundResource(R.color.bg_clr1)
                    textBG.setBackgroundResource(R.color.bg_clr1)
                }
            }
            alertDialogBuilder.dismiss()

        })
        val changeCancel: Button = changeDialogView.findViewById(R.id.change_cancel)
        changeCancel.setOnClickListener(View.OnClickListener {
            alertDialogBuilder.dismiss()
        })
        alertDialogBuilder.show()
    }

    //to change text/title color
    private var textColor = BottomNavigationAdapter(
        this,
        listOf(
            BottomNavigationData(2, 1, R.color.txt_clr1),
            BottomNavigationData(2, 1, R.color.txt_clr2),
            BottomNavigationData(2, 1, R.color.txt_clr3),
            BottomNavigationData(2, 1, R.color.txt_clr4),
            BottomNavigationData(2, 1, R.color.txt_clr5),
            BottomNavigationData(2, 6, R.color.txt_clr6)
        )
    )

    fun changeTextColor(changeTitle: String, titleORText: EditText) {
        var changeDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change, null)
        var changeAlertBuilder = AlertDialog.Builder(this)
            .setView(changeDialogView)
        var alertDialogBuilder = changeAlertBuilder.create()

        val textTitle: TextView = changeDialogView.findViewById(R.id.change_title)
        textTitle.visibility = View.VISIBLE
        textTitle.text = changeTitle
        val txtClrRecyclerView = changeDialogView.findViewById<RecyclerView>(R.id.change_RV)
        txtClrRecyclerView.adapter = textColor
        txtClrRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        txtClrRecyclerView.setHasFixedSize(true)
        val changeSet: Button = changeDialogView.findViewById(R.id.change_set)
        changeSet.setOnClickListener(View.OnClickListener
        {
            var colorId = 0
            when (colorId) {
                1 -> {
                    titleORText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr1))
                }
                2 -> {
                    titleORText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr2))
                }
                3 -> {
                    titleORText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr3))
                }
                4 -> {
                    titleORText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr4))
                }
                5 -> {
                    titleORText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr5))
                }
                6 -> {
                    titleORText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr6))
                }
                else -> {
                    titleORText.setTextColor(ContextCompat.getColor(this, R.color.txt_clr1))
                }
            }
            alertDialogBuilder.dismiss()

        })
        val changeCancel: Button = changeDialogView.findViewById(R.id.change_cancel)
        changeCancel.setOnClickListener(View.OnClickListener {
            alertDialogBuilder.dismiss()
        })
        alertDialogBuilder.show()

    }

    //  toolbar back button
    fun onBackClickTB(button: Button) {
        button.setOnClickListener(View.OnClickListener {
            onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        })
    }


    //for progress bar
    /*fun progressBarDialog()
    {
        progress= ProgressDialog(this);


        val view: View
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        view = inflater?.inflate(R.layout.progress_bar, null)!!

        val progressBar = findViewById<View>(R.id.spin_kit) as ProgressBar
        val doubleBounce: Sprite = DoubleBounce()
        progressBar.indeterminateDrawable = doubleBounce

        progress?.show()
    }*/

    //TO ADD SECRET NOTE
    fun addSecretNote(title:String, body:String)
    {
        var getCurrentDate: Date = Calendar.getInstance().time
        var formatDate: String = DateFormat.getDateInstance().format(getCurrentDate)
        var secretList: MutableMap<String, Any> = HashMap()
        secretList["secretTitle"] = title
        secretList["secretBody"] = body
        secretList["secretDate"] = formatDate
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email
        secretList["userEmail"] = personEmail.toString()
        db.collection("secretList")
            .add(secretList)
            .addOnSuccessListener {
                baseBinding.logFun("fireBase add success ", "Data saved")
                Toast.makeText(this, "Successfully Added in Secret List", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                baseBinding.logFun("fireBase add failure", "Couldn't save")

            }
    }


    open fun onSNoteItemClickListner(itemNote: SecretListData, position: Int) {}
}