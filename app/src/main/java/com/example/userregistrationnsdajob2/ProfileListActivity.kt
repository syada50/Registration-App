package com.example.userregistrationnsdajob2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileListActivity : AppCompatActivity() {

    private lateinit var profileViewModel: UserProfileViewModel
    private lateinit var profileAdapter: ProfileAdapter

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

        // Observe the LiveData from the ViewModel to update the UI
        profileViewModel.getUserProfiles().observe(this, Observer { profiles ->
            profiles?.let { profileAdapter.submitList(it) }
        })

        // Handle profile item clicks
        profileAdapter.setOnItemClickListener { userProfile ->
            val intent = Intent(this, ProfileDetailActivity::class.java)
            intent.putExtra("USER_PROFILE", userProfile)
            startActivity(intent)
        }

        // Handle profile delete action
        profileAdapter.setOnDeleteClickListener { userProfile ->
            profileViewModel.deleteUserProfile(userProfile)
        }

        // Handle profile update action
        profileAdapter.setOnUpdateClickListener { userProfile ->
            val intent = Intent(this, UpdateProfileActivity::class.java)
            intent.putExtra("USER_PROFILE", userProfile)
            startActivity(intent)
        }

        // Handle add profile button click
        findViewById<FloatingActionButton>(R.id.addProfileBtn).setOnClickListener {
            val intent = Intent(this, AddProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
