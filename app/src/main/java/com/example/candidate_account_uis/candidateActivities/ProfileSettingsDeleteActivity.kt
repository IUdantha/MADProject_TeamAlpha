package com.example.candidate_account_uis.candidateActivities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileSettingsDeleteActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile_settings_delete)

        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        cancelBtn.setOnClickListener {
            val thisIntent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(thisIntent)
        }

        val deleteConfirmBtn = findViewById<Button>(R.id.deleteConfirmBtn)
        deleteConfirmBtn.setOnClickListener {
            deleteAccfun()
        }

    }

    fun deleteAccfun() {
        val confirmEmailField = findViewById<TextView>(R.id.confirmEmail)
        val nowUser = FirestoreClass().getCurrentUserID()

        val db = FirebaseFirestore.getInstance()
        val collectionUsers = db.collection("users")

        val query = collectionUsers.whereEqualTo("id", nowUser)

        query.get().addOnSuccessListener { documents ->
            if (documents.size() > 0) {
                val document = documents.first()
                val correctEmail = document.getString("email").toString()

                if (confirmEmailField.text.toString() == correctEmail) {
                    val userDocRef = collectionUsers.document(nowUser)
                    userDocRef.delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!")
                            val auth = FirebaseAuth.getInstance()
                            val user = auth.currentUser
                            user?.delete()?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "User account deleted.")
                                } else {
                                    Log.w(TAG, "Error deleting user account.", task.exception)
                                }
                            }
                            val thisIntent = Intent(this, SignUpActivity::class.java)
                            startActivity(thisIntent)
                            Toast.makeText(this, "Your account deleted Successfully!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error deleting document", e)
                        }
                } else {
                    Toast.makeText(this, "Provided email doesn't exist. Deletion Failed!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d(TAG, "No matching documents.")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }
    }

}