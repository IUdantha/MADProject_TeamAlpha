package com.example.candidate_account_uis.candidate.favjobpage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidate.ApplicationFormFragment
import com.example.candidate_account_uis.companyjobs.VacAdapter
import com.example.candidate_account_uis.companyjobs.VacancyFetching
import com.google.firebase.database.FirebaseDatabase

class FavAdapter : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    private val favJobList = ArrayList<FavJobModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_favourite_jobs_component,
            parent,false
        )

        return FavViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {

        val currentitem = favJobList[position]

        holder.title.text = currentitem.title
        holder.company.text = currentitem.company
        holder.description.text = currentitem.description

    }

    override fun getItemCount(): Int {

        return favJobList.size
    }

    fun updateFavJobList(favJobList : List<FavJobModel>){

        this.favJobList.clear()
        this.favJobList.addAll(favJobList)
        notifyDataSetChanged()
    }
//-----------------------------------------------------------------


    class  FavViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val title : TextView = itemView.findViewById(R.id.job_title)
        val company : TextView = itemView.findViewById(R.id.job_company)
        val description : TextView = itemView.findViewById(R.id.job_description)

        val applybutton : Button = itemView.findViewById(R.id.Fav_Job_apply)

    }



    private fun deleteRecord(title: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("jobs").child(title)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            //Toast.makeText(this, "Vacancy  deleted successfully", Toast.LENGTH_LONG).show()

//            val intent = Intent(this, VacancyFetching::class.java)
//            finish()
//            startActivity(intent)
        }.addOnFailureListener{ error ->
            //Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }

}
