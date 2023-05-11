package com.example.candidate_account_uis.candidateActivities

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidate.HomeFragment
import com.example.candidate_account_uis.candidate.MainActivity_sarindu
import com.example.candidate_account_uis.candidate.ProfileFragment
import com.example.candidate_account_uis.databinding.ActivitySignInBinding
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.firestore.FirebaseFirestore

class ProfileSettingsActivity: AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile_settings)



        // Navigate buttons
        val nameButton = findViewById<Button>(R.id.nameBtn)
        val statusButton = findViewById<Button>(R.id.statusBtn)
        val rePasswordButton = findViewById<Button>(R.id.rePassword)
        val delAccButton = findViewById<Button>(R.id.delAccount)
        val emailView = findViewById<TextView>(R.id.emailView)
        val backbutton = findViewById<Button>(R.id.profilebackbutton)

        backbutton.setOnClickListener {
            val thisIntent = Intent(this, MainActivity_sarindu::class.java)
            startActivity(thisIntent)
        }




        nameButton.setOnClickListener {
            val thisIntent = Intent(this, ProfileSettingsNameUpdateActivity::class.java)
            startActivity(thisIntent)
        }

        statusButton.setOnClickListener {
            val thisIntent = Intent(this, ProfileSettingsStatusUpdateActivity::class.java)
            startActivity(thisIntent)
        }

        rePasswordButton.setOnClickListener {
            val thisIntent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(thisIntent)
        }

        delAccButton.setOnClickListener {
            val thisIntent = Intent(this, ProfileSettingsDeleteActivity::class.java)
            startActivity(thisIntent)
        }

        //Get database instance
        val db = FirebaseFirestore.getInstance()

        val collectionUsers = db.collection("users")
        val nowUser = FirestoreClass().getCurrentUserID()

        val query = collectionUsers.whereEqualTo("id", nowUser)

        query.get().addOnSuccessListener { documents ->
            if (documents.size() > 0) {
                val document = documents.first()
                val name = document.getString("name")
                val status = document.getString("status")
                val email = document.getString("email")
                nameButton.text = "Name\n$name"
                statusButton.text = "Status\n$status"
                emailView.text = email
            } else {
                Log.d(TAG, "No matching documents.")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }

//        collectionUsers.get()
//            .addOnSuccessListener { result ->
//                // Handle successful query result
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                // Handle failed query result
//                Log.w(TAG, "Error getting documents.", exception)
//            }






    }

}