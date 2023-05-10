package com.example.candidate_account_uis.company

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidateActivities.ResetPasswordActivity
import com.example.candidate_account_uis.company.entities.companyDetails
import com.example.candidate_account_uis.databinding.ActivityComLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ComLogin : AppCompatActivity() {

    //authentication
    private lateinit var binding: ActivityComLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val createComAcc = findViewById<TextView>(R.id.createComAcc)

        createComAcc.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, CompanyDetailForm::class.java)
            startActivity(intent)

        })

        //authentication
        binding.comLoginButton.setOnClickListener {
            val email = binding.comLoginEmail.text.toString()
            val password = binding.comLoginPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this@ComLogin, CompanyProfile::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.resetComPassword.setOnClickListener {
            val intent = Intent(this, CompanyChangePwActivity::class.java)
            startActivity(intent)
        }
    }
}
//
        //authentication
//    fun compareEmail(changePwEmail: EditText){
//        if (changePwEmail.text.toString().isEmpty()){
//            return
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(changePwEmail.text.toString()).matches()){
//            return
//        }
//        firebaseAuth.sendPasswordResetEmail(changePwEmail.text.toString())
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }


