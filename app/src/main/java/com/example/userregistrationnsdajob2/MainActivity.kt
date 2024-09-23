package com.example.userregistrationnsdajob2


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var listButton: Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listButton = findViewById(R.id.profileListBtn)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        listButton.setOnClickListener {
            startActivity(Intent(this, ProfileListActivity::class.java))
            finish()
        }


        swipeRefreshLayout.setOnRefreshListener {
            refreshContent() // Call refresh logic when swiped down
        }
    }

    private fun refreshContent() {
        // Simulate a data refresh or network call
        // For actual implementation, replace this with your data-fetching logic

        // Simulate a delay to show the refresh effect
        swipeRefreshLayout.postDelayed({
            // Stop the refreshing animation
            swipeRefreshLayout.isRefreshing = false

            // If you want to reload the activity as well, uncomment the line below
            // reloadActivity()
        }, 2000) // Delay for 2 seconds
    }

    private fun reloadActivity() {
        finish() // Finish the current instance of the activity
        startActivity(Intent(this, MainActivity::class.java)) // Start a new instance
    }
}
