package com.example.mybirkbeckapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var isPasswordVisible = false
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)

        // Check if auto-login is enabled
        val isAutoLoginEnabled = sharedPreferences.getBoolean("auto_login", false)
        val savedUsername = sharedPreferences.getString("username", "")

        // If auto-login is enabled and we have a username
        if (isAutoLoginEnabled && !savedUsername.isNullOrEmpty()) {
            // Determine if it's a staff account
            val isStaff = savedUsername.startsWith("staff.")

            // Skip to welcome activity
            val welcomeIntent = Intent(this, WelcomeActivity::class.java).apply {
                putExtra("USERNAME", savedUsername)
                putExtra("IS_STAFF", isStaff)
            }
            startActivity(welcomeIntent)
            finish() // Close login activity
            return // Skip the rest of onCreate
        }

        // If auto-login is not enabled, continue with normal login screen
        setContentView(R.layout.activity_main)

        // Find views
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val rememberMeCheckbox = findViewById<CheckBox>(R.id.rememberMeCheckbox)
        val autoLoginCheckbox = findViewById<CheckBox>(R.id.autoLoginCheckbox) // Add this checkbox to your layout
        val togglePasswordVisibility = findViewById<ImageButton>(R.id.togglePasswordVisibility)

        // Load saved credentials if "Remember Me" was checked
        if (!savedUsername.isNullOrEmpty()) {
            val savedPassword = sharedPreferences.getString("password", "")
            usernameEditText.setText(savedUsername)
            passwordEditText.setText(savedPassword)
            rememberMeCheckbox.isChecked = true

            // Check if auto-login was previously enabled
            autoLoginCheckbox.isChecked = isAutoLoginEnabled
        }

        // Password visibility toggle
        togglePasswordVisibility.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglePasswordVisibility.setImageResource(R.drawable.ic_visibility)
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglePasswordVisibility.setImageResource(R.drawable.ic_visibility_off)
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        // Login button click
        findViewById<Button>(R.id.loginButton).setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Save credentials and auto-login preference
                with(sharedPreferences.edit()) {
                    if (rememberMeCheckbox.isChecked) {
                        putString("username", username)
                        putString("password", password)
                    } else {
                        remove("username")
                        remove("password")
                    }

                    // Save auto-login preference
                    putBoolean("auto_login", autoLoginCheckbox.isChecked)

                    apply()
                }

                // Check if user is staff
                val isStaff = username.startsWith("staff.")

                // Navigate to Welcome Activity
                val welcomeIntent = Intent(this, WelcomeActivity::class.java).apply {
                    putExtra("USERNAME", username)
                    putExtra("IS_STAFF", isStaff)
                }
                startActivity(welcomeIntent)
            } else {
                Toast.makeText(this, "Please enter credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


