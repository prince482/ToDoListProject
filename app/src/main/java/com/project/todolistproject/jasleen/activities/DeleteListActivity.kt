package com.project.todolistproject.jasleen.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityDeleteListBinding
import com.project.todolistproject.jasleen.adapter.DeleteListAdapter
import com.project.todolistproject.jasleen.model.HomeNotesData

class DeleteListActivity : AppCompatActivity() {

    private lateinit var deleteBinding: ActivityDeleteListBinding
    private var delDocRef = FirebaseFirestore.getInstance().collection("deleteListData")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deleteBinding = DataBindingUtil.setContentView(this, R.layout.activity_delete_list)
        getAndShowData()

    }

    fun onDltBckBtn(view: View) {
        startActivity(Intent(this, HomeLandingActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    fun deletePrmntly(docId:String)
    {
        deletePermntly(docId)
    }
    //for delete list
    private fun getAndShowData() {
        val listDeleteData = ArrayList<HomeNotesData>()
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email
        delDocRef.whereEqualTo("email", personEmail)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        val noteTitle = document.data["listTitle"].toString()
                        val noteBody = document.data["listBody"].toString()
                        val noteTime = document.data["listTime"].toString()
                        val personMail = document.data["email"].toString()
                        val id = document.reference.id
                        listDeleteData.add(
                            HomeNotesData(
                                title = noteTitle,
                                body = noteBody,
                                date = noteTime,
                                id = id
                            )
                        )
                        Log.e("Success", "List added sucessfully in the RV adapter")
                    }
                    var rvLayoutType = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    deleteBinding!!.deleteNotesRV.layoutManager = rvLayoutType
                    deleteBinding!!.deleteNotesRV.setHasFixedSize(true)
                    setDeleteAdapter(listDeleteData)
                    /*homeBinding!!.searchET.setOnEditorActionListener( //done symbol
                        fun(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                    docRef.orderBy("listTitle")
                                                .startAt(query)
                                                .endAt(query + "\uf8ff")
                            }
                            return false
                        })*/

                    //FOR SEARCHING
                    /* var searchList:SearchView=homeBinding!!.searchET as SearchView
                     searchList.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                         override fun onQueryTextSubmit(query: String?): Boolean {
                             processSearchByTitle(query.toString())
                             return true
                         }

                         override fun onQueryTextChange(newText: String?): Boolean {
                             processSearchByTitle(newText.toString())
                             return true
                         }

                     })*/
                    Log.e("adapter", "Rv adapter working")

                } else {
                    Log.e("Failer", "List can't be added in RV adapter")
                }
            }
    }

    //will delete the list permanently
    private fun deletePermntly(sDocID:String)
    {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_box, null)
        val alertBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        var dialogTitle: TextView = dialogView.findViewById(R.id.error_title_Tv)
        var dialogMessage: TextView = dialogView.findViewById(R.id.error_msg_Tv)
        var dialogButton1: Button = dialogView.findViewById(R.id.dialog_BTN1)
        var dialogButton2: Button = dialogView.findViewById(R.id.dialog_BTN2)
        dialogTitle.text = "Delete permanently"
        dialogMessage.text = "Do you want to delete the List permanently.\n On click on OK button, you won't be able to restore the list."
        dialogButton1.text = "OK"
        dialogButton2.text="Cancel"
        var alertDialogBuilder = alertBuilder.create()
        dialogButton1.setOnClickListener(View.OnClickListener
        {
            val ListDataDelete = ArrayList<HomeNotesData>()
            FirebaseFirestore.getInstance().collection("deleteListData").document(sDocID)
                .delete()
                .addOnSuccessListener { //call adapter
                    getAndShowData()
                }
                .addOnFailureListener { e ->
                    Log.w(
                        ContentValues.TAG,
                        "Error deleting document",
                        e
                    )
                }
            alertDialogBuilder.dismiss()
        })
        dialogButton2.setOnClickListener{
            alertDialogBuilder.dismiss()
        }
        alertDialogBuilder.show()
    }

    private fun setDeleteAdapter(deletelstData: ArrayList<HomeNotesData>) {
        deleteBinding.deleteNotesRV.adapter = DeleteListAdapter(this, deletelstData, this)
    }
}