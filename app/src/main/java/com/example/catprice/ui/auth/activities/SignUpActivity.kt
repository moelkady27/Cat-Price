package com.example.catprice.ui.auth.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.R
import com.example.catprice.ntwork.NetworkUtils
import com.example.catprice.ui.auth.viewModel.SignUpViewModel
import kotlinx.android.synthetic.main.activity_sign_up.btn_select_company
import kotlinx.android.synthetic.main.activity_sign_up.btn_select_individual
import kotlinx.android.synthetic.main.activity_sign_up.btn_sign_up
import kotlinx.android.synthetic.main.activity_sign_up.et_company_name
import kotlinx.android.synthetic.main.activity_sign_up.et_country
import kotlinx.android.synthetic.main.activity_sign_up.et_email
import kotlinx.android.synthetic.main.activity_sign_up.et_full_name
import kotlinx.android.synthetic.main.activity_sign_up.et_password
import kotlinx.android.synthetic.main.activity_sign_up.et_phone_number
import kotlinx.android.synthetic.main.activity_sign_up.textView17
import kotlinx.android.synthetic.main.activity_sign_up.tv_sign_in_now
import org.json.JSONException
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {

    private lateinit var networkUtils: NetworkUtils
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        networkUtils = NetworkUtils(this@SignUpActivity)

        signUpViewModel = ViewModelProvider(this@SignUpActivity)[SignUpViewModel::class.java]

        signUpViewModel.signUpResponseLiveData.observe(this@SignUpActivity) { response ->
            response.let {
                val message = response.success.toString()

                Log.e("message sign up", message)
            }
        }

        signUpViewModel.errorLiveData.observe(this@SignUpActivity) { error ->
            error.let {
                try {
                    val errorMessage = JSONObject(error).getString("error")
                    Log.e("Error Message Sign Up", errorMessage)
                } catch (e: JSONException) {
                    Toast.makeText(this@SignUpActivity, error, Toast.LENGTH_LONG).show()
                }
            }
        }

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

        btn_sign_up.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val fullName = et_full_name.text.toString().trim()
        val phone = et_phone_number.text.toString().trim()
        val email = et_email.text.toString().trim()
        val password = et_password.text.toString().trim()
        val country = et_country.text.toString().trim()

        val type = when {
            btn_select_company.isChecked -> "company"
            btn_select_individual.isChecked -> "individual"
            else -> {
                Toast.makeText(this@SignUpActivity, "Please select a type", Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (isValidInput()){
            signUpViewModel.register(fullName, email, password, country,  phone, type)
        }

    }

    private fun isValidInput(): Boolean {
        val fullName = et_full_name.text.toString().trim()
        val phone = et_phone_number.text.toString().trim()
        val email = et_email.text.toString().trim()
        val password = et_password.text.toString().trim()
        val country = et_country.text.toString().trim()

//        val type = when {
//            btn_select_company.isChecked -> "company"
//            btn_select_individual.isChecked -> "individual"
//            else -> {
//                Toast.makeText(this@SignUpActivity, "Please select a type", Toast.LENGTH_SHORT).show()
//                return false
//            }
//        }

        if (fullName.isEmpty()) {
            et_full_name.error = "Full name is not allowed to be empty."
            return false
        } else if (!isUserNameValid(fullName)) {
            et_full_name.error = "Full name must be at least 6 characters long."
            return false
        }

        if (country.isEmpty()) {
            et_country.error = "Country is not allowed to be empty."
            return false
        }

        if (phone.isEmpty()) {
            et_phone_number.error = "phone is not allowed to be empty."
            return false
        } else if (!isPhoneNumberValid(phone)) {
            et_phone_number.error = "Invalid phone number. Please enter a valid 11-digit number."
            return false
        }

        if (email.isEmpty()) {
            et_email.error = "Email is not allowed to be empty."
            return false
        } else if (!isValidEmail(email)) {
            et_email.error = "Please enter a valid email address."
            return false
        }

        if (password.isEmpty()) {
            et_password.error = "Password cannot be empty."
            return false
        } else if (!isPasswordValid(password)) {
            et_password.error = "Password must be at least 8 characters long."
            return false
        }

//        if (type == null) {
//            Toast.makeText(this@SignUpActivity, "Please select a type", Toast.LENGTH_SHORT).show()
//            return false
//        }

        return true
    }

    private fun isUserNameValid(userName: CharSequence): Boolean {
        return userName.length >= 6
    }

    private fun isValidEmail(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
    }

    private fun isPasswordValid(password: CharSequence): Boolean {
        return password.length >= 8
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.length == 11 && phoneNumber.all {
            it.isDigit()
        }
    }
}