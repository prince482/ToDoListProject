package com.project.todolistproject.jasleen.fragments

import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.ClipDrawable.VERTICAL
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.events.calendar.utils.EventsCalendarUtil
import com.events.calendar.utils.EventsCalendarUtil.selectedDate
import com.events.calendar.views.EventsCalendar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.project.todolistproject.databinding.FragmentCalenderBinding
import com.project.todolistproject.jasleen.adapter.EventCalenderAdapter
import com.project.todolistproject.jasleen.model.EventCalender
import java.util.*

/**
 * Created by Jasleen Kaur on 23-02-2021.
 */
class CalenderFragment : Fragment(), EventsCalendar.Callback
{
    private lateinit var calenderBinding: FragmentCalenderBinding
    private var docRef = FirebaseFirestore.getInstance().collection("ToDoListData")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        calenderBinding = FragmentCalenderBinding.inflate(inflater)
        return calenderBinding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getAndShowData()
        // selected.text = getDateString(calenderBinding.eventsCalendar.getCurrentSelectedDate()?.timeInMillis)
        val today = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 2)
        calenderBinding.eventsCalendar.setSelectionMode(calenderBinding.eventsCalendar.MULTIPLE_SELECTION)
            .setToday(today)
            .setMonthRange(today, end)
            .setWeekStartDay(Calendar.SUNDAY, false)
            .setIsBoldTextOnSelectionEnabled(true)
//            .setDatesTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_REGULAR, this))
//            .setMonthTitleTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_SEMIBOLD, this))
//            .setWeekHeaderTypeface(FontsManager.getTypeface(FontsManager.OPENSANS_SEMIBOLD, this))
            .setCallback(this)
            .build()


        /*  calenderBinding.eventsCalendar.setSelectionMode(calenderBinding.eventsCalendar.MULTIPLE_SELECTION) //set mode of Calendar
              .setToday(today) //set today's date [today: Calendar]
              .setMonthRange(today, end) //set starting month [start: Calendar] and ending month [end: Calendar]
              .setWeekStartDay(Calendar.SUNDAY, false) //set start day of the week as you wish [s:day: Int, doReset: Boolean]
              .setCurrentSelectedDate(today) //set current date and scrolls the calendar to the corresponding month of the selected date [today: Calendar]
  //            .setDatesTypeface(typeface) //set font for dates
              .setDateTextFontSize(16f) //set font size for dates
  //            .setMonthTitleTypeface(typeface) //set font for title of the calendar
              .setMonthTitleFontSize(16f) //set font size for title of the calendar
  //            .setWeekHeaderTypeface(typeface) //set font for week names
              .setWeekHeaderFontSize(16f) //set font size for week names
              .setCallback(this) //set the callback for EventsCalendar
             // .addEvent(c) //set events on the EventsCalendar [c: Calendar]
             // .disableDate(dc) //disable a specific day on the EventsCalendar [c: Calendar]
              .disableDaysInWeek(Calendar.SATURDAY, Calendar.SUNDAY) //disable days in a week on the whole EventsCalendar [varargs days: Int]
              .build()*/

        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 2)
        calenderBinding.eventsCalendar.addEvent(c)
        c.add(Calendar.DAY_OF_MONTH, 3)
        calenderBinding.eventsCalendar.addEvent(c)
        c.add(Calendar.DAY_OF_MONTH, 4)
        calenderBinding.eventsCalendar.addEvent(c)
        c.add(Calendar.DAY_OF_MONTH, 7)
        calenderBinding.eventsCalendar.addEvent(c)
        c.add(Calendar.MONTH, 1)
        c[Calendar.DAY_OF_MONTH] = 1
        calenderBinding.eventsCalendar.addEvent(c)

        calenderBinding.selected.setOnClickListener {
            val dates = calenderBinding.eventsCalendar.getDatesFromSelectedRange()
            Log.e("SELECTED SIZE", dates.size.toString())
        }

        //   calenderBinding.selected.typeface = FontsManager.getTypeface(FontsManager.OPENSANS_SEMIBOLD, this)

        val dc = Calendar.getInstance()
        dc.add(Calendar.DAY_OF_MONTH, 2)
    }

    private fun getDateString(time: Long?): String {
        if (time != null) {
            val cal = Calendar.getInstance()
            cal.timeInMillis = time
            val month = when (cal[Calendar.MONTH]) {
                Calendar.JANUARY -> "January"
                Calendar.FEBRUARY -> "February"
                Calendar.MARCH -> "March"
                Calendar.APRIL -> "April"
                Calendar.MAY -> "May"
                Calendar.JUNE -> "June"
                Calendar.JULY -> "July"
                Calendar.AUGUST -> "August"
                Calendar.SEPTEMBER -> "September"
                Calendar.OCTOBER -> "October"
                Calendar.NOVEMBER -> "November"
                Calendar.DECEMBER -> "December"
                else -> ""
            }
            return "$month ${cal[Calendar.DAY_OF_MONTH]}, ${cal[Calendar.YEAR]}"
        } else return ""
    }

    override fun onDayLongPressed(selectedDate: Calendar?) {
        Log.e(
            "LONG CLICKED",
            EventsCalendarUtil.getDateString(selectedDate, EventsCalendarUtil.DD_MM_YYYY)
        )

    }

    override fun onDaySelected(selectedDate: Calendar?) {
        //  Toast.makeText(context, "Date-: $selectedDate" , Toast.LENGTH_SHORT).show()
        // startActivity(Intent(context,EventsCalendar::class.java))
    }

    override fun onMonthChanged(monthSthisDate: Calendar?) {
        Log.e(
            "CLICKED",
            EventsCalendarUtil.getDateString(selectedDate, EventsCalendarUtil.DD_MM_YYYY)
        )
        //selected.text = getDateString(selectedDate?.timeInMillis)

    }

    private fun getAndShowData() {

        val eventList = ArrayList<EventCalender>()
        val acct = GoogleSignIn.getLastSignedInAccount(context)
        val personEmail = acct?.email
        docRef.whereEqualTo("email", personEmail)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        val eventDate = document.data["date"].toString()
                        val EventBody = document.data["eventBody"].toString()
                        val EventTime = document.data["time"].toString()
                        val personMail = document.data["email"].toString()
                        eventList.add(
                            EventCalender(calenderEvent = EventBody,date = eventDate , currentTime = EventTime)
                        )
                        calenderBinding?.calenderRecyclerView.adapter = EventCalenderAdapter(context ,eventList)
                    }

                    /*  var layoutManager = LinearLayoutManager(context, )
                      calenderBinding!!.calenderRecyclerView.layoutManager = layoutManager
                //      calenderBinding!!.calenderRecyclerView.setHasFixedSize(true)*/

                    // calenderBinding!!.searchET.setOnEditorActionListener( //done symbol
//                    fun(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
//                        if (actionId == EditorInfo.IME_ACTION_DONE) {
//                            docRef.orderBy("listTitle", Query.Direction.ASCENDING).limit(10)
//                                .startAt(calenderBinding!!.searchET.text.toString())
//                                .endAt(homeBinding!!.searchET.text.toString() + "\uf8ff")
//                        }
//                        return false
//                    })
                    Log.e("Success", "Success")
                } else {
                    Log.e("Failer", "Failer")
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}