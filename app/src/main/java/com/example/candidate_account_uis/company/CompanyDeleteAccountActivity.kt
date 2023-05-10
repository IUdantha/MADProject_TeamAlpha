package com.example.candidate_account_uis.company

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidateActivities.ProfileSettingsActivity
import com.example.candidate_account_uis.candidateActivities.SignUpActivity
import com.example.candidate_account_uis.company.entities.companyDetails
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class CompanyDeleteAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_delete_account)

        val cancelButton= findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            val thisIntent = Intent(this, CompanyProfileSettings::class.java)
            startActivity(thisIntent)
        }

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            deleteAcc()
        }
    }


    fun deleteAcc() {

        val confirmEmailField = findViewById<EditText>(R.id.deleteEmail)

        val nowUser = FirebaseAuth.getInstance().currentUser

        val db = FirebaseDatabase.getInstance()
        val usersRef = db.getReference("companyDetails")

        Log.d("TAG", "confirmEmailField value: " + confirmEmailField.text.toString())
        usersRef.child(nowUser!!.uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(companyDetails::class.java)
                    val correctEmail = user?.email.toString()

                    Log.d("TAG", "Correct email value: $correctEmail")

                    if (confirmEmailField.text.toString() == correctEmail) {
                        usersRef.child(nowUser.uid).removeValue()
                            .addOnSuccessListener {
                                Log.d(ContentValues.TAG, "User data successfully deleted!")
                                nowUser.delete()?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(ContentValues.TAG, "User account deleted.")
                                    } else {
                                        Log.w(
                                            ContentValues.TAG,
                                            "Error deleting user account.",
                                            task.exception
                                        )
                                    }
                                }
                                val thisIntent = Intent(
                                    this@CompanyDeleteAccountActivity,
                                    CompanyDetailForm::class.java
                                )
                                startActivity(thisIntent)
                                Toast.makeText(
                                    this@CompanyDeleteAccountActivity,
                                    "Your account deleted Successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error deleting user data", e)
                            }
                    } else {
                        Toast.makeText(
                            this@CompanyDeleteAccountActivity,
                            "Provided email doesn't exist. Deletion Failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.d(ContentValues.TAG, "No matching user data.")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "Error getting user data", databaseError.toException())
            }
        })
    }
}