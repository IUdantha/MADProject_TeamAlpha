package com.example.candidate_account_uis.companyjobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.company.adapters.interestedCandidatesAdapter
import com.example.candidate_account_uis.candidate.ApplicationData
import com.example.candidate_account_uis.company.CompanyProfile
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.appcompat.widget.SearchView
import java.util.*


class InterestedCandidates : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_interested_candidates)
        val recyclerView: RecyclerView = findViewById(R.id.int_candidate_recycler)

        val adapter = interestedCandidatesAdapter()
        recyclerView.adapter = adapter

        //context of this is passed to the adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Firebase Realtime Database reference

        val myDbRef = FirebaseDatabase.getInstance().getReference("CandidateAppication")

        // Retrieve data from Firebase Realtime Database
        myDbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the adapter before adding new data
//                adapter.clear()

                val interestedCandidateDetailsList = mutableListOf<ApplicationData>()

                // Loop through each child of the "companyDetails" node in Firebase Realtime Database
                for (childSnapshot in snapshot.children) {
                    // Get the CompanyDetails data and add it to the adapter
                    val applicationData = childSnapshot.getValue(ApplicationData::class.java)
                    applicationData?.let { data ->
                        interestedCandidateDetailsList.add(data)
                    }
                }

                // Set the list of CompanyDetails to the adapter
                adapter.setData(interestedCandidateDetailsList, this@InterestedCandidates)
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
            }
        })

        //navigation buttons
        val homeIcon = findViewById<ImageView>(R.id.homeIcon)
        homeIcon.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, InterestedCandidates::class.java)
            startActivity(intent)
        })

        val FormIcon = findViewById<ImageView>(R.id.FormIcon)
        FormIcon.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, VacancyAdd::class.java)
            startActivity(intent)
        })

        val ProfileIcon = findViewById<ImageView>(R.id.ProfileIcon)
        ProfileIcon.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, CompanyProfile::class.java)
            startActivity(intent)
        })


    }
}