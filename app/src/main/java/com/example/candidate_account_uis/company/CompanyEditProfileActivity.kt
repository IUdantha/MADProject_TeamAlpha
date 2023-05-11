package com.example.candidate_account_uis.company
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.company.entities.companyDetails
import com.example.candidate_account_uis.companyjobs.InterestedCandidates
import com.example.candidate_account_uis.companyjobs.VacancyAdd
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class CompanyEditProfileActivity : AppCompatActivity() {


    private lateinit var comName: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var description: EditText

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    private lateinit var myDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_edit_profile)

            comName = findViewById(R.id.comName)
            phone = findViewById(R.id.phone)
            email = findViewById(R.id.email)
            description = findViewById(R.id.description)

            firebaseAuth = FirebaseAuth.getInstance()
            firebaseUser = firebaseAuth.currentUser!!

            myDbRef = FirebaseDatabase.getInstance().getReference("companyDetails")

            if (firebaseUser != null) {
                val userId = firebaseUser.uid

                myDbRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val user = dataSnapshot.getValue(companyDetails::class.java)
                            // Display user's profile details in the activity's UI
                            if (user != null) {
                                comName.setText(user.name)
                                phone.setText(user.contact.toString())
                                email.setText(user.email)
                                description.setText(user.description)
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors here
                    }
                })
            }

            findViewById<Button>(R.id.saveButton).setOnClickListener {
                // Retrieve the updated profile details from the EditText views
                val name = comName.text.toString().trim()
                val contact = phone.text.toString().trim().toInt()
                val email = email.text.toString().trim()
                val description = description.text.toString().trim()

                // Update the user's profile in the Firebase Realtime Database
                val user = companyDetails(name, contact, email, description)
                myDbRef.child(firebaseUser.uid).setValue(user)


                // Return to the CompanyProfileActivity
                    val intent = Intent(this, CompanyProfile::class.java)
                    startActivity(intent)

            }

        //cancel button
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        cancelButton.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, CompanyProfile::class.java)
            startActivity(intent)

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