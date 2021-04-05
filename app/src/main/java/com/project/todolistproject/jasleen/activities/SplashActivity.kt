package com.project.todolistproject.jasleen.activities

/**
 * Created by Jasleen Kaur on 18-02-2021.
 */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivitySplashBinding
    private lateinit var handler: Handler
    private var SPLASH_DISPLAY_LENGTH:Long=2000

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        //set the screen to full window
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        /*handler=Handler()
        handler.postDelayed(
            {
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            },SPLASH_DISPLAY_LENGTH)*/
        handler=Handler()
        handler.postDelayed(
                {
                    startActivity(Intent(this, HomeLandingActivity::class.java))
                    finish()
                },SPLASH_DISPLAY_LENGTH)



    }
}