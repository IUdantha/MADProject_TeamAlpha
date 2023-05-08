package com.example.candidate_account_uis.company

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.company.adapters.companyDetailsAdapter
import com.example.candidate_account_uis.company.entities.companyDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CompanyProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_profile)

        val recyclerView: RecyclerView = findViewById(R.id.com_user_profile)
        val adapter = companyDetailsAdapter()
        recyclerView.adapter = adapter

        //context of this is passed to the adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Firebase Realtime Database reference

        val myDbRef = FirebaseDatabase.getInstance().getReference("companyDetails")

        // Retrieve data from Firebase Realtime Database
        myDbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the adapter before adding new data
//                adapter.clear()

                val companyDetailsList = mutableListOf<companyDetails>()

                // Loop through each child of the "companyDetails" node in Firebase Realtime Database
                for (childSnapshot in snapshot.children) {
                    // Get the CompanyDetails data and add it to the adapter
                    val companyDetails = childSnapshot.getValue(companyDetails::class.java)
                    companyDetails?.let { companyDetailsList.add(it) }
                }

                // Set the list of CompanyDetails to the adapter
                adapter.setData(companyDetailsList, this@CompanyProfile)
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
            }
        })
    }
}