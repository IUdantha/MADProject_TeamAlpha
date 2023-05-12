package com.example.candidate_account_uis.candidate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidate.favjobpage.FavAdapter
import com.example.candidate_account_uis.candidate.favjobpage.FavJobViewModel
import com.example.candidate_account_uis.databinding.FragmentFavouritesBinding

private lateinit var viewModel : FavJobViewModel
private lateinit var favJobRecyclerView: RecyclerView
lateinit var adapter: FavAdapter

private var _binding: FragmentFavouritesBinding? = null
private val binding get() = _binding!!

class FavouritesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

//        binding.Fav_Job_apply.setOnClickListener{
//
//            val transaction = activity?.supportFragmentManager?.beginTransaction()
//            transaction?.replace(R.id.frame_layout,ApplicationFormFragment())?.commit()
//        }



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favJobRecyclerView = view.findViewById(R.id.favourite_job_list)
        favJobRecyclerView.layoutManager = LinearLayoutManager(context)
        favJobRecyclerView.setHasFixedSize(true)
        adapter = FavAdapter()
        favJobRecyclerView.adapter = adapter


        viewModel = ViewModelProvider(this).get(FavJobViewModel::class.java)

        viewModel.allFavJobs.observe(viewLifecycleOwner, Observer {

            adapter.updateFavJobList(it)
        })


    }




}