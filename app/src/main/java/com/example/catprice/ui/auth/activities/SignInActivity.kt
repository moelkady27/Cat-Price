package com.example.catprice.ui.auth.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.R
import com.example.catprice.ntwork.NetworkUtils
import com.example.catprice.retrofit.RetrofitClient
import com.example.catprice.storage.AppReferences
import com.example.catprice.ui.auth.factory.SignInViewModelFactory
import com.example.catprice.ui.auth.repository.SignInRepository
import com.example.catprice.ui.auth.viewModel.SignInViewModel
import com.example.catprice.ui.home.activities.HomeActivity
import kotlinx.android.synthetic.main.activity_sign_in.btn_sign_in
import kotlinx.android.synthetic.main.activity_sign_in.et_email_sign_in
import kotlinx.android.synthetic.main.activity_sign_in.et_password_sign_in
import kotlinx.android.synthetic.main.activity_sign_in.textView10
import kotlinx.android.synthetic.main.activity_sign_in.tv_sign_up_now
import org.json.JSONException
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {

    private lateinit var networkUtils: NetworkUtils

    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        networkUtils = NetworkUtils(this@SignInActivity)

        initView()

    }

    private fun initView() {
        val signInRepository = SignInRepository(RetrofitClient.instance)
        val factory = SignInViewModelFactory(signInRepository)
        signInViewModel = ViewModelProvider(this@SignInActivity, factory)[SignInViewModel::class.java]

        tv_sign_up_now.setOnClickListener {
            startActivity(Intent(this@SignInActivity , SignUpActivity::class.java))
        }

        textView10.setOnClickListener {
            startActivity(Intent(this@SignInActivity , ForgotPasswordActivity::class.java))
        }

        btn_sign_in.setOnClickListener {
            if (networkUtils.isNetworkAvailable()){
                signIn()
            }
        }
    }

    private fun signIn() {
        val email = et_email_sign_in.text.toString().trim()
        val password = et_password_sign_in.text.toString().trim()

        if (isValidInput()){
            signInViewModel.signIn(email, password)

            signInViewModel.signInResponseLiveData.observe(this) { response ->
                response.let {

                    val token = it.token
                    val userId = it.userData._id

                    Log.e("SignInActivity token is" , token)

                    AppReferences.setToken(this@SignInActivity , token)

                    AppReferences.setLoginState(this@SignInActivity, true)

                    AppReferences.setUserId(this@SignInActivity , userId)

                    Log.e("Sign In userId is " , userId)

                    startActivity(Intent(this@SignInActivity , HomeActivity::class.java))
                }
            }

            signInViewModel.errorLiveData.observe(this) { error ->
                error?.let {
                    try {
                        val errorMessage = JSONObject(error).getString("message")
                        Toast.makeText(this@SignInActivity, errorMessage, Toast.LENGTH_LONG).show()
                    } catch (e: JSONException) {
                        Toast.makeText(this@SignInActivity, error, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

    private fun isValidInput(): Boolean {
        val email = et_email_sign_in.text.toString().trim()
        val password = et_password_sign_in.text.toString().trim()

        if (email.isEmpty()) {
            et_email_sign_in.error = "Email is not allowed to be empty."
            return false
        }

        if (password.isEmpty()) {
            et_password_sign_in.error = "Password cannot be empty."
            return false
        }
        return true
    }
}