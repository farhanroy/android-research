package com.android.datastore

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.datastore.utils.UserManager
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

/*
 *
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var userManager: UserManager

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init UserManager class
        userManager = UserManager(this)

        // Button action
        val button = findViewById<Button>(R.id.btn_login)
        button.setOnClickListener { handleLogin() }
    }

    // Handle login when text field is null
    private fun handleLogin() {
        val email = findViewById<TextInputLayout>(R.id.textInputLayout).editText?.text.toString()
        val pass = findViewById<TextInputLayout>(R.id.textInputLayout2).editText?.text.toString()

        // Check is email and password empty
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            lifecycleScope.launch {

                // Update data when user is logged in
                userManager.updateIsLoggedIn(true)

                // Routing to home screen
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }
        } else {
            Toast.makeText(this, "Isi Email dan Password-nya", Toast.LENGTH_SHORT).show()
        }
    }


}