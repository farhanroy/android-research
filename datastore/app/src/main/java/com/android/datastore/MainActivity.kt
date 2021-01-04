package com.android.datastore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.android.datastore.utils.UserManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/*
 *
 */
class MainActivity : AppCompatActivity() {

    //
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init UserManager class
        userManager = UserManager(this)

        // Lifecycle to make delayed view like as splash
        lifecycleScope.launch {
            delay(2000)
            isUserLogin()
        }
    }

    // read data from jetpack datastore
    private suspend fun isUserLogin() {
        userManager.userPreferencesFlow.collect { userPreferences ->
            when(userPreferences.isLogin) {
                true -> toHomeOrLogin(true)
                false -> toHomeOrLogin(false)
            }
        }
    }

    // if true yo will route to home screen
    private fun toHomeOrLogin(home: Boolean) {
        if (home) startActivity(Intent(this, HomeActivity::class.java))
        else startActivity(Intent(this, LoginActivity::class.java))
    }
}