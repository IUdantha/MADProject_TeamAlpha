package com.example.candidate_account_uis.company

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidateActivities.isValidEmail
import com.example.candidate_account_uis.company.entities.companyDetails
import com.example.candidate_account_uis.company.entities.interviewDetails
import com.example.candidate_account_uis.companyjobs.InterestedCandidates
import com.example.candidate_account_uis.companyjobs.VacancyAdd
import com.example.candidate_account_uis.databinding.ActivityCompanyDetailFormBinding
import com.example.candidate_account_uis.databinding.ActivityCompanyInterviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CompanyInterviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyInterviewBinding
    private lateinit var firebaseAuth: FirebaseAuth

    // Initialize Firebase Realtime Database reference
    val myDbRef = FirebaseDatabase.getInstance().getReference("interviewDetails")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_interview)

        //code
        binding = ActivityCompanyInterviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //authentication
        firebaseAuth = FirebaseAuth.getInstance()


        //when click change password
        binding.sendButton.setOnClickListener {

            val email = intent.getStringExtra("email")

            val note = binding.editNote.text.toString()
            val date = binding.inteviewD.text.toString()
            val time = binding.interviewT.text.toString()



            // Check for empty fields
            if (note.isBlank() || date.isBlank() || time.isBlank()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()

            }else{
                val myData = interviewDetails(
                    note = note,
                    date = date,
                    time = time
                )

                myDbRef.push().setValue(myData)

                // Create the email intent with pre-populated fields
                val subject = "Interview Details"
                val body = "Note: $note\nDate: $date\nTime: $time"
                val intent = Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    .putExtra(Intent.EXTRA_SUBJECT, subject)
                    .putExtra(Intent.EXTRA_TEXT, body)

                // Verify that an email client is available to handle the intent
                val packageManager = packageManager
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                    val successIntent = Intent(this, CompInterviewSendSuccessActivity::class.java)
                    startActivity(successIntent)
                } else {
                    Toast.makeText(this, "No email client found.", Toast.LENGTH_SHORT).show()
                }
            }

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

    }
}