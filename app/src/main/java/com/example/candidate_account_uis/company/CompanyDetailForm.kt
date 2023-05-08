package com.example.candidate_account_uis.company

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.company.adapters.companyDetailsAdapter
import com.example.candidate_account_uis.company.entities.companyDetails
import com.google.firebase.database.FirebaseDatabase



class CompanyDetailForm : AppCompatActivity() {

    // Initialize Firebase Realtime Database reference
    val myDbRef = FirebaseDatabase.getInstance().getReference("companyDetails")
    val adapter = companyDetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_detail_form)

        addCompany()
    }

    private fun addCompany(){

        // Set up a button click listener to submit the form
        val submitButton = findViewById<Button>(R.id.btnComAdd)
        submitButton.setOnClickListener {
            // Get the data from the form and add it to your Firebase Realtime Database
            val comName = findViewById<EditText>(R.id.edtComName).text.toString()
            val comContact = findViewById<EditText>(R.id.edtComContact).text.toString().toInt()
            val comEmail = findViewById<EditText>(R.id.edtComEmail).text.toString()
            val comDescription = findViewById<EditText>(R.id.edtComDescription).text.toString()

            val myData = companyDetails(name = comName, contact = comContact, email = comEmail, description = comDescription)

            myDbRef.push().setValue(myData)


            // Navigate to the display activity
            val intent = Intent(this, CompanyProfile::class.java)
            intent.putExtra("comName", myData.name)
            intent.putExtra("comContact", myData.contact)
            intent.putExtra("comEmail", myData.email)
            intent.putExtra("comDescription", myData.description)
            startActivity(intent)

        }
    }


}