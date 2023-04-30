package com.example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.job

class t_job_list_adapter(var jobList: ArrayList<job>):RecyclerView.Adapter<t_job_list_adapter.ViewHolder>()  {
    class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val job_title: TextView = itemview.findViewById(R.id.job_title)
        val job_company: TextView = itemview.findViewById(R.id.job_company)
        val job_description: TextView = itemview.findViewById(R.id.job_description)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context)
            .inflate(R.layout.t_job_list_component,parent,false)
        return ViewHolder(itemview)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentitem = jobList[position]

        holder.job_title.text = currentitem.title
        holder.job_company.text = currentitem.company
        holder.job_description.text = currentitem.description
    }
    override fun getItemCount(): Int {
        return jobList.size
    }

    fun setFilteredList(job: List<job>){
        this.jobList = job as ArrayList<job>
        notifyDataSetChanged()
    }

}