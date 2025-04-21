package com.example.mybirkbeckapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.content.edit

class StaffActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff)

        // Setup Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup Drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        // Setup menu icon click listener
        val menuButton = findViewById<ImageView>(R.id.menu_icon)
        menuButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        // Optionally handle profile icon click
        val profileIcon = findViewById<ImageView>(R.id.profile_icon)
        profileIcon.setOnClickListener {
            // Handle profile icon click (e.g., open profile settings)
        }

        // Handle navigation menu item clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Close the drawer - this is what was missing
                    drawerLayout.closeDrawer(GravityCompat.START)
                    // No need to navigate anywhere since we're already home
                }
                R.id.nav_logout -> {
                    // Disable auto-login by updating SharedPreferences
                    val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
                    sharedPreferences.edit { putBoolean("auto_login", false) }

                    // Navigate to login screen
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else -> {
                    // Handle other menu items if needed
                }
            }
            // Always close drawer and return true to indicate item was handled
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }}