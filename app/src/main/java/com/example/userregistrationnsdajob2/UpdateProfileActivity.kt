package com.example.userregistrationnsdajob2

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.app.ProgressDialog as AndroidAppProgressDialog

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var profileViewModel: UserProfileViewModel
    private lateinit var userProfile: UserProfile

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var districtEditText: EditText
    private lateinit var mobileEditText: EditText
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressDialog: AndroidAppProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        // Initialize ViewModel
        profileViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        // Retrieve user profile from intent with null safety
        userProfile = intent.getSerializableExtra("USER_PROFILE") as? UserProfile ?: run {
            Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if no profile is found
            return
        }

        // Initialize UI elements
        nameEditText = findViewById(R.id.profileNameEt)
        emailEditText = findViewById(R.id.profileEmailEt)
        dobEditText = findViewById(R.id.profileDOBEt)
        districtEditText = findViewById(R.id.profileDistrictEt)
        mobileEditText = findViewById(R.id.profilemobileEt)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        // Initialize ProgressDialog
        progressDialog = AndroidAppProgressDialog(this).apply {
            setMessage("Updating profile...")
            setCancelable(false)
        }

        // Populate fields with user profile data
        populateFields()

        // Set up the update button click listener
        findViewById<Button>(R.id.updateBtn).setOnClickListener {
            updateUserProfile()
        }

        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            refreshContent()
        }
    }

    // Populate the fields with the user's profile data
    private fun populateFields() {
        nameEditText.setText(userProfile.name)
        emailEditText.setText(userProfile.email)
        dobEditText.setText(userProfile.dob)
        districtEditText.setText(userProfile.district)
        mobileEditText.setText(userProfile.mobile)
    }

    // Update the user profile with new data
    private fun updateUserProfile() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val dob = dobEditText.text.toString().trim()
        val district = districtEditText.text.toString().trim()
        val mobile = mobileEditText.text.toString().trim()

        // Validate input
        if (name.isEmpty() || email.isEmpty() || dob.isEmpty() || district.isEmpty() || mobile.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create updated user profile object
        val updatedUserProfile = UserProfile(
            id = userProfile.id,
            name = name,
            email = email,
            dob = dob,
            district = district,
            mobile = mobile
        )

        // Show loading spinner
        progressDialog.show()

        // Update the profile in the ViewModel
        profileViewModel.updateUserProfile(updatedUserProfile)

        // Simulate a delay for the update process
        Handler().postDelayed({
            progressDialog.dismiss() // Dismiss the loading spinner
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }, 3000) // Show for 3 seconds
    }

    // Refresh the content
    private fun refreshContent() {
        // Show the refresh indicator
        swipeRefreshLayout.isRefreshing = true

        // Simulate a delay for refreshing
        swipeRefreshLayout.postDelayed({
            // Re-populate fields with current data (could also fetch new data if necessary)
            populateFields() // Ensure the fields are up-to-date
            swipeRefreshLayout.isRefreshing = false // Stop the refreshing animation
            Toast.makeText(this, "Content refreshed", Toast.LENGTH_SHORT).show()
        }, 2000) // Delay for 2 seconds
    }
}


