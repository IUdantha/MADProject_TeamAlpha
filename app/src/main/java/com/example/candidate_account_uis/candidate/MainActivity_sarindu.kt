package com.example.candidate_account_uis.candidate

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.candidateActivities.BaseActivity
import com.example.candidate_account_uis.candidateActivities.ProfileSettingsActivity
import com.example.candidate_account_uis.candidateActivities.SignInActivity
import com.example.candidate_account_uis.candidateActivities.job
import com.example.candidate_account_uis.databinding.ActivityMainSarinduBinding
import com.example.candidate_account_uis.model.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class MainActivity_sarindu : BaseActivity(), communicator, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainSarinduBinding
    private lateinit var database : DatabaseReference

    //------------------------------------------------------------------------
    private lateinit var mUserName: String
    //------------------------------------------------------------------------


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainSarinduBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.jobs -> replaceFragment(JobFragment())
                R.id.favourites -> replaceFragment(FavouritesFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else -> {}
            }
            true
        }

        //------------------------------------------------------------------------
        val nav_view: NavigationView = findViewById(R.id.nav_view)
        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        nav_view.setNavigationItemSelectedListener(this)
        //------------------------------------------------------------------------


    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }



    override fun passData1(editTextData: String) {
        val bundle = Bundle()
        bundle.putString("we_are_seeking22",editTextData)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentb = Experience_edit_frag()

        fragmentb.arguments = bundle
        transaction.replace(R.id.frame_layout,fragmentb).commit()
    }

    override fun passData2(editTextData: String) {
        val bundle = Bundle()
        bundle.putString("we_are_seeking222",editTextData)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentc = Skills_edit_frag()

        fragmentc.arguments = bundle
        transaction.replace(R.id.frame_layout,fragmentc).commit()
    }

    override fun passData3(editTextData: String) {
        val bundle = Bundle()
        bundle.putString("we_are_seeking2",editTextData)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentd = Education_edit_frag()

        fragmentd.arguments = bundle
        transaction.replace(R.id.frame_layout,fragmentd).commit()
    }


    //------------------------------------------------------------------------

    private fun toggleDrawer() {
        val drawer_layout: DrawerLayout = findViewById(R.id.drawer_layout)

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    /**
     * A function to get the current user details from firebase.
     */
    fun updateNavigationUserDetails(user: User) {

        hideProgressDialog()

        mUserName = user.name

        // The instance of the header view of the navigation view.
        val nav_view: NavigationView = findViewById(R.id.nav_view)
        val headerView = nav_view.getHeaderView(0)

        // The instance of the user image of the navigation view.
        val navUserImage = headerView.findViewById<ImageView>(R.id.iv_user_image)

        // Load the user image in the ImageView.
        Glide
            .with(this@MainActivity_sarindu)
            .load(user.image) // URL of the image
            .centerCrop() // Scale type of the image.
            .placeholder(R.drawable.ic_user_place_holder) // A default place holder
            .into(navUserImage) // the view in which the image will be loaded.

        // The instance of the user name TextView of the navigation view.
        val navUsername = headerView.findViewById<TextView>(R.id.tv_username)
        // Set the user name
        navUsername.text = "GAMAGE" //user.name

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val drawer_layout: DrawerLayout = findViewById(R.id.drawer_layout)

        when (menuItem.itemId) {
            R.id.nav_my_profile -> {
                val intent = Intent(this, ProfileSettingsActivity::class.java)
                startActivity(intent)

                Toast.makeText(this@MainActivity_sarindu, "My Profile", Toast.LENGTH_SHORT).show()
                print("I am working")
            }

            R.id.nav_sign_out -> {
                // Here sign outs the user from firebase in this device.
                FirebaseAuth.getInstance().signOut()

                // Send the user to the intro screen of the application.
                val intent = Intent(this, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        // END
        return true
    }
}