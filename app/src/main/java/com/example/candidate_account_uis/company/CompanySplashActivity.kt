package com.example.candidate_account_uis.company

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidateActivities.MainActivity
import com.example.candidate_account_uis.candidateActivities.SignInActivity
import com.example.candidate_account_uis.companyjobs.InterestedCandidates
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CompanySplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_splash)

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Adding the handler to after the a task after some delay.
        Handler().postDelayed({

            // Here if the user is signed in once and not signed out again from the app. So next time while coming into the app
            // we will redirect him to MainScreen or else to the Intro Screen as it was before.


            val database = FirebaseDatabase.getInstance().reference
            val currentUserID = FirebaseAuth.getInstance().currentUser?.uid

            // Get the current user id
            database.child("users").child(currentUserID.toString()).child("id").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() && snapshot.value != null) {
                        val userId = snapshot.value as String
                        if (userId.isNotEmpty()) {
                            // Start the Trending Job Activity
                            startActivity(Intent(this@CompanySplashActivity, InterestedCandidates::class.java))
                        } else {
                            // Start the SignIn Activity
                            startActivity(Intent(this@CompanySplashActivity, ComLogin::class.java))
                        }
                    } else {
                        // Start the SignIn Activity
                        startActivity(Intent(this@CompanySplashActivity, ComLogin::class.java))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                }
            })
            // Start the Intro Activity


            finish() // Call this when your activity is done and should be closed.
        }, 2500) // Here we pass the delay time in milliSeconds after which the splash activity will disappear.
    }
}