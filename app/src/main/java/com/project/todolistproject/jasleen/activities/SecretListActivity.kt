package com.project.todolistproject.jasleen.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivitySecretListBinding
import com.project.todolistproject.jasleen.adapter.SecretListAdapter
import com.project.todolistproject.jasleen.model.SecretListData

class SecretListActivity : BaseFunActivity(), SecretListAdapter.OnSNoteItemClickListner {
    private lateinit var secretListBinding: ActivitySecretListBinding
    private var db = FirebaseFirestore.getInstance()
    private var docRef = FirebaseFirestore.getInstance().collection("secretList")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secretListBinding = DataBindingUtil.setContentView(this, R.layout.activity_secret_list)
        secretListBinding.secRefresh.setOnRefreshListener {
            showSecretList()
        }
        showSecretList()
    }

    //show secret list
    private fun showSecretList() {
        val listData = ArrayList<SecretListData>()
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email

        docRef.whereEqualTo("userEmail", personEmail)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    for (document in it.result!!) {

                        val sListTitle = document.data["secretTitle"].toString()
                        val sListBody = document.data["secretBody"].toString()
                        val sListDate = document.data["secretDate"].toString()
                        val personMail = document.data["userEmail"].toString()
                        val sId = document.reference.id
                        listData.add(
                            SecretListData(
                                sTitle = sListTitle,
                                sBody = sListBody,
                                sDate = sListDate,
                                sId = sId,
                            )
                        )
                    }
                    var rvLayoutType = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    secretListBinding.secretRV.layoutManager = rvLayoutType
                    secretListBinding.secretRV.setHasFixedSize(true)
                    setAdapter(listData)
                    secretListBinding.secRefresh.isRefreshing = false
                    /*secretListBinding!!.secretNoteSearchET.setOnEditorActionListener( //done symbol
                        fun(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                docRef.orderBy("secretTitle")
                                    .startAt(secretListBinding!!.secretNoteSearchET.text.toString())
                                    .endAt(secretListBinding!!.secretNoteSearchET.text.toString() + "\uf8ff")
                            }
                            return false
                        })*/
                    Log.e("Success", "Success")
                } else {
                    Log.e("Failer", "Failer")
                }
            }
    }

    //delete the list in RV
    fun deleteList(sDocID: String) {
        val secretListData = ArrayList<SecretListData>()
        db.collection("secretList").document(sDocID)
            .delete()
            .addOnSuccessListener {
                showSecretList()
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }


    }

    //setting Rv adapter
    private fun setAdapter(sListData: ArrayList<SecretListData>) {
        secretListBinding.secretRV.adapter = SecretListAdapter(this, sListData, this, this)
    }

    //to open particular list
    override fun onSNoteItemClick(itemNote: SecretListData, position: Int) {
        val showScrtList = Intent(this, OnClickShowSecretListActivity::class.java)
        showScrtList.putExtra("secretTitle", itemNote.sTitle)
        showScrtList.putExtra("secretBody", itemNote.sBody)
        startActivity(showScrtList)
    }

    fun onScrtBackClick(view: View) {
        onBackPressed()
    }
}