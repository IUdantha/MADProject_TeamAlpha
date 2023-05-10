package com.example.candidate_account_uis.company

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.databinding.ActivityComLoginBinding
import com.example.candidate_account_uis.databinding.ActivityCompanyProfileSettingsBinding

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
    }
}