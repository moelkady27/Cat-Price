package com.example.catprice.ui.setting.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.catprice.R
import kotlinx.android.synthetic.main.activity_change_password.toolbar_change_pass

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)



        setUpActionBar()
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_change_pass)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.title = ""
        }

        toolbar_change_pass.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}