package com.example.candidate_account_uis.companyjobs
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.candidate_account_uis.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
//import kotlinx.android.synthetic.main.activity_vacancy_scroll.*

class VacancyAdd : AppCompatActivity() {

    private lateinit var jobRole: EditText
    private lateinit var jobDesc: EditText
    private lateinit var companyOver: EditText
    private lateinit var salary1: EditText
    private lateinit var btnSubmitData: Button
    private lateinit var btnCancelData: Button

    private lateinit var dbRef: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancy_scroll)

        jobRole = findViewById(R.id.textViewJobRoleFill)
        jobDesc = findViewById(R.id.textViewJobDesFill)
        companyOver = findViewById(R.id.textViewOverViewFill)
        salary1 = findViewById(R.id.textViewJobRoleFill2)
        btnSubmitData = findViewById(R.id.submit)
        btnCancelData = findViewById(R.id.button7)

        dbRef = FirebaseDatabase.getInstance().getReference("Vacancies")

        btnSubmitData.setOnClickListener {
            saveVacancyData()
        }

    }

    private fun saveVacancyData(){

        val jbRl = jobRole.text.toString()
        val jobDes = jobDesc.text.toString()
        val comOver = companyOver.text.toString()
        val sal = salary1.text.toString()

        if (jbRl.isEmpty()) {
            jobRole.error = "Please fill the field"
        }
        if (jobDes.isEmpty()) {
            jobDesc.error = "Please  fill the field"
        }
        if (comOver.isEmpty()) {
            companyOver.error = "Please fill the field"
        }

        if (sal.isEmpty()) {
            salary1.error = "Please fill the field"
        }
        else {

            val vid = dbRef.push().key!!

            val vacancy = VacancyModel(vid, jbRl, jobDes, comOver, sal)

            dbRef.child(vid).setValue(vacancy)
                .addOnCompleteListener {
                    Toast.makeText(this, "Vacancy added successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, VacancyFetching::class.java)
                    startActivity(intent)


                    jobRole.text.clear()
                    jobDesc.text.clear()
                    companyOver.text.clear()
                    salary1.text.clear()

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}