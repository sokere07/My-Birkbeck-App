package com.example.mybirkbeckapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)

        // Get username from intent
        val username = intent.getStringExtra("USERNAME") ?: "User"

        // Get isStaff flag from intent (this would be passed from MainActivity)
        val isStaff = intent.getBooleanExtra("IS_STAFF", false)

        // Set welcome message
        val welcomeMessageTextView = findViewById<TextView>(R.id.welcomeText)
        welcomeMessageTextView.text = "Welcome, $username!"

        // Set a delay before proceeding to the appropriate activity
        Handler(Looper.getMainLooper()).postDelayed({
            if (username.startsWith("staff.")) {
                // Redirect to Staff Activity
                val intent = Intent(this, StaffActivity::class.java).apply {
                    putExtra("USERNAME", username)
                }
                startActivity(intent)
            } else {
                // Redirect to Student Dashboard/Profile
                val intent = Intent(this, ProfileActivity::class.java).apply {
                    putExtra("USERNAME", username)
                }
                startActivity(intent)
            }
            finish() // Close this activity so it's not in the back stack
        }, 2000) // 2 second delay, adjust as needed
    }
}