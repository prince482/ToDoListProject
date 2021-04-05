package com.project.todolistproject.jasleen.adapter

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.todolistproject.R
import com.project.todolistproject.databinding.RvNoteItemBinding
import com.project.todolistproject.jasleen.fragments.HomeFragment
import com.project.todolistproject.jasleen.model.HomeNotesData
import kotlin.random.Random


/**
 * Created by Jasleen Kaur on 23-02-2021.
 */
class HomeNotesAdapter(
    val context: Context?,
    val noteList: ArrayList<HomeNotesData>,
    var clickListner: OnNoteItemClickListner,
    var frag: HomeFragment
) : RecyclerView.Adapter<HomeNotesAdapter.HomeNotesViewHolder>() {
    class HomeNotesViewHolder(noteItem: RvNoteItemBinding) :
        RecyclerView.ViewHolder(noteItem.root) {
        var noteBinding: RvNoteItemBinding = noteItem

        fun initialize(itemNote: HomeNotesData, noteOpen: OnNoteItemClickListner) {
            noteBinding.noteTitleTV.text = itemNote.title
            noteBinding.noteBodyTV.text = itemNote.body
            noteBinding.noteBodyTV.setTextColor(itemNote.txtClr.toInt())
            noteBinding.noteTitleTV.setTextColor(itemNote.titleClr.toInt())
            noteBinding.noteBodyTV.setOnClickListener{
                noteOpen.onNoteItemClick(itemNote, adapterPosition)
            }
        }
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
        val getListData = noteList[position]
        holder.noteBinding.noteTitleTV.text = getListData.title
        holder.noteBinding.noteBodyTV.text = getListData.body
        holder.noteBinding.noteCreatedTimeTV.text = getListData.date
        holder.noteBinding.noteBodyTV.setTextColor(Color.parseColor(getListData.txtClr))
        holder.noteBinding.noteTitleTV.setTextColor(Color.parseColor(getListData.titleClr))
        holder.initialize(noteList[position], clickListner)
        holder.noteBinding.noteMenuBTN.setOnClickListener{
            val popupMenu = PopupMenu(context!!, it)
            popupMenu.inflate(R.menu.note_menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.note_imp -> {
                        holder.noteBinding.noteImpIB.visibility = View.VISIBLE
                        frag.importantList(getListData.id)
                         true
                    }
                    R.id.share -> {
                       frag.shareListData(getListData.id)
                        true
                    }
                    R.id.note_delete -> {
                        frag.deleteList(getListData.id)
                        Log.e("note_delete", "note_delete")
                        true
                    }
                    else -> {
                        Log.e("Error", "Error")
                        false
                    }

                }
            }
            popupMenu.show()
        }
        holder.noteBinding.cardViewCL.setBackgroundResource(getRandomColor())
    }


    private fun getRandomColor(): Int {
        val bgColor = ArrayList<Int>()
        bgColor.add(R.color.bg_clr1)
        bgColor.add(R.color.bg_clr2)
        bgColor.add(R.color.bg_clr3)
        bgColor.add(R.color.bg_clr4)
        bgColor.add(R.color.bg_clr5)
        bgColor.add(R.color.bg_clr6)
        bgColor.add(R.color.bg_clr7)
        bgColor.add(R.color.bg_clr8)

        val random = Random
        val coloID: Int = random.nextInt(bgColor.size)

        return bgColor[coloID]
    }

    override fun getItemCount() = noteList.size

    interface OnNoteItemClickListner {
        fun onNoteItemClick(itemNote: HomeNotesData, position: Int) {

        }

    }
}
