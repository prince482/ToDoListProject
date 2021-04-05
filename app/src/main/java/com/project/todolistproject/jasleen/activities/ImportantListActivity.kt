package com.project.todolistproject.jasleen.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.project.todolistproject.R
import com.project.todolistproject.databinding.ActivityImportantListBinding
import com.project.todolistproject.jasleen.adapter.DeleteListAdapter
import com.project.todolistproject.jasleen.adapter.ImportantAdapter
import com.project.todolistproject.jasleen.model.HomeNotesData

/**
 * Created by Jasleen Kaur on 24-03-2021.
 */
class ImportantListActivity: AppCompatActivity(), ImportantAdapter.onImpItemCLck {

    private lateinit var imptBinding: ActivityImportantListBinding
    private var impDocRef = FirebaseFirestore.getInstance().collection("importantList")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imptBinding = DataBindingUtil.setContentView(this, R.layout.activity_important_list)
        showImportantList()
    }

    fun onImpBckBtn(view: View) {
        startActivity(Intent(this, HomeLandingActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

   //will show the all list marked as important
    private fun showImportantList() {

        val listImpData = ArrayList<HomeNotesData>()
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email
        impDocRef.whereEqualTo("email", personEmail)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        val noteTitle = document.data["listTitle"].toString()
                        val noteBody = document.data["listBody"].toString()
                        val noteTime = document.data["listTime"].toString()
                        val personMail = document.data["email"].toString()
                        val id = document.reference.id
                        listImpData.add(
                            HomeNotesData(
                                title = noteTitle,
                                body = noteBody,
                                date = noteTime,
                                id = id
                            )
                        )
                        Log.e("imp Success", "Imp List added sucessfully in the RV adapter")
                    }
                    imptBinding.impNotesRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
                    setImpAdapter(listImpData)
                    Log.e("imp adapter", "Imp Rv adapter working")

                } else {
                    Log.e("imp Failer", "Imp List can't be added in RV adapter")
                }
            }
    }

    fun setImpAdapter(impLstData: ArrayList<HomeNotesData>) {
        imptBinding.impNotesRV.adapter = ImportantAdapter(this, impLstData,this,this)
    }

    override fun onImpItemCLck(itemNote: HomeNotesData, position: Int) {
        val showNote = Intent(this, OnClickShowNotesActivity::class.java)
        showNote.putExtra("title", itemNote.title)
        showNote.putExtra("body", itemNote.body)
        startActivity(showNote)
    }

}