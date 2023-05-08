package com.example.candidate_account_uis.company

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.MainActivity
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.company.entities.companyDetails
import com.google.firebase.database.*


class ComLogin : AppCompatActivity() {

    var comLoginEmail: EditText? = null
    var comLoginPassword: EditText? = null
//    var comLoginButton: Button? = null
//    var createComAcc: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_com_login)

        comLoginEmail = findViewById<EditText>(R.id.comLoginEmail)
        comLoginPassword = findViewById<EditText>(R.id.comLoginPassword)
        val comLoginButton = findViewById<Button>(R.id.comLoginButton)
        val createComAcc = findViewById<TextView>(R.id.createComAcc)

        comLoginButton.setOnClickListener {
            if (validateUsername() == true || validatePassword() == true) {
                checkUser()
            }
        }

        createComAcc.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, CompanyDetailForm::class.java)
            startActivity(intent)

        })

    }

    fun validateUsername(): Boolean? {
        val comEmail : String? = comLoginEmail?.text.toString()
        return if (comEmail.isNullOrEmpty()) {
            comLoginEmail!!.error = "Email cannot be empty"
            false
        } else {
            comLoginEmail!!.error = null
            true
        }
    }

    fun validatePassword(): Boolean? {
        val comPwd: String? = comLoginPassword?.text.toString()
        return if (comPwd.isNullOrEmpty()) {
            comLoginPassword!!.error = "Password cannot be empty"
            false
        } else {
            comLoginPassword!!.error = null
            true
        }
    }

    fun checkUser() {
        val userComEmail = findViewById<EditText>(R.id.comLoginEmail).text.toString()
        val userComPassword = findViewById<EditText>(R.id.comLoginPassword).text.toString()

        val reference = FirebaseDatabase.getInstance().getReference("companyDetails")
        val checkCompanyDetails: Query = reference.orderByChild("email").equalTo(userComEmail)

        checkCompanyDetails.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    comLoginEmail!!.error = null

                    val passwordFromDB = snapshot.child(userComEmail).child("password").getValue(String::class.java)

                    if (passwordFromDB == userComPassword) {
                        comLoginEmail!!.error = null

                        val intent = Intent(this@ComLogin, CompanyProfile::class.java)
                        startActivity(intent)

                    } else {
                        comLoginPassword!!.setError("Invalid Credentials")
                        comLoginPassword!!.requestFocus()
                    }
                } else {
                    comLoginEmail!!.error = "User does not exist"
                    comLoginEmail!!.requestFocus()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

}