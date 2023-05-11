package com.example.candidate_account_uis.company

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.company.entities.companyDetails
import com.google.firebase.database.FirebaseDatabase

//authentication
import com.google.firebase.auth.FirebaseAuth
import com.example.candidate_account_uis.databinding.ActivityCompanyDetailFormBinding

class CompanyDetailForm : AppCompatActivity() {

    //authentication
    private lateinit var binding: ActivityCompanyDetailFormBinding
    private lateinit var firebaseAuth: FirebaseAuth

    // Initialize Firebase Realtime Database reference
    val myDbRef = FirebaseDatabase.getInstance().getReference("companyDetails")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompanyDetailFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //authentication
        firebaseAuth = FirebaseAuth.getInstance()

        val loginComAcc = findViewById<TextView>(R.id.loginComAcc)
        loginComAcc.setOnClickListener(View.OnClickListener() {

            val intent = Intent(this, ComLogin::class.java)
            startActivity(intent)

        })

        binding.btnComAdd.setOnClickListener {
            val email = binding.edtComEmail.text.toString()
            val password = binding.edtComPassword.text.toString()
            val confirmPassword = binding.confirmComPassword.text.toString()

            val comName = findViewById<EditText>(R.id.edtComName).text.toString()
            val comContact = findViewById<EditText>(R.id.edtComContact).text.toString().toInt()
            val comDescription = findViewById<EditText>(R.id.edtComDescription).text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {

                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                val firebaseUser = authTask.result?.user
                                if (firebaseUser != null) {
                                    val myData = companyDetails(
                                        name = comName,
                                        contact = comContact,
                                        email = email,
                                        description = comDescription
                                    )

                                    myDbRef.child(firebaseUser.uid).setValue(myData)

                                    val intent = Intent(this, ComLogin::class.java)
                                    intent.putExtra("comName", myData.name)
                                    intent.putExtra("comContact", myData.contact)
                                    intent.putExtra("email", myData.email)
                                    intent.putExtra("comDescription", myData.description)

                                    startActivity(intent)

                                } else {
                                    Toast.makeText(
                                        this,
                                        authTask.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Password does not matched",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Password does not matched", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}




//
//      //normal
//        addCompany()


//    private fun addCompany(){
//
//        // Set up a button click listener to submit the form
//        val submitButton = findViewById<Button>(R.id.btnComAdd)
//        submitButton.setOnClickListener {
//            // Get the data from the form and add it to your Firebase Realtime Database
//            val comName = findViewById<EditText>(R.id.edtComName).text.toString()
//            val comContact = findViewById<EditText>(R.id.edtComContact).text.toString().toInt()
//            val comEmail = findViewById<EditText>(R.id.edtComEmail).text.toString()
//            val comDescription = findViewById<EditText>(R.id.edtComDescription).text.toString()
//
//            val myData = companyDetails(name = comName, contact = comContact, email = comEmail, description = comDescription)
//
//            myDbRef.push().setValue(myData)
//
//            Toast.makeText(this, "You have created an account successfully!", Toast.LENGTH_SHORT).show();
//
//            // Navigate to the display activity
//            val intent = Intent(this, ComLogin::class.java)
//
//            startActivity(intent)
//          val intent = Intent(this, CompanyProfile::class.java)
//
//            startActivity(intent)
//
//        }



