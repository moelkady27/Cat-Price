package com.example.catprice.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.catprice.R
import com.example.catprice.ui.auth.SignInActivity
import kotlinx.android.synthetic.main.activity_main.getStarted

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getStarted.setOnClickListener {
            startActivity(Intent(this@MainActivity , SignInActivity::class.java))
        }
    }
}