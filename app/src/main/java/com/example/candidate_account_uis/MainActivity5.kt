package com.example.candidate_account_uis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.candidate_account_uis.databinding.ActivityMain5Binding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity5 : AppCompatActivity() {

    private lateinit var binding: ActivityMain5Binding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteBtn.setOnClickListener{

            val userName = binding.userName.text.toString()

            if (userName.isNotEmpty()){
                deleteData(userName)
            }else{
                Toast.makeText(this,"enter a username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteData(userName: String) {

        database = FirebaseDatabase.getInstance().getReference("JobVacancy")
        database.child(userName).removeValue().addOnSuccessListener{

            binding.userName.text.clear()
            Toast.makeText(this,"successfull", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {

            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
        }

    }
}