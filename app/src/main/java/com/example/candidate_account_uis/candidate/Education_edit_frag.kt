package com.example.candidate_account_uis.candidate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.databinding.FragmentEducationEditFragBinding
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class Education_edit_frag : Fragment() {

    private var _binding: FragmentEducationEditFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : DatabaseReference

    var output1 : String ?= ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEducationEditFragBinding.inflate(inflater, container, false)

        val textview : TextView = binding.weAreSeeking2

        output1 = arguments?.getString("we_are_seeking2")
        textview.text = output1

        binding.saveedu.setOnClickListener {

            val edu = binding.weAreSeeking2.text.toString()
            updateData(edu)
        }

        return binding.root
    }

    private fun updateData(edu: String) {

        val db = FirebaseFirestore.getInstance()
        val collectionUsers = db.collection("users")

        val nowUser = FirestoreClass().getCurrentUserID()
        val userDocRef = collectionUsers.document(nowUser)

        val updates = hashMapOf<String, Any>(
            "education" to edu
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

//        database = FirebaseDatabase.getInstance().getReference("UserproD")
//        val userproD = mapOf<String,String>(
////            "experience" to exp,
////            "skills" to skil,
//            "eduction" to edu
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
//        }.addOnFailureListener{
//
//            Toast.makeText(activity, "Failed to Update", Toast.LENGTH_SHORT).show()
//
//        }}


}