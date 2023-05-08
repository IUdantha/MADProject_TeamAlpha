package com.example.candidate_account_uis.candidateActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R

class TJobListComponentActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.t_job_list_component)

        val buttonNavigate = findViewById<Button>(R.id.job_apply_btn)
        buttonNavigate.setOnClickListener {
            val intent = Intent(this, SelecetedJobDescriptionActivity::class.java)
            startActivity(intent)
        }


    }

}