package com.project.todolistproject.jasleen.activities

/**
 * Created by Jasleen Kaur on 23-02-2021.
 */
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivitySettingsBinding
import tejinder.shearedpref.IntroSharedPref
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var settingBinding: ActivitySettingsBinding

    // sheared pref for theme
    inner class MyPreferences(context: Context?) {

        private val DARK_STATUS = "io.github.manuelernesto.DARK_STATUS"

        private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        var darkMode = preferences.getInt(DARK_STATUS, 0)
            set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        settingBinding.settingBackBTN.setOnClickListener(View.OnClickListener
        {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            onBackPressed()
        })
        loadLocate()
    }

    fun userProfileClick(view: View) {
        //will go to another activity and will show the user data
        var options: Intent = Intent(this, OnClickPartSettingActivity::class.java)
        options.putExtra("user", "User Profile")
        startActivity(options)
        finish()
    }

    //for theme setting
    fun onThemeClick(view: View) {
        chooseThemeDialog()
        checkTheme()
    }

    //for language setting
    fun onLangSettingClick(view: View) {
        loadLocate()
        showChangeLang()
    }

    private fun showChangeLang() {

        var langOptions = arrayOf(
            "English",
            "हिन्दी",
            "français",
            "Español",
            "Ελληνικά",
            "Português",
            "中国人"
        )

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Choose Language")
        val checkedLang=0
        mBuilder.setSingleChoiceItems(langOptions, checkedLang) { dialog, whichLang ->
            if (whichLang == 0) {
                setLanguage("en")
                recreate()

            } else if (whichLang == 1) {
                setLanguage("hi")
                recreate()

            } else if (whichLang == 2) {
                setLanguage("fr")
                recreate()

            } else if (whichLang == 3) {
                setLanguage("es")
                recreate()

            } else if (whichLang == 4) {
                setLanguage("el")
                recreate()

            } else if (whichLang == 5) {
                setLanguage("pt")
                recreate()

            } else if (whichLang == 6) {
                setLanguage("zh")
                recreate()

            } else {
                setLanguage("en")
                recreate()

            }
            dialog.dismiss()
        }

        var mDialogShow = mBuilder.create()
        mDialogShow.show()

    }

    private fun setLanguage(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLanguage(language!!)
    }

    //  for log out
    fun onLogOutClick(view: View) {
        lateinit var mGoogleSignInClient: GoogleSignInClient
        val auth by lazy {
            FirebaseAuth.getInstance()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mGoogleSignInClient.signOut().addOnCompleteListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    /*private fun checkLanguage()
    {
        when(IntroSharedPref(this).)
        {

        }
    }*/

    // theme Selection
    private fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.settings_text_select_theme))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = 0
        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    delegate.applyDayNight()

                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }

    }
}


