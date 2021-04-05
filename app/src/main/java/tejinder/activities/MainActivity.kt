package tejinder.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil.setContentView
import androidx.viewpager2.widget.ViewPager2
import com.example.worktodo.Interoslider.IntroSlider
import tejinder.Interoslider.IntroSliderAdapter
import tejinder.shearedpref.IntroSharedPref
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityIntroSliderBinding
import com.project.todolistproject.jasleen.activities.HomeLandingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityIntroSliderBinding
    lateinit var pref: IntroSharedPref
    lateinit var textView: TextView

    // IntroSlider List
    private var introSliderAdapter: IntroSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlider(
                "WELCOME",
                R.drawable.ic_logo,
                "Work To Do is a smart task list for everyday use.",
                "It is truly usable with a great user experience.",
                "Regain clarity and calmness by getting all those tasks out of your head and onto your to-do list(no matter where you are or what  device you use).",
            ),
            IntroSlider(
                "Grouping tasks in handy task lists",
                R.drawable.ic_files,
                "Quick Task Bar - to add something hot quickly",
                "Calendar View-Get a better perspective of how busy your schedule is with calendar view.",
                "Color Coding. Assign colors to each task list to make it easier to identify which lists you are viewing\n" +
                        "Each task can have detailed notes, and many more......"
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = setContentView(this, R.layout.activity_intro_slider)
        pref = IntroSharedPref(this)
        // calling of introSlider Adapter
        dataBinding.introSliderViewPager.adapter = introSliderAdapter
        setSlideIndicator()
        currentSlideIndicator(0)
        dataBinding.introSliderViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentSlideIndicator(position)
                }
            }
        )
        dataBinding.skipBtn.setOnClickListener {
            chooseThemeDialog()
            checkTheme()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        dataBinding.NextBtn.setOnClickListener {
            startActivity(Intent(this, HomeLandingActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        if (pref.isFirstRun()) {
            showIntroSlides()
        } else {
            startActivity(Intent(this, HomeLandingActivity::class.java))
        }
    }

    //for first time launch
    fun showIntroSlides() {
        pref.setFirstRun()
    }

    // introSlider Indicator
    fun setSlideIndicator() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(10, 0, 10, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.intro_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            dataBinding.sliderIndicator.addView(indicators[i])
        }
    }

    fun currentSlideIndicator(index: Int) {
        val childCount = dataBinding.sliderIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = dataBinding.sliderIndicator[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.intro_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.intro_inactive
                    )
                )
            }
        }
    }

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
        when (IntroSharedPref(this).darkMode) {
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

