package com.example.candidate_account_uis.companyjobs

    data class VacancyModel(
        var vacancyID: String? = null,
        var jobTitle: String? = null,
        var jobDescription: String? = null,
        var companyOverview: String?=null,
        var salary: String?=null
    )
//used to represent a single vacancy in the application. It has five properties