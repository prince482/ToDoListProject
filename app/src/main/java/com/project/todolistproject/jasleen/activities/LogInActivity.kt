package com.project.todolistproject.jasleen.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityLoginBinding
import tejinder.activities.MainActivity
import tejinder.shearedpref.IntroSharedPref

/**
 * Created by Jasleen Kaur on 19-02-2021.
 */
class LogInActivity : BaseFunActivity() {
    private lateinit var logBinding: ActivityLoginBinding
    private var mIsShowPass = false
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var pref: IntroSharedPref
    //val loading = LoadingDialog(this)
    var callbackManager = CallbackManager.Factory.create()
    private val EMAIL = "email"
    private var progressBarColor=Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        //set the screen to full window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        logBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        logBinding.progress.settype(com.fevziomurtekin.customprogress.Type.RIPPLE)
        logBinding.progress.setdurationTime(100)

        //for google integration
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // getting the value of gso inside the GoogleSigninClient
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        // initialize the firebaseAuth variable
        firebaseAuth = FirebaseAuth.getInstance()
        FacebookSdk.sdkInitialize(getApplicationContext())
        logBinding.gglSignBtn.setOnClickListener(View.OnClickListener {
            logBinding?.progress?.show()
            signInGoogle()
        })
        logBinding.fbSignBtn.setOnClickListener {
           // loading.startLoading()
            logBinding?.progress?.show()
            fbIntegration()
        }

    }

    /*FOR GOOGLE INTEGRATION*/
    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        //loading.startLoading()
        startActivityForResult(signInIntent, Req_Code)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        var toolbar: Toolbar? = findViewById(R.id.custom_toolbar)
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)!!
            startActivity(Intent(this, MainActivity::class.java))
            logBinding?.progress?.gone()
        // loading.isDismiss()
        } catch (e: ApiException) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        }
    }

    object SavedPreference {

        const val EMAIL = "email"
        const val USERNAME = "username"

        private fun getSharedPreference(ctx: Context?): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        private fun editor(context: Context, const: String, string: String) {
            getSharedPreference(
                context
            )?.edit()?.putString(const, string)?.apply()
        }
    }

    /*FOR FACEBOOK INTEGRaTION*/
    fun fbIntegration() {

        logBinding.fbSignBtn.setReadPermissions("email", "public_profile")
        logBinding.fbSignBtn.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("TAG", "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d("TAG", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("TAG", "facebook:onError", error)
            }

        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(Intent(this, MainActivity::class.java))
            logBinding?.progress?.gone()
            finish()
        }
        if(currentUser!=null)
        {
            logBinding?.progress?.gone()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("TAG", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    startActivity(Intent(this, MainActivity::class.java))
                    //loading.isDismiss()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }
            }
    }

    fun needHelp(view: View) {
        startActivity(Intent(this, NeedHelpActivity::class.java))
    }
}