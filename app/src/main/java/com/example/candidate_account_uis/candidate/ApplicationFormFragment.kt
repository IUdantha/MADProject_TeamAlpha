package com.example.candidate_account_uis.candidate

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.candidate_account_uis.databinding.FragmentApplicationFormBinding
import com.example.candidate_account_uis.databinding.FragmentHomeBinding
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class ApplicationFormFragment : Fragment() {

    //--------
    private var _binding: FragmentApplicationFormBinding? = null
    private val binding get() = _binding!!
//--------

    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //-----
        _binding = FragmentApplicationFormBinding.inflate(inflater, container, false)
        //-----

        binding.applicationConfirm.setOnClickListener {

            val fullname = binding.fullName.text.toString()
            val cno = binding.contactNo.text.toString()
            val nic = binding.nicNumber.text.toString()
            val email = binding.email.text.toString()
            val uni = binding.university.text.toString()

            if (fullname.isEmpty()) {
                binding.fullName.error = "Please enter your Full Name"
            }
            if (cno.isEmpty()) {
                binding.contactNo.error = "Please enter your contact No"
            }
//            if (!isValidPhoneNumber(cno)) {
//                binding.contactNo.error = "Please enter valid contact No"
//            }
            if (nic.isEmpty()) {
                binding.nicNumber.error = "Please enter your NIC No"
            }
            if (email.isEmpty()) {
                binding.email.error = "Please enter your email"
            }
//            if (!isValidEmail(email)) {
//                binding.email.error = "Please enter a valid email"
//            }
            if (uni.isEmpty()) {
                binding.university.error = "Please enter your university"
            } else {
                if (isValidEmail(email)) {

                    if (isValidPhoneNumber(cno)) {

                        val progressDialog = ProgressDialog(activity)
                        progressDialog.setMessage("Submitting ....")
                        progressDialog.setCancelable(false)
                        progressDialog.show()

                        val db = FirebaseFirestore.getInstance()
                        val collectionUsers = db.collection("users")
                        val nowUser = FirestoreClass().getCurrentUserID()
                        val query = collectionUsers.whereEqualTo("id", nowUser)

                        query.get().addOnSuccessListener { documents ->
                            if (documents.size() > 0) {
                                val document = documents.first()
                                val userid = document.getString("id")
                                val imagepath = document.getString("image")


                                database = FirebaseDatabase.getInstance()
                                    .getReference("CandidateAppication")

                                val applicationid = database.push().key!!

                                val applicationData = ApplicationData(userid, applicationid, fullname, cno, nic, email, uni, imagepath)

                                database.child(applicationid).setValue(applicationData)
                                    .addOnSuccessListener {

                                        binding.fullName.text.clear()
                                        binding.contactNo.text.clear()
                                        binding.nicNumber.text.clear()
                                        binding.email.text.clear()
                                        binding.university.text.clear()

                                        progressDialog.cancel()

                                        Toast.makeText(activity, "successfully submitted", Toast.LENGTH_SHORT).show()

                                    }.addOnFailureListener {

                                        progressDialog.cancel()
                                        Toast.makeText(activity, "submission failed", Toast.LENGTH_SHORT).show()
                                    }
                            }
}

                    } else {
                        binding.contactNo.error = "Please enter valid contact No"
                    }
                } else {
                    binding.email.error = "Please enter a valid email"
                }
            }
        }

        return binding.root
    }

}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
    return emailRegex.matches(email)
}

fun isValidPhoneNumber(phoneNum: String): Boolean {
    val phoneNumRegex = Regex("^\\d{10}\$")
    return phoneNumRegex.matches(phoneNum)
}






