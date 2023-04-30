package com.example.candidate_account_uis

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.t_job_list_adapter
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class TrendingJobsActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var jobRecyclerview : RecyclerView
    private lateinit var jobArrayList : ArrayList<job>

    //Search functions
    private lateinit var searchView: SearchView
    private var mList = ArrayList<job>()
    private lateinit var adapter: t_job_list_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_jobs)

        jobRecyclerview = findViewById(R.id.t_job_list)
        jobRecyclerview.layoutManager = LinearLayoutManager(this)
        jobRecyclerview.setHasFixedSize(true)


        jobArrayList = arrayListOf<job>()
        getUserData()

        //Filter jobs from search bar
        mList = jobArrayList
        searchView = findViewById(R.id.search_input)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })


    }
    //Search functions
    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<job>()
            for (i in mList) {
                if (i.title?.lowercase(Locale.ROOT)?.contains(query) == true) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    //Recycle view
    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("jobs")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (jobSnapshot in snapshot.children){


                        val job = jobSnapshot.getValue(job::class.java)
                        jobArrayList.add(job!!)

                    }

                    jobRecyclerview.adapter = t_job_list_adapter(jobArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}