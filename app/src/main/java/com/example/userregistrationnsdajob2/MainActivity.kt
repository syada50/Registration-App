package com.example.userregistrationnsdajob2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var listButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listButton = findViewById(R.id.profileListBtn)

        listButton.setOnClickListener {
            startActivity(Intent(this, ProfileListActivity::class.java))
            finish()
        }
    }
}