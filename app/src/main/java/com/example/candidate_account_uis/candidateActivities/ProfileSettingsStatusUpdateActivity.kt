package com.example.candidate_account_uis.candidateActivities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.firestore.FirebaseFirestore

class ProfileSettingsStatusUpdateActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile_settings_statusupdate)

        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        cancelBtn.setOnClickListener {
            val thisIntent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(thisIntent)
        }

        val saveBtn = findViewById<Button>(R.id.edtStatusSaveBtn)
        saveBtn.setOnClickListener {
            editStatusFun()
            val thisIntent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(thisIntent)
        }


    }

    fun editStatusFun(){
        val edtStatusField = findViewById<EditText>(R.id.edtStatus)
        val enteredText = edtStatusField.text.toString()

        val db = FirebaseFirestore.getInstance()
        val collectionUsers = db.collection("users")

        val nowUser = FirestoreClass().getCurrentUserID()
        val userDocRef = collectionUsers.document(nowUser)

        val updates = hashMapOf<String, Any>(
            "status" to enteredText
        )

        userDocRef.update(updates)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error updating document", e)
            }
    }
}