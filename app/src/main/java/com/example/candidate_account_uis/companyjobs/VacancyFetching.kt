package com.example.candidate_account_uis.companyjobs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.R
// fetches and displays a list of vacancies from a Firebase Realtime Database
class VacancyFetching: AppCompatActivity() {

    private lateinit var vacncyRecyclerView: RecyclerView

    //    private lateinit var tvLoadingData: TextView
    private lateinit var vacList: ArrayList<VacancyModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.candidate_account_uis.R.layout.activity_added_vacancies)
//RecyclerView object that is used to display the list of vacancies.
        vacncyRecyclerView = findViewById(com.example.candidate_account_uis.R.id.rvVacancy)
        vacncyRecyclerView.layoutManager = LinearLayoutManager(this)
        vacncyRecyclerView.setHasFixedSize(true)

//        tvLoadingData = findViewById(R.id.tvLoadingData)
//an ArrayList of VacancyModel objects that holds the vacancies fetched from the database
        vacList = arrayListOf<VacancyModel>()

        getVacanciesData()

    }

    private fun getVacanciesData() {

        vacncyRecyclerView.visibility = View.GONE
//        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Vacancies")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                vacList.clear()
                if (snapshot.exists()){
                    for (vacSnap in snapshot.children){
                        val vacData = vacSnap.getValue(VacancyModel::class.java)
                        vacList.add(vacData!!)
                    }
                    val mAdapter = VacAdapter(vacList)
                    vacncyRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : VacAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@VacancyFetching, VacancyDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("vid", vacList[position].vacancyID)
                            intent.putExtra("jbRl", vacList[position].jobTitle)
                            intent.putExtra("jobDes", vacList[position].jobDescription)
                            intent.putExtra("comOver", vacList[position].companyOverview)

                            startActivity(intent)

                        }


                    })

                    vacncyRecyclerView.visibility = View.VISIBLE
//                    tvLoadingData.visibility = View.GONE
                }
            }



            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        }

    }
