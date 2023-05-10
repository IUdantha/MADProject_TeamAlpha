package com.example.candidate_account_uis.candidate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.databinding.FragmentSkillsEditFragBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Skills_edit_frag : Fragment() {

    private var _binding: FragmentSkillsEditFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : DatabaseReference

    var output2 : String ?= ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSkillsEditFragBinding.inflate(inflater, container, false)
        val textview2 : TextView = binding.weAreSeeking222

        output2 = arguments?.getString("we_are_seeking222")
        textview2.text = output2

        binding.saveskill.setOnClickListener {

//---------------------------------------------???????
            val userName = "amal"
//---------------------------------------------???????

            val skil = binding.weAreSeeking222.text.toString()

            updateData(userName,skil)
        }

        return binding.root
    }


    private fun updateData(userName: String, skil: String) {

        database = FirebaseDatabase.getInstance().getReference("UserproD")
        val userproD = mapOf<String,String>(
//            "experience" to exp,
            "skills" to skil,
//            "eduction" to edu
        )

        database.child(userName).updateChildren(userproD).addOnSuccessListener {

//            binding.userName.text.clear()
//            binding.firstName.text.clear()
//            binding.lastname.text.clear()
//            binding.age.text.clear()
            Toast.makeText(activity, "Successfuly Updated", Toast.LENGTH_SHORT).show()

            //------
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, ProfileFragment())?.commit()
            //--------
            //readData("amal")

        }.addOnFailureListener{

            Toast.makeText(activity, "Failed to Update", Toast.LENGTH_SHORT).show()

        }}

}