package com.example.candidate_account_uis.company

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.company.entities.companyDetails
import com.example.candidate_account_uis.companyjobs.InterestedCandidates
import com.example.candidate_account_uis.companyjobs.VacancyAdd
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CompanyProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_profile)

        Log.d("CompanyProfileActivity", "onCreate called")

        // Inside UserProfileActivity
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser


        if (firebaseUser == null) {

            Log.d("CompanyProfileActivity", "User is null. Redirecting to login activity")

            val intent = Intent(this, ComLogin::class.java)
            startActivity(intent)
            finish()  // To prevent the user from going back to this activity using the back button
            return
        }

        if (firebaseUser != null) {

            Log.d("CompanyProfileActivity", "User ID: ${firebaseUser.uid}")

            val myDbRef = FirebaseDatabase.getInstance().getReference("companyDetails")
            val userId = firebaseUser.uid

            myDbRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val user = dataSnapshot.getValue(companyDetails::class.java)
                        // Display user's profile details in the activity's UI
                        if (user != null) {
                            findViewById<TextView>(R.id.headingComName).text = user.name
                            findViewById<TextView>(R.id.comNameP).text = user.name
                            findViewById<TextView>(R.id.comContactP).text = user.contact.toString()
                            findViewById<TextView>(R.id.comEmailP).text = user.email
                            findViewById<TextView>(R.id.comDescriptionP).text = user.description
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }


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

        val settings = findViewById<ImageView>(R.id.settings)
        settings.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, CompanyProfileSettings::class.java)
            startActivity(intent)
        })

    }
}


//
//val recyclerView: RecyclerView = findViewById(R.id.com_user_profile)
//val adapter = companyDetailsAdapter()
//recyclerView.adapter = adapter
//
////context of this is passed to the adapter
//recyclerView.layoutManager = LinearLayoutManager(this)
//
//// Initialize Firebase Realtime Database reference
//
//val myDbRef = FirebaseDatabase.getInstance().getReference("companyDetails")
//
//// Retrieve data from Firebase Realtime Database
//myDbRef.addValueEventListener(object : ValueEventListener {
//    override fun onDataChange(snapshot: DataSnapshot) {
//        // Clear the adapter before adding new data
////                adapter.clear()
//
//        val companyDetailsList = mutableListOf<companyDetails>()
//
//        // Loop through each child of the "companyDetails" node in Firebase Realtime Database
//        for (childSnapshot in snapshot.children) {
//            // Get the CompanyDetails data and add it to the adapter
//            val companyDetails = childSnapshot.getValue(companyDetails::class.java)
//            companyDetails?.let { companyDetailsList.add(it) }
//        }
//
//        // Set the list of CompanyDetails to the adapter
//        adapter.setData(companyDetailsList, this@CompanyProfile)
//        // Notify the adapter that the data has changed
//        adapter.notifyDataSetChanged()
//    }
//
//    override fun onCancelled(error: DatabaseError) {
//        // Handle any errors
//    }
//})
