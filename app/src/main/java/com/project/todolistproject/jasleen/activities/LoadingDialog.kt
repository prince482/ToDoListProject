package com.project.todolistproject.jasleen.activities

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.core.content.ContextCompat.getSystemService
import com.project.todolistproject.R
import java.security.AccessController.getContext

/**
 * Created by Jasleen Kaur on 18-03-2021.
 */
/*
class LoadingDialog(val mActivity:Activity) {

}*/

class LoadingDialog(val mActivity: Context)
{
    private lateinit var isdialog: AlertDialog
    fun startLoading()
    {

/**set View*/

val inflater: LayoutInflater? = mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

        val dialogView = inflater!!.inflate(R.layout.progress_bar_lyt, null)

/**set Dialog*/

        val bulider = AlertDialog.Builder(mActivity)
        bulider.setView(dialogView)
        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.show()
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}