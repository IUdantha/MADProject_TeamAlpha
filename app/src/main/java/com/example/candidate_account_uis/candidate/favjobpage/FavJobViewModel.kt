package com.example.candidate_account_uis.candidate.favjobpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavJobViewModel : ViewModel() {

    private val rep: FavRep
    private val _allFavJobs = MutableLiveData<List<FavJobModel>>()
    val allFavJobs : LiveData<List<FavJobModel>> = _allFavJobs

    init {
        rep = FavRep().getInstance()
        rep.loadFavJobs(_allFavJobs)
    }
}