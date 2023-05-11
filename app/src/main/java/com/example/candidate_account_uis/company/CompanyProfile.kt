package com.example.candidate_account_uis.company

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.company.entities.companyDetails
import com.example.candidate_account_uis.companyjobs.InterestedCandidates
import com.example.candidate_account_uis.companyjobs.VacancyAdd
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class CompanyProfile : AppCompatActivity() {

    val PICK_IMAGE_REQUEST = 1001
    private val PERMISSION_CODE = 1000
    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_profile)

        //Image uploading


        //Image upload
        val editPencil = findViewById<ImageView>(R.id.editPencil)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null && data.data != null) {
                    val imageUri = data.data
                    // Upload the selected image to Firebase Storage and update the Realtime Database
                    uploadImage(imageUri!!)
                }
            }
        }

        editPencil.setOnClickListener {

            // Request permissions to access external storage
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                }
            }

            // Open the file chooser intent
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            launcher.launch(Intent.createChooser(intent, "Select Picture"))
        }
        // Inside UserProfileActivity
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            val intent = Intent(this, ComLogin::class.java)
            startActivity(intent)
            finish()  // To prevent the user from going back to this activity using the back button
            return
        }

        if (firebaseUser != null) {

            val myDbRef = FirebaseDatabase.getInstance().getReference("companyDetails")
            val userId = firebaseUser.uid

            // Get the ImageView for the profile picture
            val logoIcon = findViewById<ImageView>(R.id.logoIcon)

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


                            // Load the user's profile picture into the ImageView using Glide
                            Glide.with(this@CompanyProfile)
                                .load(user.comProfilePicture)
                                .placeholder(R.drawable.logobg)
                                .error(R.drawable.logobg)
                                .circleCrop()
                                .into(logoIcon)

                        }

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
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

            val settings = findViewById<ImageView>(R.id.settings)
            settings.setOnClickListener(View.OnClickListener() {
                val intent = Intent(this, CompanyProfileSettings::class.java)
                startActivity(intent)
            })

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

        private fun uploadImage(imageUri: Uri) {
            // Get a reference to the Firebase Storage instance
            val storageReference = FirebaseStorage.getInstance().getReference("comProfilePicture")

            // Get the current user ID
            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            // Create a reference to the file that will be uploaded
            val fileReference = storageReference.child("$userId.jpg")

            // Upload the file to Firebase Storage
            fileReference.putFile(imageUri).addOnSuccessListener {
                // Get the download URL of the uploaded image
                fileReference.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Store the download URL in the Realtime Database under the user's node
                    val databaseReference =
                        FirebaseDatabase.getInstance().getReference("companyDetails").child(userId)
                    databaseReference.child("comProfilePicture").setValue(downloadUri.toString())
                }
            }
        }
}


