package com.simple.instagram

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.android.synthetic.main.activity_shared_pref.*

class SharedPrefActivity : AppCompatActivity() {

    private lateinit var securedSharedPrefs: SharedPreferences
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_pref)

        /**
         * init onclick or another view listener
         */
        initViews()

        /**
         * recommended way for general purposes
         */
        val masterKey = MasterKey.Builder(this,MasterKey.DEFAULT_MASTER_KEY_ALIAS).
        setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        // Init Shared Preferences
        sharedPrefs = getSharedPreferences("values", Context.MODE_PRIVATE)
        securedSharedPrefs = EncryptedSharedPreferences.create(
            this, // context
            "values_secured",   //xml file name
            masterKey,   //master key
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,  // key encryption technique
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // value encryption technique
        )

        setUi()
    }

    /**
     * Update User Interface when value submitted
     */
    private fun setUi() {

        tv_normal_value.text = sharedPrefs.getString(savedValueKey, "")

        tv_encrypted_value.text = securedSharedPrefs.getString(savedValueKey, "")
    }

    private fun initViews() {
        btn_save.setOnClickListener {
            if (isSavingValid()) {
                sharedPrefs.edit().putString(savedValueKey, ed_data.text.toString()).apply()
                setUi()
            }
        }

        btn_save_encrypted.setOnClickListener {
            if (isSavingValid()) {
                securedSharedPrefs.edit().putString(savedValueKey, ed_data.text.toString()).apply()
                setUi()
            }
        }
    }

    /**
     * validating is edit text empty or not
     * if edit text empty it will show toast
     */
    private fun isSavingValid(): Boolean {
        return if (ed_data.text.toString().isNotEmpty()) true
        else {
            Toast.makeText(this, getString(R.string.enter_any_value), Toast.LENGTH_LONG).show()
            false
        }
    }
    companion object {
        // Key for saved value in Shared Preferences
        const val savedValueKey = "saved_value"
    }
}