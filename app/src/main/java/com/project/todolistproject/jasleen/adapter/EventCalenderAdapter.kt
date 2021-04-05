package com.project.todolistproject.jasleen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.todolistproject.R
import com.project.todolistproject.jasleen.model.EventCalender

class EventCalenderAdapter(val context: Context?, val eventList: ArrayList<EventCalender>)
    : RecyclerView.Adapter<EventCalenderAdapter.EventNotesViewHolder>()  {
    class EventNotesViewHolder(eventItem: View) :
    RecyclerView.ViewHolder(eventItem) {
        var eventBody : TextView = eventItem.findViewById(R.id.Event_tv)
        var eventDate : TextView = eventItem.findViewById(R.id.Date_tv)
        var evetCurrentDate: TextView = eventItem.findViewById(R.id.current_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventNotesViewHolder {

        return EventNotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.show_event_items,parent,
            false))
    }
    override fun onBindViewHolder(holder: EventNotesViewHolder, position: Int) {
        var getEventData=eventList[position]
        holder.eventBody.text = getEventData.calenderEvent
        holder.eventDate.text= getEventData.date
        holder.evetCurrentDate.text=getEventData.currentTime

    }
    override fun getItemCount(): Int {
      return eventList.size
    }
}