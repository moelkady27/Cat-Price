package com.example.catprice.ui.auth.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.catprice.R
import kotlinx.android.synthetic.main.activity_sign_in.textView10
import kotlinx.android.synthetic.main.activity_sign_in.tv_sign_up_now

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        tv_sign_up_now.setOnClickListener {
            startActivity(Intent(this@SignInActivity , SignUpActivity::class.java))
        }

        textView10.setOnClickListener {
            startActivity(Intent(this@SignInActivity , ForgotPasswordActivity::class.java))
        }
    }
}