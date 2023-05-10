package com.example.candidate_account_uis.candidate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.databinding.FragmentJobBinding


class JobFragment : Fragment() {


    private var _binding: FragmentJobBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJobBinding.inflate(inflater, container, false)

        binding.gotoapplicationbutton.setOnClickListener{

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,ApplicationFormFragment())?.commit()
        }

        return binding.root
    }


}