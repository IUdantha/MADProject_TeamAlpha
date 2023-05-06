package com.example.candidate_account_uis.company.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.company.entities.companyDetails

class companyDetailsAdapter:RecyclerView.Adapter<companyDetailsAdapter.ViewHolder>() {

    lateinit var data: List<companyDetails>
    lateinit var context: Context


    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){

            val edtComName: TextView = view.findViewById(R.id.comNameP)
            val edtComContact: TextView = view.findViewById(R.id.comContactP)
            val edtComEmail: TextView = view.findViewById(R.id.comEmailP)
            val edtComDescription: TextView = view.findViewById(R.id.comDescriptionP)

        }

        fun setData(data: List<companyDetails>, context: Context) {
            this.data = data
            this.context = context
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.com_profile,parent,false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = data[position]

            holder.edtComName.text = currentItem.name
            holder.edtComContact.text = currentItem.contact.toString()
            holder.edtComEmail.text = currentItem.email
            holder.edtComDescription.text = currentItem.description
        }
        override fun getItemCount(): Int {
            return if(::data.isInitialized) data.size else 0
        }




}
