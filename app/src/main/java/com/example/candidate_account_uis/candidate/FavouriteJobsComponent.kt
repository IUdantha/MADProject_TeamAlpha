package com.example.candidate_account_uis.candidate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.databinding.ActivityFavouriteJobsComponentBinding
import com.example.candidate_account_uis.databinding.ActivityMainSarinduBinding

private lateinit var binding : ActivityFavouriteJobsComponentBinding

class FavouriteJobsComponent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteJobsComponentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.FavJobApply.setOnClickListener {

            val transaction = this?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,ApplicationFormFragment())?.commit() }
    }
}