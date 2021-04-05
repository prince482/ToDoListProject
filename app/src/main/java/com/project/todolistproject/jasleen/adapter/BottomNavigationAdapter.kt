package com.project.todolistproject.jasleen.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.todolistproject.R
import com.project.todolistproject.databinding.RvChangeItemsBinding
import com.project.todolistproject.jasleen.model.BottomNavigationData

/**
 * Created by Jasleen Kaur on 26-02-2021.
 */
class BottomNavigationAdapter(var context: Context?, val dataList:List<BottomNavigationData>):RecyclerView.Adapter<BottomNavigationAdapter.BottomViewHolder>() {
    var row:Int=-1
    class BottomViewHolder(itemView: RvChangeItemsBinding):RecyclerView.ViewHolder(itemView.root)
    {
         var changeBinding: RvChangeItemsBinding

         init { this.changeBinding=itemView }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHolder {
        val changeInflater=LayoutInflater.from(parent.context)
        val changeBinding=DataBindingUtil.inflate<RvChangeItemsBinding>(changeInflater, R.layout.rv_change_items,parent,false)

        return BottomViewHolder(changeBinding)
    }

    override fun onBindViewHolder(holder: BottomViewHolder, position: Int) {

        var getChangeItems=dataList[position]
        var getId=getChangeItems.changeID
        if(getId==1)
        {
            holder.changeBinding.changeBgTxtclrTtlcolorIV.visibility= View.GONE
            holder.changeBinding.textFontChangeTV.visibility=View.VISIBLE
            holder.changeBinding.textFontChangeTV.text=getChangeItems.textFont
            holder.changeBinding.textFontChangeTV.setOnClickListener(View.OnClickListener {
                row = position
                notifyDataSetChanged()
            })
            if (row == position)
            {
                holder.changeBinding.textFontChangeTV.setBackgroundColor(Color.parseColor("#e8f7ff"))
            }
            else
            {
                holder.changeBinding.textFontChangeTV.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }
        if(getId==2)
        {
            holder.changeBinding.textFontChangeTV.visibility=View.GONE
            holder.changeBinding.changeBgTxtclrTtlcolorIV.visibility= View.VISIBLE
            holder.changeBinding.changeBgTxtclrTtlcolorIV.setImageResource(getChangeItems.color)
            holder.changeBinding.changeBgTxtclrTtlcolorIV.setOnClickListener(View.OnClickListener {
                row = position
                notifyDataSetChanged()
            })
            if (row == position)
            {
                holder.changeBinding.cardView.setBackgroundColor(Color.parseColor("#e8f7ff"))
            }
            else
            {
                holder.changeBinding.cardView.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }

    }

    override fun getItemCount(): Int=dataList.size
}