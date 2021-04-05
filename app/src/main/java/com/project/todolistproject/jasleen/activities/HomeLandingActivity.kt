package com.project.todolistproject.jasleen.activities

/**
 * Created by Jasleen Kaur on 23-02-2021.
 */
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityHomeLandingBinding
import com.project.todolistproject.jasleen.adapter.FragmentViewPagerAdapter
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap

class HomeLandingActivity : BaseFunActivity() {
    private lateinit var homeBinding: ActivityHomeLandingBinding
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var mPagerViewAdapter: FragmentViewPagerAdapter
    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallBack: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    baseBinding.toastMessage("Authentication Error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        this@HomeLandingActivity,
                        "Authentication Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    var secretListOpen =
                        Intent(this@HomeLandingActivity, SecretListActivity::class.java)
                    startActivity(secretListOpen)
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_landing)
        setSupportActionBar(homeBinding.homeTB.findViewById(R.id.custom_toolbar))

        // var serchGp = homeBinding.homeTB.findViewById(R.id.serachGP) as Group

        //to get userName and userProfilePicture
        var profile: ImageView = homeBinding.homeTB.findViewById(R.id.user_logo_IV)
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl
            val isPhoto: Boolean = true

            homeBinding.homeTB.findViewById<TextView>(R.id.user_name_TV).text = personName
            Glide.with(this).load(personPhoto).placeholder(R.drawable.ic_login_logo).into(profile)
        } /*else {
            Glide.with(this).load(R.drawable.ic_login_logo).into(profile)
        }*/
        //to open deleted folder
        var newFolderClick: Button = homeBinding.homeTB.findViewById(R.id.delete_BTN)
        newFolderClick.setOnClickListener(View.OnClickListener {
            openDeletedFolder()
        })
        menuToolBarAction()
        //search()
        bottomFragment()

        checkBioMetricSupport()

    }

    private fun checkBioMetricSupport(): Boolean {
        val keyGuardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyGuardManager.isKeyguardSecure) {
            baseBinding.toastMessage("Fingerprint authentication has not been enabled in settings.")
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            baseBinding.toastMessage("Fingerprint authentication permission is not enabled.")
            return false
        }
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        }
        return true

    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            baseBinding.toastMessage("Authentication as cancelled by user.")
        }
        return cancellationSignal as CancellationSignal
    }


    //plus button(NEW TASK OPTIONS)
    fun openNewTaskOptions(view: View) {
        //dialog_new
        val noteDialog = LayoutInflater.from(this).inflate(R.layout.dialog_new, null)
        val noteBuilder = AlertDialog.Builder(this)
            .setView(noteDialog)

        //views of dialog_new
        var title: TextView = noteDialog.findViewById(R.id.new_title)
        var list: TextView = noteDialog.findViewById(R.id.new_note)
        var grocery: TextView = noteDialog.findViewById(R.id.new_grocery)
        var meeting: TextView = noteDialog.findViewById(R.id.new_meeting)
        var event: TextView = noteDialog.findViewById(R.id.new_event)
        var cancelBtn: Button = noteDialog.findViewById(R.id.new_cancel)

        var alertNoteBuilder = noteBuilder.create()

        list.setOnClickListener(View.OnClickListener
        {
            //open simple task activity
            startActivity(Intent(this, ListActivity::class.java))
            finish()
            alertNoteBuilder.dismiss()

        })

        grocery.setOnClickListener(View.OnClickListener
        {
            //open grocery task activity

            startActivity(Intent(this, GroceryListActivity::class.java))
            finish()
            alertNoteBuilder.dismiss()
        })

        meeting.setOnClickListener(View.OnClickListener
        {
            //open meeting activity
            Toast.makeText(this, "Schedule Meeting Selected", Toast.LENGTH_LONG).show()
            alertNoteBuilder.dismiss()

        })

        event.setOnClickListener(View.OnClickListener
        {
            //open event on calender activity
            startActivity(Intent(this, EventActivity::class.java))
            alertNoteBuilder.dismiss()

        })
        cancelBtn.setOnClickListener(View.OnClickListener { alertNoteBuilder.dismiss() })
        alertNoteBuilder.show()
    }

    //Custom Menu New Folder Options
    private fun openDeletedFolder() {
        //deleted fragment will be opened
        var deleteIntent=Intent(this,DeleteListActivity::class.java)
        deleteIntent.putExtra("deleteAct","")
        startActivity(deleteIntent)
        finish()
    }

    //Custom Menu Three dot Options
    @RequiresApi(Build.VERSION_CODES.P)
    private fun menuToolBarAction() {

        //sharedPref for log out
        lateinit var mGoogleSignInClient: GoogleSignInClient
        val auth by lazy {
            FirebaseAuth.getInstance()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        homeBinding.homeTB.findViewById<Button>(R.id.menu_BTN).setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.imp_notes -> {
                        logFun("imp_notes", "important notes clicked")
                        var impIntent=Intent(this,ImportantListActivity::class.java)
                        impIntent.putExtra("impAct","")
                        startActivity(impIntent)
                        finish()
                        true
                    }
                    R.id.s_notes -> {
                        baseBinding.logFun("S.note", "Coming in secret note")
                        val biometricPrompt = BiometricPrompt.Builder(this)
                            .setTitle("User Authentication ")
                            .setSubtitle("Verification Required")
                            .setDescription("Before we continue, please verify yourself so as to keep your data secure.")
                            .setNegativeButton(
                                "Cancel",
                                this.mainExecutor,
                                DialogInterface.OnClickListener { dialog, which ->
                                    baseBinding.toastMessage("Authentication cancelled")
                                }).build()
                        baseBinding.logFun("after build", "wertyui")
                        biometricPrompt.authenticate(
                            getCancellationSignal(),
                            mainExecutor,
                            authenticationCallBack
                        )

                        true
                    }
                    R.id.log_out -> {
                        mGoogleSignInClient.signOut().addOnCompleteListener {
                            val intent = Intent(this, LogInActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        true
                    }
                    else -> {
                        logFun("menu Error", "Error")
                        baseBinding.toastMessage("Error")
                        false
                    }

                }
            }
            popupMenu.inflate(R.menu.three_dots_menu)
            popupMenu.show()
        }
    }

    //BOTTOM NAVIGATION BAR
    //home button
    fun onClickHomeButton(view: View) {
        homeBinding.fragmentVP.currentItem = 0
    }

    //calender button
    fun onCalenderButtonClick(view: View) {

        homeBinding.fragmentVP.currentItem = 1
    }

    //setting button
    fun openSettings(view: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    //quick note
    fun bttmQuickNote(view: View) {

        homeBinding.quickIV.setBackgroundResource(R.drawable.ic_quick_note)
        val quickNoteView = LayoutInflater.from(this).inflate(R.layout.quick_note, null)
        val alertBuilder = AlertDialog.Builder(this)
            .setView(quickNoteView)

        var noteTitle: EditText = quickNoteView.findViewById(R.id.quick_title)
        var noteBody: EditText = quickNoteView.findViewById(R.id.quick_body)
        var noteDone: Button = quickNoteView.findViewById(R.id.quick_done)
        var noteCancel: Button = quickNoteView.findViewById(R.id.quick_cancel)

        var alertDialogBuilder = alertBuilder.create()

        noteDone.setOnClickListener(View.OnClickListener
        {
            //adding quick note to the database
            if(TextUtils.isEmpty(noteTitle.text.toString().trim()) && TextUtils.isEmpty(noteBody.text.toString().trim()))
            {
                baseBinding.dialogBoxBtn(
                    "Empty field",
                    "Can't save the note because the title and body of note is empty.",
                    "Cancel",
                )
            }

           else if(TextUtils.isEmpty(noteTitle.text.toString().trim()))
            {
                baseBinding.dialogBoxBtn(
                    "Empty field",
                    "Title of the note is empty. Kindly give title to the note",
                    "Cancel",
                )
            }
            else if(TextUtils.isEmpty(noteBody.text.toString().trim()))
            {
                baseBinding.dialogBoxBtn(
                    "Empty field",
                    "Can't save the note without body",
                    "Cancel")
            }
            else{
                quickFireStoreAddData(noteTitle.text.toString(), noteBody.text.toString())
            }

            alertDialogBuilder.dismiss()

        })
        noteCancel.setOnClickListener(View.OnClickListener
        {
            //will dismiss() the dialog w/o saving
            alertDialogBuilder.dismiss()

        })
        alertDialogBuilder.show()

    }

    //add quick note data into firebase
    private fun quickFireStoreAddData(title: String, body: String) {

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
                Toast.makeText(this, "Sucessfully Added", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                baseBinding.logFun("fireBase add failure", "Couldn't save")
            }
    }

    //bottom navigation fragment(home, calender and search)
    private fun bottomFragment() {
        // baseBinding.progressBarDialog()
        homeBinding.fragmentVP.visibility = View.VISIBLE
        mPagerViewAdapter = FragmentViewPagerAdapter(supportFragmentManager)
        homeBinding.fragmentVP.adapter = mPagerViewAdapter
        homeBinding.fragmentVP.offscreenPageLimit = 3
        homeBinding.fragmentVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                changeTabs(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        homeBinding.fragmentVP.currentItem = 0
        homeBinding.homeIV.setBackgroundResource(R.drawable.ic_home_onselect)

    }

    private fun changeTabs(position: Int) {


        if (position == 0) {
            homeBinding.homeIV.setBackgroundResource(R.drawable.ic_home_onselect)
            homeBinding.calenderIV.setBackgroundResource(R.drawable.ic_calendar)
            homeBinding.quickIV.setBackgroundResource(R.drawable.ic_quick_note)
            homeBinding.settingIV.setBackgroundResource(R.drawable.ic_settings)
        }
        if (position == 1) {
            homeBinding.homeIV.setBackgroundResource(R.drawable.ic_home)
            homeBinding.calenderIV.setBackgroundResource(R.drawable.ic_calendar_onselect)
            homeBinding.quickIV.setBackgroundResource(R.drawable.ic_quick_note)
            homeBinding.settingIV.setBackgroundResource(R.drawable.ic_settings)

        }
    }


}