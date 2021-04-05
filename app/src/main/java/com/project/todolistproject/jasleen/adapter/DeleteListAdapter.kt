package com.project.todolistproject.jasleen.adapter

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.RvNoteItemBinding
import com.project.todolistproject.jasleen.activities.DeleteListActivity
import com.project.todolistproject.jasleen.model.HomeNotesData
import kotlin.random.Random

/**
 * Created by Jasleen Kaur on 23-03-2021.
 */
class DeleteListAdapter(
    val context: Context?,
    val noteList: ArrayList<HomeNotesData>,
    var act: DeleteListActivity
) : RecyclerView.Adapter<DeleteListAdapter.HomeNotesViewHolder>() {
    class HomeNotesViewHolder(noteItem: RvNoteItemBinding) :
        RecyclerView.ViewHolder(noteItem.root) {
        var noteBinding: RvNoteItemBinding = noteItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNotesViewHolder {
        val noteInflater = LayoutInflater.from(parent.context)
        val noteBinding = DataBindingUtil.inflate<RvNoteItemBinding>(
            noteInflater,
            R.layout.rv_note_item,
            parent,
            false
        )
        return HomeNotesViewHolder(noteBinding)
    }

    override fun onBindViewHolder(holder: HomeNotesViewHolder, position: Int) {
        var getListData = noteList[position]
        holder.noteBinding.noteTitleTV.text = getListData.title
        holder.noteBinding.noteBodyTV.text = getListData.body
        holder.noteBinding.noteCreatedTimeTV.text = getListData.date
        holder.noteBinding.noteBodyTV.setOnClickListener { act.deletePrmntly(getListData.id) }
        holder.noteBinding.noteMenuBTN.visibility=View.GONE
        holder.noteBinding.cardViewCL.setBackgroundResource(getRandomColor())
    }


    private fun getRandomColor(): Int {
        var bgColor = ArrayList<Int>()
        bgColor.add(R.color.bg_clr1)
        bgColor.add(R.color.bg_clr2)
        bgColor.add(R.color.bg_clr3)
        bgColor.add(R.color.bg_clr4)
        bgColor.add(R.color.bg_clr5)
        bgColor.add(R.color.bg_clr6)
        bgColor.add(R.color.bg_clr7)
        bgColor.add(R.color.bg_clr8)

        var random = Random
        var coloID: Int = random.nextInt(bgColor.size)

        return bgColor[coloID]
    }

    override fun getItemCount(): Int {
        return noteList.size
    }


}
