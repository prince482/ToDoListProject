package tejinder.shearedpref

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.preference.PreferenceManager


class IntroSharedPref(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCE_NAME,
        PRIVATE_MODE)
    private val editor: SharedPreferences.Editor = preferences.edit()
    private val language:String?=null
    private val DARK_STATUS = "io.github.manuelernesto.DARK_STATUS"
    var darkMode = preferences.getInt(DARK_STATUS, 44)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()
    fun isFirstRun() = preferences.getBoolean(FIRST_TIME, true)

    fun setFirstRun() {
        editor.putBoolean(FIRST_TIME, false).commit()
        editor.commit()
    }
    companion object {
        private const val PRIVATE_MODE = 0
        private const val PREFERENCE_NAME = "configuration"
        private const val FIRST_TIME = "isFirstRun"
    }

}