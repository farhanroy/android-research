package com.android.datastore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.android.datastore.utils.UserManager
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Init user manager
        userManager = UserManager(this)

        val button = findViewById<Button>(R.id.btn_logout)
        button.setOnClickListener { handleLogout() }
    }

    // Handling user logout
    private fun handleLogout() {
        lifecycleScope.launch {
            userManager.updateIsLoggedIn(false)
            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        }
    }
}