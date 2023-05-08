package com.example.candidate_account_uis.companyjobs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.candidate_account_uis.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity_udeshani : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        setContentView(R.layout.activity_vacancy_details)
        setContentView(R.layout.activity_vacancy_details2)
        setContentView(R.layout.activity_added_vacancies)
        setContentView(R.layout.activity_vacancy_scroll)
        setContentView(R.layout.activity_vacancy_list)
        setContentView(R.layout.activity_update_vacancy)

    }
}