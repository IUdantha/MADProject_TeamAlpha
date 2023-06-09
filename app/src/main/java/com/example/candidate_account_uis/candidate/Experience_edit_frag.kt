package com.example.candidate_account_uis.candidate

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.databinding.FragmentExperienceEditFragBinding
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class Experience_edit_frag : Fragment() {

    private var _binding: FragmentExperienceEditFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : DatabaseReference

    var output3 : String ?= ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_experience_edit_frag, container, false)
//        val textview3 : TextView = view.findViewById(R.id.we_are_seeking22)
//
//        output3 = arguments?.getString("we_are_seeking22")
//        textview3.text = output3
//
//        return view

        _binding = FragmentExperienceEditFragBinding.inflate(inflater, container, false)

        val textview3 : TextView = binding.weAreSeeking22

        output3 = arguments?.getString("we_are_seeking22")
        textview3.text = output3

        binding.backexp.setOnClickListener {

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, ProfileFragment())?.commit()
        }

        binding.saveexp.setOnClickListener {

            val exp = binding.weAreSeeking22.text.toString()
            updateData(exp)
        }

        return binding.root
    }


    private fun updateData(exp: String) {

        val db = FirebaseFirestore.getInstance()
        val collectionUsers = db.collection("users")

        val nowUser = FirestoreClass().getCurrentUserID()
        val userDocRef = collectionUsers.document(nowUser)

        val updates = hashMapOf<String, Any>(
            "experience" to exp
        )

        userDocRef.update(updates)
            .addOnSuccessListener {
                Toast.makeText(activity, "Successfuly Updated", Toast.LENGTH_SHORT).show()

                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_layout, ProfileFragment())?.commit()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Failed to Update", Toast.LENGTH_SHORT).show()

            }
    }



        //-----------------------------------

//        database = FirebaseDatabase.getInstance().getReference("UserproD")
//        val userproD = mapOf<String,String>(
//            "experience" to exp,
////            "skills" to skil,
////            "eduction" to edu
//        )
//
//        database.child(userName).updateChildren(userproD).addOnSuccessListener {
//
////            binding.userName.text.clear()
////            binding.firstName.text.clear()
////            binding.lastname.text.clear()
////            binding.age.text.clear()
//            Toast.makeText(activity, "Successfuly Updated", Toast.LENGTH_SHORT).show()
//
//            //------
//            val transaction = activity?.supportFragmentManager?.beginTransaction()
//            transaction?.replace(R.id.frame_layout, ProfileFragment())?.commit()
//            //--------
//            //readData("amal")
//
//       }.addOnFailureListener{
//
//            Toast.makeText(activity, "Failed to Update", Toast.LENGTH_SHORT).show()
//
//        }}




}