package com.example.catprice.ui.auth.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.catprice.R
import kotlinx.android.synthetic.main.activity_sign_up.btn_select_company
import kotlinx.android.synthetic.main.activity_sign_up.btn_select_individual
import kotlinx.android.synthetic.main.activity_sign_up.et_company_name
import kotlinx.android.synthetic.main.activity_sign_up.textView17
import kotlinx.android.synthetic.main.activity_sign_up.tv_sign_in_now

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        textView17.visibility = View.INVISIBLE
        et_company_name.visibility = View.INVISIBLE

        btn_select_company.setOnClickListener {
            textView17.visibility = View.VISIBLE
            et_company_name.visibility = View.VISIBLE
        }

        btn_select_individual.setOnClickListener {
            textView17.visibility = View.INVISIBLE
            et_company_name.visibility = View.INVISIBLE
        }

        tv_sign_in_now.setOnClickListener {
            startActivity(Intent(this@SignUpActivity , SignInActivity::class.java))
        }
    }
}