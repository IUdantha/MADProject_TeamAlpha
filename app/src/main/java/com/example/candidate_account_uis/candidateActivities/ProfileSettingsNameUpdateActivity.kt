package com.example.candidate_account_uis.candidateActivities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.firestore.FirebaseFirestore

class ProfileSettingsNameUpdateActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile_settings_nameupdate)

        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        cancelBtn.setOnClickListener {
            val thisIntent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(thisIntent)
        }

        val saveBtn = findViewById<Button>(R.id.edtNameSaveBtn)
        saveBtn.setOnClickListener {
            editNameFun()
            val thisIntent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(thisIntent)
        }






    }

    fun editNameFun(){
        val edtNameField = findViewById<EditText>(R.id.edtName)
        val enteredText = edtNameField.text.toString()

        val db = FirebaseFirestore.getInstance()
        val collectionUsers = db.collection("users")

        val nowUser = FirestoreClass().getCurrentUserID()
        val userDocRef = collectionUsers.document(nowUser)

        val updates = hashMapOf<String, Any>(
            "name" to enteredText
        )

        userDocRef.update(updates)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
    }
}