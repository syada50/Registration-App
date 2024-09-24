package com.example.userregistrationnsdajob2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.app.ProgressDialog as AndroidAppProgressDialog
import android.os.Handler

class AddProfileActivity : AppCompatActivity() {
    private lateinit var profileViewModel: UserProfileViewModel
    private lateinit var progressDialog: AndroidAppProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile)

        profileViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        val nameEditText = findViewById<EditText>(R.id.profileNameEt)
        val emailEditText = findViewById<EditText>(R.id.profileEmailEt)
        val dobEditText = findViewById<EditText>(R.id.profileDOBEt)
        val districtEditText = findViewById<EditText>(R.id.profileDistrictEt)
        val mobileEditText = findViewById<EditText>(R.id.profilemobileEt)

        // Initialize ProgressDialog
        progressDialog = AndroidAppProgressDialog(this).apply {
            setMessage("Adding profile...")
            setCancelable(false)
        }

        findViewById<Button>(R.id.addBtn).setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val dob = dobEditText.text.toString()
            val district = districtEditText.text.toString()
            val mobile = mobileEditText.text.toString()

            // Validate inputs
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email.isEmpty() || !isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (dob.isEmpty() || !isValidDate(dob)) {
                Toast.makeText(this, "Please enter a valid date of birth (YYYY-MM-DD)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (district.isEmpty()) {
                Toast.makeText(this, "Please enter your district", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (mobile.isEmpty() || !isValidMobile(mobile)) {
                Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show loading spinner
            progressDialog.show()

            // Proceed to create the user profile
            val userProfile = UserProfile(name = name, email = email, dob = dob, district = district, mobile = mobile)

            // Insert user profile in ViewModel
            profileViewModel.insertUserProfile(userProfile)

            // Simulate a network delay with Handler (for demonstration)
            Handler().postDelayed({
                progressDialog.dismiss() // Dismiss the loading spinner

                // Assuming the insert operation is successful; adjust as necessary
                Toast.makeText(this, "Profile added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }, 3000) // Show for 3 seconds
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidDate(date: String): Boolean {
        val regex = Regex("""\d{4}-\d{2}-\d{2}""")
        return date.matches(regex)
    }

    private fun isValidMobile(mobile: String): Boolean {
        return mobile.length == 11 && mobile.all { it.isDigit() }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::progressDialog.isInitialized && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}
