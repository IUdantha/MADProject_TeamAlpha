package com.example.candidate_account_uis.company

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.companyjobs.InterestedCandidates
import com.example.candidate_account_uis.companyjobs.VacancyAdd

class CompInterviewSendSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comp_interview_send_success)


        val success = findViewById<TextView>(R.id.button2)

        success.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, InterestedCandidates::class.java)
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