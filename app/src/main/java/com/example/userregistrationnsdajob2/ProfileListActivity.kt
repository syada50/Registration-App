package com.example.userregistrationnsdajob2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileListActivity : AppCompatActivity() {

    private lateinit var profileViewModel: UserProfileViewModel
    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var loadingSpinner: ProgressBar
    private lateinit var loadingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_list)

        // Initialize ViewModel
        profileViewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]

        // Setup RecyclerView and Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.profileRecyclerView)
        profileAdapter = ProfileAdapter()
        recyclerView.adapter = profileAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize loading spinner and text
        loadingSpinner = findViewById(R.id.loadingSpinner)
        loadingText = findViewById(R.id.loadingText)

        // Set up click listener for the edit button
        profileAdapter.setOnUpdateClickListener { userProfile ->
            val intent = Intent(this, UpdateProfileActivity::class.java)
            intent.putExtra("USER_PROFILE", userProfile)  // Pass the selected profile to UpdateProfileActivity
            startActivity(intent)
        }

        // Show loading spinner
        showLoading()

        // Load profiles directly without delay
        loadProfiles()

        // Handle add profile button click
        findViewById<FloatingActionButton>(R.id.addProfileBtn).setOnClickListener {
            val intent = Intent(this, AddProfileActivity::class.java)
            startActivity(intent)
        }
    }

    // Show spinner and loading text
    private fun showLoading() {
        loadingSpinner.visibility = View.VISIBLE
        loadingText.visibility = View.VISIBLE
    }

    // Hide spinner and loading text
    private fun hideLoading() {
        loadingSpinner.visibility = View.GONE
        loadingText.visibility = View.GONE
    }

    // Function to load profiles and hide the loading spinner
    private fun loadProfiles() {
        // Observe the LiveData from the ViewModel to update the UI
        profileViewModel.getUserProfiles().observe(this, Observer { profiles ->
            profiles?.let {
                hideLoading() // Hide loading when data is received
                profileAdapter.submitList(it) // Update the adapter with new profiles
            }
        })
    }

    override fun onStart() {
        super.onStart()
        loadProfiles() // Load profiles when the activity starts
    }
}
