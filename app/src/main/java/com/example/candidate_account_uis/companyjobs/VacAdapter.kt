package com.example.candidate_account_uis.companyjobs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.candidate_account_uis.R

class VacAdapter(private val vacList:ArrayList<VacancyModel>) :
    RecyclerView.Adapter<VacAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_vacancy_list, parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = vacList[position]
        holder.tvVName.text = currentEmp.jbRl
    }

    override fun getItemCount(): Int {
        return vacList.size
    }
    class ViewHolder(itemView: View, clickListener : onItemClickListener): RecyclerView.ViewHolder(itemView){
        val tvVName : TextView = itemView.findViewById(R.id.vName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}
