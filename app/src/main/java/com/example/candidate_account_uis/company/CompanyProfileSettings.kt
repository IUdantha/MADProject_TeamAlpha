package com.example.candidate_account_uis.company

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.companyjobs.InterestedCandidates
import com.example.candidate_account_uis.companyjobs.VacancyAdd
import com.example.candidate_account_uis.databinding.ActivityComLoginBinding
import com.example.candidate_account_uis.databinding.ActivityCompanyProfileSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamicloading.ComponentLoader

class CompanyProfileSettings : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyProfileSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompanyProfileSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val delete= findViewById<Button>(R.id.delete)
        binding.delete.setOnClickListener {
            val thisIntent = Intent(this, CompanyDeleteAccountActivity::class.java)
            startActivity(thisIntent)
        }

        val update= findViewById<Button>(R.id.update)
        binding.update.setOnClickListener {
            val thisIntent = Intent(this, CompanyEditProfileActivity::class.java)
            startActivity(thisIntent)
        }

        val pw = findViewById<Button>(R.id.pw)
        binding.pw.setOnClickListener {
            val thisIntent = Intent(this, CompanyChangePwActivity::class.java)
            startActivity(thisIntent)
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

        val signOut = findViewById<Button>(R.id.signOut)
        signOut.setOnClickListener(View.OnClickListener() {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, ComLogin::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finishAffinity()  // To prevent the user from going back to the previous activity
        })

    }
}