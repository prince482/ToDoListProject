package com.project.todolistproject.jasleen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.todolistproject.R
import com.project.todolistproject.databinding.RvImpItemsBinding
import com.project.todolistproject.jasleen.activities.ImportantListActivity
import com.project.todolistproject.jasleen.fragments.HomeFragment
import com.project.todolistproject.jasleen.model.HomeNotesData
import com.project.todolistproject.jasleen.model.SecretListData
import kotlin.random.Random

/**
 * Created by Jasleen Kaur on 24-03-2021.
 */

//take the list of HomeNotesData
class ImportantAdapter(val context: Context, val impList: List<HomeNotesData>, var impClckListner: ImportantAdapter.onImpItemCLck,var act: ImportantListActivity) :
    RecyclerView.Adapter<ImportantAdapter.ImptViewHolder>() {
    inner class ImptViewHolder(impItems: RvImpItemsBinding) :
        RecyclerView.ViewHolder(impItems.root) {
        var impBinding: RvImpItemsBinding = impItems
        fun initialize(itemNote: HomeNotesData, noteOpen: ImportantAdapter.onImpItemCLck) {
            impBinding.imptTitleTV.text = itemNote.title
            impBinding.imptBodyTV.text = itemNote.body
            impBinding.imptBodyTV.setOnClickListener{
                noteOpen.onImpItemCLck(itemNote, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImptViewHolder {
        val impInflater = LayoutInflater.from(parent.context)
        val impBinding = DataBindingUtil.inflate<RvImpItemsBinding>(
            impInflater,
            R.layout.rv_imp_items,
            parent,
            false
        )

        return ImptViewHolder(impBinding)

    }

    override fun onBindViewHolder(holder: ImptViewHolder, position: Int) {
        val getItemPos = impList[position]
        holder.impBinding.impCardViewCL.setBackgroundResource(getRandomColor())
        holder.impBinding.imptTitleTV.text = getItemPos.title
        holder.impBinding.imptBodyTV.text = getItemPos.body
        holder.impBinding.imptCreatedTimeTV.text = getItemPos.date
        holder.initialize(impList[position], impClckListner)
    }

    override fun getItemCount(): Int = impList.size

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

        return bgColor.get(coloID)
    }
    interface onImpItemCLck {
        fun onImpItemCLck(itemNote: HomeNotesData, position: Int) {

        }
    }
}