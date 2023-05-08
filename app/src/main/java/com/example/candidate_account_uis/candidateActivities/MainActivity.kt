package com.example.candidate_account_uis.candidateActivities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.candidate_account_uis.R
import com.example.candidate_account_uis.firebase.FirestoreClass
import com.example.candidate_account_uis.model.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    // A global variable for User Name
    private lateinit var mUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

//        setupActionBar()

        val nav_view: NavigationView = findViewById(R.id.nav_view)
        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        nav_view.setNavigationItemSelectedListener(this)

    }

    /**
     * A function to setup action bar
     */
//    private fun setupActionBar() {
//        val toolbar_main_activity: Toolbar = findViewById(R.id.toolbar_main_activity)
//
//        setSupportActionBar(toolbar_main_activity)
//        toolbar_main_activity.setNavigationIcon(R.drawable.ic_action_navigation_menu)
//
//        toolbar_main_activity.setNavigationOnClickListener {
//            toggleDrawer()
//        }
//    }


    /**
     * A function for opening and closing the Navigation Drawer.
     */
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
            .with(this@MainActivity)
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

                Toast.makeText(this@MainActivity, "My Profile", Toast.LENGTH_SHORT).show()
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