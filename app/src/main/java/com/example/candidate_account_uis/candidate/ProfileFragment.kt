package com.example.candidate_account_uis.candidate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.candidate_account_uis.databinding.FragmentProfileBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : DatabaseReference


    private lateinit var communicator: communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


//        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//        val btn1  : Button = view.findViewById(R.id.add_ek1)
//        val btn2  : Button = view.findViewById(R.id.add_ek2)
//        val btn3  : Button = view.findViewById(R.id.add_ek3)
//
//
//        val textview1 : TextView = view.findViewById(R.id.experiencetextbox)
//        val textview2 : TextView = view.findViewById(R.id.skilltext)
//        val textview3 : TextView = view.findViewById(R.id.educationtext)
//
//
//        communicator = activity as communicator
//
//        btn1.setOnClickListener {
//            communicator.passData1(textview1.text.toString())
//        }
//        btn2.setOnClickListener {
//            communicator.passData2(textview2.text.toString())
//        }
//        btn3.setOnClickListener {
//            communicator.passData3(textview3.text.toString())
//        }
//        return view

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        readData("amal")

        val btn1  : Button = binding.addEk1
        val btn2  : Button = binding.addEk2
        val btn3  : Button = binding.addEk3

        val textview1 : TextView = binding.experiencetextbox
        val textview2 : TextView = binding.skilltext
        val textview3 : TextView = binding.educationtext

        communicator = activity as communicator

        btn1.setOnClickListener {
            communicator.passData1(textview1.text.toString())
        }
        btn2.setOnClickListener {
            communicator.passData2(textview2.text.toString())
        }
        btn3.setOnClickListener {
            communicator.passData3(textview3.text.toString())
        }


        return binding.root



    }

    private fun readData(userName: String) {

        database = FirebaseDatabase.getInstance().getReference("UserproD")
        database.child(userName).get().addOnSuccessListener {


            if(it.exists()){
                val exp = it.child("experience").value
                val skil = it.child("skills").value
                val edu = it.child("eduction").value
                val name = it.child("name").value


                Toast.makeText(activity, "successfully read", Toast.LENGTH_SHORT).show()
                //binding.editTextText5.text.clear()
                binding.experiencetextbox.text =exp.toString()
                binding.skilltext.text =skil.toString()
                binding.educationtext.text =edu.toString()
                //binding.editTextText6.setText(name.toString())



            }
            else{

                Toast.makeText(activity, "User doesn't exist", Toast.LENGTH_SHORT).show()
            }


        }.addOnFailureListener {

            Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show()

        }

    }



}