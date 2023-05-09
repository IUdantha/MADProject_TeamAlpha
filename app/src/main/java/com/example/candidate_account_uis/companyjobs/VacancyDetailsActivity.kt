package com.example.candidate_account_uis.companyjobs

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R
import com.google.firebase.database.FirebaseDatabase


class VacancyDetailsActivity: AppCompatActivity() {

    private lateinit var tvVacId: TextView
    private lateinit var tvJbRole: TextView
    private lateinit var tvJD: TextView
    private lateinit var tvOv: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancy_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("vid").toString(),
                intent.getStringExtra("jbRl").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("vid").toString()
            )
        }
    }


    private fun initView(){
        tvVacId = findViewById(R.id.tvVacId)
        tvJbRole = findViewById(R.id.tvJbRole)
        tvJD = findViewById(R.id.tvJD)
        tvOv = findViewById(R.id.tvOv)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){

        tvVacId.text=intent.getStringExtra("vid")
        tvJbRole.text=intent.getStringExtra("jbRl")
        tvJD.text=intent.getStringExtra("jobDes")
        tvOv.text=intent.getStringExtra("comOver")
    }
    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Vacancies").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Vacancy  deleted successfully", Toast.LENGTH_LONG).show()

            val intent = Intent(this, VacancyFetching::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }

        private fun openUpdateDialog(
        vid: String,
        jbRl: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_vacancy, null)

        mDialog.setView(mDialogView)

        val etJbRole = mDialogView.findViewById<EditText>(R.id.etJbRole)
        val etJD = mDialogView.findViewById<EditText>(R.id.etJD)
        val etOv = mDialogView.findViewById<EditText>(R.id.etOv)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etJbRole.setText(intent.getStringExtra("jbRl").toString())
        etJD.setText(intent.getStringExtra("jobDes").toString())
        etOv.setText(intent.getStringExtra("comOver").toString())

        mDialog.setTitle("Updating $jbRl Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateVacData(
                vid,
                etJbRole.text.toString(),
                etJD.text.toString(),
                etOv.text.toString()
            )
            Toast.makeText(applicationContext, "Vacancy Details Updated Successfully", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textview
            tvJbRole.text=etJbRole.text.toString()
            tvJD.text= etJD.text.toString()
            tvOv.text= etOv.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateVacData(
        id:String,
        job:String,
        description:String,
        overview:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Vacancies").child(id)
        val vacInfo = VacancyModel(id, job, description, overview)
        dbRef.setValue(vacInfo)
    }
}