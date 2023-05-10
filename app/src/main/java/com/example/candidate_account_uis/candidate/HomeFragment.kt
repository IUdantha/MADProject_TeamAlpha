package com.example.candidate_account_uis.candidate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candidate_account_uis.candidateActivities.job
import com.example.candidate_account_uis.candidateActivities.t_job_list_adapter
import com.example.candidate_account_uis.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class HomeFragment : Fragment() {
    //--------
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
//--------

    private lateinit var database : DatabaseReference

    private lateinit var dbref : DatabaseReference
    private lateinit var jobRecyclerview : RecyclerView
    private lateinit var jobArrayList : ArrayList<job>

    //Search functions
    private lateinit var searchView: SearchView
    private var mList = ArrayList<job>()
    private lateinit var adapter: t_job_list_adapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //-----
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        //-----

        jobRecyclerview = binding.tJobList
        jobRecyclerview.layoutManager = LinearLayoutManager(activity)
        jobRecyclerview.setHasFixedSize(true)


        jobArrayList = arrayListOf<job>()
        getUserData()

        //Filter jobs from search bar
        mList = jobArrayList
        searchView = binding.searchInput
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })


        return binding.root

    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<job>()
            for (i in mList) {
                if (i.title?.lowercase(Locale.ROOT)?.contains(query) == true) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(activity, "No Data found", Toast.LENGTH_SHORT).show()
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