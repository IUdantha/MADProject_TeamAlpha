package com.example.candidate_account_uis.candidateActivities

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.candidate_account_uis.R

import com.example.candidate_account_uis.candidate.HomeFragment
import com.example.candidate_account_uis.candidate.MainActivity_sarindu
import com.example.candidate_account_uis.candidate.ProfileFragment


import com.example.candidate_account_uis.databinding.ActivitySignInBinding
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileSettingsActivity: AppCompatActivity() {
    val PICK_IMAGE_REQUEST = 1  // Define a constant for the image picker request


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
        val settingsBackBtn = findViewById<ImageView>(R.id.settingsBackBtn)
        val profileImage = findViewById<ImageView>(R.id.profileImg)
        val profileImgEditButton = findViewById<ImageView>(R.id.profileImgEdit)


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

        settingsBackBtn.setOnClickListener {
            val thisIntent = Intent(this, MainActivity_sarindu::class.java)
            startActivity(thisIntent)
        }

        profileImgEditButton.setOnClickListener {
            // Inside the click listener of profileImgEditButton
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
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
                val imagePath = document.getString("image")
                nameButton.text = "Name\n$name"
                statusButton.text = "Status\n$status"
                emailView.text = email
                Glide.with(this)
                    .load(imagePath)
                    .circleCrop() // Apply circular shape transformation
                    .into(profileImage)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data

            // Upload the image to Firebase Storage
            uploadImageToFirebaseStorage(selectedImageUri)
        }
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri?) {
        if (imageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/${FirestoreClass().getCurrentUserID()}")

            val uploadTask = imageRef.putFile(imageUri)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Image uploaded successfully, get the download URL
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Save the image URL to the Firestore database
                        saveImageToFirestore(uri.toString())
                    }
                } else {
                    // Handle the upload failure
                    // ...
                }
            }
        }
    }

    private fun saveImageToFirestore(imageUrl: String) {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        val nowUser = FirestoreClass().getCurrentUserID()

        usersCollection.document(nowUser).update("image", imageUrl)
            .addOnSuccessListener {
                val thisIntent = Intent(this, ProfileSettingsActivity::class.java)
                startActivity(thisIntent)
            }
            .addOnFailureListener { exception ->
                // Handle the failure
                // ...
            }
    }


}