package com.example.candidate_account_uis.company

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidateActivities.SignInActivity
import com.example.candidate_account_uis.candidateActivities.isValidEmail
import com.example.candidate_account_uis.companyjobs.InterestedCandidates
import com.example.candidate_account_uis.companyjobs.VacancyAdd
import com.example.candidate_account_uis.databinding.ActivityCompanyChangePwBinding
import com.example.candidate_account_uis.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class CompanyChangePwActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyChangePwBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyChangePwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.resetButton.setOnClickListener{
            val email = binding.changePwEmail.text.toString()

            if(isValidEmail(email)){
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "Email sent.")
                            Toast.makeText(this, "Email Sent successfully.\nPlease Check your Email!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ComLogin::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "Provided email doesn't exists.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Invalid Email. Try again!", Toast.LENGTH_SHORT).show()
            }


        }

        binding.cancelButton.setOnClickListener {
            val intent = Intent(this, ComLogin::class.java)
            startActivity(intent)
        }


        //when click change password
        binding.resetButton.setOnClickListener{
            val email = binding.changePwEmail.text.toString()

            if(isValidEmail(email)){
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "Email sent.")
                            Toast.makeText(this, "Email Sent successfully.\nPlease Check your Email!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ComLogin::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "Provided email doesn't exists.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Invalid Email. Try again!", Toast.LENGTH_SHORT).show()
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