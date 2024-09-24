package com.example.userregistrationnsdajob2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
        profileViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        // Setup RecyclerView and Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.profileRecyclerView)
        profileAdapter = ProfileAdapter()

        recyclerView.adapter = profileAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize loading spinner and text
        loadingSpinner = findViewById(R.id.loadingSpinner)
        loadingText = findViewById(R.id.loadingText)

        // Show loading spinner
        showLoading()

        // Simulate a 3-second delay before loading profiles
        Handler(Looper.getMainLooper()).postDelayed({
            loadProfiles()
        }, 3000) // 3-second delay

        // Handle add profile button click
        findViewById<FloatingActionButton>(R.id.addProfileBtn).setOnClickListener {
            val intent = Intent(this, AddProfileActivity::class.java)
            startActivity(intent)
        }
    }

    // Show spinner and reloading text
    private fun showLoading() {
        loadingSpinner.visibility = View.VISIBLE
        loadingText.visibility = View.VISIBLE
    }

    // Hide spinner and reloading text
    private fun hideLoading() {
        loadingSpinner.visibility = View.GONE
        loadingText.visibility = View.GONE
    }

    // Function to load profiles and hide the loading spinner
    private fun loadProfiles() {
        // Observe the LiveData from the ViewModel to update the UI
        profileViewModel.getUserProfiles().observe(this, Observer { profiles ->
            profiles?.let {
                hideLoading()
                profileAdapter.submitList(it)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        // Optionally handle loading when returning to the activity
    }
}
