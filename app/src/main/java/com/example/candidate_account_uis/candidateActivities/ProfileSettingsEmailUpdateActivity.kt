package com.example.candidate_account_uis.candidateActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R

class ProfileSettingsEmailUpdateActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile_settings_emailupdate)

        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        cancelBtn.setOnClickListener {
            val thisIntent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(thisIntent)
        }


    }
}