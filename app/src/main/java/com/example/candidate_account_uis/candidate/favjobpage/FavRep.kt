package com.example.candidate_account_uis.candidate.favjobpage

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavRep {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("jobs")

    @Volatile private var INSTANCE : FavRep ?= null

    fun getInstance() : FavRep{
        return INSTANCE ?: synchronized(this){

            val instance = FavRep()
            INSTANCE = instance
            instance
        }
    }

    fun loadFavJobs(favJobList : MutableLiveData<List<FavJobModel>>){

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _favJobList : List<FavJobModel> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(FavJobModel::class.java)!!
                }

                favJobList.postValue(_favJobList)

                }catch (e : Exception){

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}