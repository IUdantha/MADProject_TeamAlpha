package com.example.candidate_account_uis.candidateActivities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.databinding.ActivitySignUpBinding
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.example.candidate_account_uis.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    //    normal authentication
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    //    google authentication
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient



    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.haveAccount.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            val confirmPass = binding.confPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (isValidEmail(email)) {
                    if (pass == confirmPass) {
                        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {

                                    // Firebase registered user
                                    val firebaseUser: FirebaseUser = it.result!!.user!!
                                    // Registered Email
                                    val registeredEmail = firebaseUser.email!!

                                    val user = User(
                                        firebaseUser.uid, registeredEmail
                                    )

                                    // call the registerUser function of FirestoreClass to make an entry in the database.
                                    FirestoreClass().registerUser(this@SignUpActivity, user)

                                    //added initial data to firestore
                                    initialData()

                                    val intent = Intent(this, SignInActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this, it.exception.toString(), Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Invalid Email address format", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields are Not Allowed!!", Toast.LENGTH_SHORT).show()
            }

        }

//        Google signin
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<ImageButton>(R.id.googlesigninbtn).setOnClickListener {
            signInGoogle()
        }
    }

    //initial data function
    fun initialData(){

        val db = FirebaseFirestore.getInstance()
        val collectionUsers = db.collection("users")

        val nowUser = FirestoreClass().getCurrentUserID()
        val userDocRef = collectionUsers.document(nowUser)

        val updates = hashMapOf<String, Any>(
            "name" to "No name",
            "image" to "https://firebasestorage.googleapis.com/v0/b/mad-candidate-dbs.appspot.com/o/images%2Fdefault_pro_img.png?alt=media&token=ca7f39d0-ddfe-472d-98c9-9845028f8dd4"
        )

        userDocRef.update(updates)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error updating document", e)
            }
    }

    //google
    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                // Firebase registered user
                val firebaseUser: FirebaseUser = it.result!!.user!!
                // Registered Email
                val registeredEmail = account.email

                val user = User(
                    firebaseUser.uid, registeredEmail!!
                )

                // call the registerUser function of FirestoreClass to make an entry in the database.
                FirestoreClass().registerUser(this@SignUpActivity, user)

                val intent: Intent = Intent(this, TrendingJobsActivity::class.java)
                intent.putExtra("email", account.email)
                intent.putExtra("name", account.displayName)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

            }
        }
    }

    //temporary code paste
    fun userRegisteredSuccess() {

        Toast.makeText(
            this@SignUpActivity,
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()

        // Hide the progress dialog
//        hideProgressDialog()

        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        FirebaseAuth.getInstance().signOut()
        // Finish the Sign-Up Screen
        finish()
    }

}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
    return emailRegex.matches(email)
}
