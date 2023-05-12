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
    //several private properties, including EditText fields
    private lateinit var jobRole: EditText
    private lateinit var jobDesc: EditText
    private lateinit var companyOver: EditText
    private lateinit var salary1: EditText
    private lateinit var btnSubmitData: Button
    private lateinit var btnCancelData: Button

    private lateinit var dbRef: DatabaseReference

    //The onCreate method is called when the activity is created
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   // sets the content view to a layout file called activity_vacancy_scroll
        setContentView(R.layout.activity_vacancy_scroll)
   //EditText and Button fields by finding them in the layout file using their resource IDs.
        jobRole = findViewById(R.id.textViewJobRoleFill)
        jobDesc = findViewById(R.id.textViewJobDesFill)
        companyOver = findViewById(R.id.textViewOverViewFill)
        salary1 = findViewById(R.id.textViewJobRoleFill2)
        btnSubmitData = findViewById(R.id.submit)
        btnCancelData = findViewById(R.id.button7)


        // Initialize Firebase Realtime Database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Vacancies")

        //saveVacancyData method is called when the user clicks the submit button
        btnSubmitData.setOnClickListener {
            saveVacancyData()
        }

    }

    private fun saveVacancyData(){
      //  EditText fields and checks that each field has been filled in. If any field is empty, the method displays an error message.
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
     // generates a unique ID for the new vacancy
            val vid = dbRef.push().key!!
     //creates a new VacancyModel object using the entered data, and adds the vacancy to the Firebase Realtime Database
            val vacancy = VacancyModel(vid, jbRl, jobDes, comOver, sal)

           // If the vacancy is added successfully, the method displays a success message
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