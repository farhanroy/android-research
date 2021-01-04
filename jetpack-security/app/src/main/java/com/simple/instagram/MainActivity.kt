package com.simple.instagram

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        btn_shared.setOnClickListener {
            startActivity(Intent(this, SharedPrefActivity::class.java))
        }

        btn_files.setOnClickListener {
            startActivity(Intent(this, FileActivity::class.java))
        }
    }
}