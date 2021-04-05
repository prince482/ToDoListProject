package com.project.todolistproject.jasleen.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.todolistproject.R
import com.project.todolistproject.databinding.RvSecretNoteItemsBinding
import com.project.todolistproject.jasleen.activities.SecretListActivity
import com.project.todolistproject.jasleen.model.SecretListData

/**
 * Created by Jasleen Kaur on 22-03-2021.
 */
class SecretListAdapter(
    val context: Context?,
    val secretListData: List<SecretListData>,
    var onClickListner: OnSNoteItemClickListner,
    var act: SecretListActivity
) :
    RecyclerView.Adapter<SecretListAdapter.SecretViewHolder>() {

    inner class SecretViewHolder(secretItem: RvSecretNoteItemsBinding) :
        RecyclerView.ViewHolder(secretItem.root) {
        var secretBinding: RvSecretNoteItemsBinding = secretItem

        fun secretInitialize(secretItem: SecretListData, sNoteOpen: OnSNoteItemClickListner
        ) {
            secretBinding.scrtTitleTV.text = secretItem.sTitle
            secretBinding.scrtBodyTV.text = secretItem.sBody
            secretBinding.scrtBodyTV.setOnClickListener(View.OnClickListener {
                sNoteOpen.onSNoteItemClick(secretItem, adapterPosition)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecretViewHolder {
        var secretInflaterLayout = LayoutInflater.from(parent.context)
        var secretBinding = DataBindingUtil.inflate<RvSecretNoteItemsBinding>(
            secretInflaterLayout,
            R.layout.rv_secret_note_items,
            parent,
            false
        )

        return SecretViewHolder(secretBinding)
    }

    override fun onBindViewHolder(holder: SecretViewHolder, position: Int) {
        var getSecretData=secretListData[position]
        holder.secretBinding.scrtTitleTV.text=getSecretData.sTitle
        holder.secretBinding.scrtTitleTV.setTextColor(Color.WHITE)
        holder.secretBinding.scrtBodyTV.text=getSecretData.sBody
        holder.secretBinding.scrtBodyTV.setTextColor(Color.WHITE)
        holder.secretBinding.scrtCreatedTimeTV.text=getSecretData.sDate
        holder.secretBinding.scrtCreatedTimeTV.setTextColor(Color.WHITE)
        holder.secretInitialize(secretListData[position], onClickListner)
        holder.secretBinding.scrtDelteBTN.setOnClickListener(View.OnClickListener {
            act.deleteList(getSecretData.sId)
            Toast.makeText(context,"Successfully deleted the list.", Toast.LENGTH_LONG).show()
            Log.e("note_delete", "note_delete")
        })
    }

    override fun getItemCount() = secretListData.size

    interface OnSNoteItemClickListner {
        fun onSNoteItemClick(itemNote: SecretListData, position: Int) {

        }

    }
}