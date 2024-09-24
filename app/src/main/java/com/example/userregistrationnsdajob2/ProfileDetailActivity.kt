package com.example.userregistrationnsdajob2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class ProfileDetailActivity : AppCompatActivity() {
    private lateinit var userProfile: UserProfile

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var dobTextView: TextView
    private lateinit var districtTextView: TextView
    private lateinit var mobileTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)

        // Retrieve the user profile from the Intent with a null check
        userProfile = intent.getSerializableExtra("USER_PROFILE") as? UserProfile
            ?: run {
                Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show()
                finish() // Close the activity if no profile is found
                return
            }

        // Initialize TextViews
        nameTextView = findViewById(R.id.nameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        dobTextView = findViewById(R.id.dobTextView)
        districtTextView = findViewById(R.id.districtTextView)
        mobileTextView = findViewById(R.id.mobileTextView)

        // Populate the fields with user data
        populateFields()

        // Set up the Edit button click listener
        val editProfileButton: ImageButton = findViewById(R.id.editBtn)
        editProfileButton.setOnClickListener {
            // Start UpdateProfileActivity, passing the user profile
            val intent = Intent(this, UpdateProfileActivity::class.java)
            intent.putExtra("USER_PROFILE", userProfile)
            startActivity(intent)
        }
    }

    private fun populateFields() {
        nameTextView.text = userProfile.name
        emailTextView.text = userProfile.email
        dobTextView.text = userProfile.dob
        districtTextView.text = userProfile.district
        mobileTextView.text = userProfile.mobile
    }
}
