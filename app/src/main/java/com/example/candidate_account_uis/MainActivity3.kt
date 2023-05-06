package com.example.candidate_account_uis
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.candidate_account_uis.databinding.ActivityMain3Binding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding : ActivityMain3Binding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addEk34.setOnClickListener{

            val userName : String = binding.editTextText5.text.toString()

            if (userName.isNotEmpty()){

                readData(userName)

            }
            else{

                Toast.makeText(this,"enter a username", Toast.LENGTH_SHORT).show()


            }
        }
    }

    private fun readData(userName: String) {

        database = FirebaseDatabase.getInstance().getReference("UserproD")
        database.child(userName).get().addOnSuccessListener {

            if(it.exists()){
                val exp = it.child("experience").value
                val skil = it.child("skills").value
                val edu = it.child("eduction").value
                val name = it.child("name").value


                Toast.makeText(this,"successfully read",Toast.LENGTH_SHORT).show()
                binding.editTextText5.text.clear()
                binding.experiencetextbox.text =exp.toString()
                binding.skilltext.text =skil.toString()
                binding.educationtext.text =edu.toString()
                //binding.editTextText6.text = name.toString()

            }
            else{

                Toast.makeText(this,"User doesn't exist",Toast.LENGTH_SHORT).show()
            }


        }.addOnFailureListener {

            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()

        }

    }
}