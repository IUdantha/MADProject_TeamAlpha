package com.example.candidate_account_uis.candidate

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.candidate_account_uis.candidateActivities.ProfileSettingsActivity
import com.bumptech.glide.Glide
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.databinding.FragmentProfileBinding
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

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

        readData()

        val btn1  : Button = binding.addEk1
        val btn2  : Button = binding.addEk2
        val btn3  : Button = binding.addEk3

        val textview1 : TextView = binding.experiencetextbox
        val textview2 : TextView = binding.skilltext
        val textview3 : TextView = binding.educationtext

        binding.vector11.setOnClickListener {
            val thisIntent = Intent(activity, ProfileSettingsActivity::class.java)
            startActivity(thisIntent)
        }

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

    private fun readData() {

        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading ....")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val db = FirebaseFirestore.getInstance()

        val collectionUsers = db.collection("users")
        val nowUser = FirestoreClass().getCurrentUserID()

        val query = collectionUsers.whereEqualTo("id", nowUser)

        query.get().addOnSuccessListener { documents ->
            if (documents.size() > 0) {
                val document = documents.first()
                val exp = document.getString("experience")
                val skil = document.getString("skills")
                val edu = document.getString("education")
                val name = document.getString("name")
                val email = document.getString("email")
                val imagePath = document.getString("image")

                Toast.makeText(activity, "successfully read", Toast.LENGTH_SHORT).show()



                binding.experiencetextbox.text = exp.toString()
                binding.skilltext.text = skil.toString()
                binding.educationtext.text = edu.toString()
                binding.profiletextbox.text = "$name\n$email"

                Glide.with(this)
                    .load(imagePath)
                    .circleCrop() // Apply circular shape transformation
                    .into(binding.proImg)

                progressDialog.cancel()

            } else {
                progressDialog.cancel()
                Toast.makeText(activity, "User doesn't exist", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener { exception ->
            progressDialog.cancel()
            Toast.makeText(activity, "Documents getting failed", Toast.LENGTH_SHORT).show()
        }

        //--------------------

        /*database = FirebaseDatabase.getInstance().getReference("UserproD")
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
*/
    }



}