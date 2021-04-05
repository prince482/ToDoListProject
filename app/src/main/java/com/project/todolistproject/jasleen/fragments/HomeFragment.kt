package com.project.todolistproject.jasleen.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.*
import com.project.todolistproject.databinding.FragmentHomeBinding
import com.project.todolistproject.jasleen.activities.BaseFunActivity
import com.project.todolistproject.jasleen.activities.OnClickShowNotesActivity
import com.project.todolistproject.jasleen.adapter.HomeNotesAdapter
import com.project.todolistproject.jasleen.model.HomeNotesData


/**
 * Created by Jasleen Kaur on 23-02-2021.
 */
class HomeFragment : Fragment(), HomeNotesAdapter.OnNoteItemClickListner {
    private var homeBinding: FragmentHomeBinding? = null
    private var docRef = FirebaseFirestore.getInstance().collection("simpleListData")
    private var delDocRef = FirebaseFirestore.getInstance().collection("deleteListData")
    private var impDocRef = FirebaseFirestore.getInstance().collection("importantList")

    companion object {
        fun newInstance() = HomeFragment()
        var baseActivity = BaseFunActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater)
        return homeBinding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeBinding?.refresh?.setOnRefreshListener {
            getAndShowData()
        }
        getAndShowData()
    }

    //get and display notes on the home activity
    private fun getAndShowData() {
        val listData = ArrayList<HomeNotesData>()
        val acct = GoogleSignIn.getLastSignedInAccount(context)
        val personEmail = acct?.email
        docRef.whereEqualTo("email", personEmail)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        homeBinding?.refresh?.isRefreshing = true
                        val noteTitle = document.data["listTitle"].toString()
                        val noteBody = document.data["listBody"].toString()
                        val noteTime = document.data["listTime"].toString()
                        val personMail = document.data["email"].toString()
                        val titleClr  = document.data["titleColor"].toString()
                        val textClr =document.data["textColor"].toString()
                        val id = document.reference.id
                        listData.add(
                            HomeNotesData(
                                title = noteTitle,
                                body = noteBody,
                                date = noteTime,
                                id = id,
                                titleClr = titleClr,
                                txtClr = textClr
                            )
                        )
                        Log.e("Success", "List added successfully in the RV adapter")
                    }
                    //for searching list using title
                    homeBinding!!.searchET.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                            TODO("Not yet implemented")
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            TODO("Not yet implemented")
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }
                    })
                    var rvLayoutType = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    homeBinding!!.notesRV.layoutManager = rvLayoutType
                    homeBinding!!.notesRV.setHasFixedSize(true)
                    setAdapter(listData)
                    homeBinding?.refresh?.isRefreshing = false
                    Log.e("adapter", "Rv adapter working")

                } else {
                    Log.e("Failer", "List can't be added in RV adapter")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    //will open the particular note
    override fun onNoteItemClick(itemNote: HomeNotesData, position: Int) {
        val showNote = Intent(context, OnClickShowNotesActivity::class.java)
        showNote.putExtra("title", itemNote.title)
        showNote.putExtra("body", itemNote.body)
        startActivity(showNote)
    }

    //to delete the list from RV-> will go to the delete folder
    fun deleteList(docId: String) {
        /*homeBinding?.refresh?.setOnRefreshListener {*/
        moveToDeleteFolder(docRef.document(docId), delDocRef.document(docId))
        /* }*/
    }

    fun importantList(docId: String) {
        importantDocuments(docRef.document(docId), impDocRef.document(docId))
    }

    fun shareListData(docId: String) {
        shareData(docRef.document(docId))
    }

    //setting RV adapter
    private fun setAdapter(listData: ArrayList<HomeNotesData>) {
        homeBinding!!.notesRV.adapter = HomeNotesAdapter(context, listData, this, this)
    }

    //on click delete list, will go to the delete folder
    private fun moveToDeleteFolder(fromPath: DocumentReference, toPath: DocumentReference) {

        fromPath.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    toPath.set(document.data!!)
                        .addOnSuccessListener {
                            Log.e("written", "DocumentSnapshot successfully written!")
                            fromPath.delete()
                                .addOnSuccessListener {

                                    Log.e("deleted", "DocumentSnapshot successfully deleted!")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("error", "Error deleting document")
                                }
                            getAndShowData()
                        }
                        .addOnFailureListener { e ->
                            Log.e("Can't delete", "Error in deleting document document\"")
                        }
                } else {
                    Log.e("No such", "No such document")
                }
            } else {
                Log.e("get failed", "get failed", task.exception)
            }
        }

    }

    //when the user will mark the note as important
    private fun importantDocuments(fromPath: DocumentReference, toPath: DocumentReference) {
        fromPath.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    toPath.set(document.data!!)
                        .addOnSuccessListener {
                            Log.e("written", "DocumentSnapshot successfully written!")
                            fromPath.delete()
                            .addOnSuccessListener {
                                Log.e("deleted", "You can also see this list in Important List")
                            }
                                .addOnFailureListener {
                                    Log.e("error", "Error in marking document important")
                                }
                            getAndShowData()
                        }
                        .addOnFailureListener {
                            Log.e("Can't delete", "Can't mark the list important")
                        }
                } else {
                    Log.e("No such imp", "No such document")
                }
            } else {
                Log.e("failed", "imp failed", task.exception)
            }
        }

    }

    //sharing data
    private fun shareData(fromPath: DocumentReference) {
        val listData = ArrayList<HomeNotesData>()
        val acct = GoogleSignIn.getLastSignedInAccount(context)
        val personEmail = acct?.email
        fromPath.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var shareBody = document.data?.get("listBody").toString()
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Sharing data \n $shareBody")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                    Log.e("shared success", "Data shared successfully")

                } else {
                    Log.e(TAG, "No such document")
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Couldn't sare the data.", Toast.LENGTH_LONG).show()
            }
    }
}

