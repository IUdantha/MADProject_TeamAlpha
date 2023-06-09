package com.example.candidate_account_uis.company.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidate.ApplicationData
import com.example.candidate_account_uis.company.CompanyDeleteAccountActivity
import com.example.candidate_account_uis.company.CompanyInterviewActivity


class interestedCandidatesAdapter:RecyclerView.Adapter<interestedCandidatesAdapter.ViewHolder>() {

    lateinit var data: List<ApplicationData>
    private var filteredData: List<ApplicationData> = emptyList()
    lateinit var context: Context


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val candidateComView: LinearLayout = view.findViewById(R.id.candidateComView)
        val candidateComName: TextView = view.findViewById(R.id.candidateComName)
        val candidateComEmail: TextView = view.findViewById(R.id.candidateComEmail)
        val candidateComContact: TextView = view.findViewById(R.id.candidateComContact)
        val candidateComUni: TextView = view.findViewById(R.id.candidateComUni)
        val candidateComPic: ImageView = view.findViewById(R.id.candidateComPic)

        init {
            candidateComView.setOnClickListener {
                val intent = Intent(context, CompanyInterviewActivity::class.java)
                intent.putExtra("email", candidateComEmail.text)
                context.startActivity(intent)
            }
        }

    }


    fun setData(data: List<ApplicationData>, context: Context) {
        this.data = data
        this.filteredData = data
        this.context = context
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.display_com_candidate,parent,false)
        return ViewHolder(view)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]

        holder.candidateComName.text = currentItem.fullname
        holder.candidateComEmail.text = currentItem.email
        holder.candidateComContact.text = currentItem.contactnumber
        holder.candidateComUni.text = currentItem.university
        Glide.with(context)
            .load(currentItem.imagepath)
            .circleCrop()
            .into(holder.candidateComPic)

    }
    override fun getItemCount(): Int {
        return if(::data.isInitialized) data.size else 0
    }

    fun getData(data: List<ApplicationData>, context: Context) {
        this.data = data
        return
    }

    fun filter(query: String) {
        filteredData = if (query.isEmpty()) {
            data
        } else {
            data.filter {
                it.fullname!!.contains(query, ignoreCase = true) ||
                        it.email!!.contains(query, ignoreCase = true) ||
                        it.contactnumber!!.contains(query, ignoreCase = true) ||
                        it.university!!.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}


