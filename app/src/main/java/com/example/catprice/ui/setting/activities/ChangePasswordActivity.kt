package com.example.catprice.ui.setting.activities

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
import com.example.catprice.ui.home.activities.HomeActivity
import com.example.catprice.ui.setting.factory.ChangePasswordViewModelFactory
import com.example.catprice.ui.setting.repository.ChangePasswordRepository
import com.example.catprice.ui.setting.viewModels.ChangePasswordViewModels
import kotlinx.android.synthetic.main.activity_change_password.button_change_pass
import kotlinx.android.synthetic.main.activity_change_password.et_confirm_new_password
import kotlinx.android.synthetic.main.activity_change_password.et_current_password
import kotlinx.android.synthetic.main.activity_change_password.et_new_password
import kotlinx.android.synthetic.main.activity_change_password.toolbar_change_pass
import org.json.JSONObject

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var networkUtils: NetworkUtils

    private lateinit var changePasswordViewModels: ChangePasswordViewModels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        networkUtils = NetworkUtils(this@ChangePasswordActivity)

        initView()
        setUpActionBar()
    }

    private fun initView() {
        val changePasswordRepository = ChangePasswordRepository(RetrofitClient.instance)
        val factory = ChangePasswordViewModelFactory(changePasswordRepository)
        changePasswordViewModels = ViewModelProvider(this@ChangePasswordActivity, factory)[ChangePasswordViewModels::class.java]

        button_change_pass.setOnClickListener {
            if (networkUtils.isNetworkAvailable()){
                changePass()
            }
        }
    }

    private fun changePass() {
        val token = AppReferences.getToken(this@ChangePasswordActivity)
        val currentPassword = et_current_password.text.toString().trim()
        val newPassword = et_new_password.text.toString().trim()
        val confirmPassword = et_confirm_new_password.text.toString().trim()
        val userId = AppReferences.getUserId(this@ChangePasswordActivity)

        if (isValidInput()) {
            changePasswordViewModels.changePass(token, currentPassword, newPassword, confirmPassword, userId)

            changePasswordViewModels.changePassResponseLiveData.observe(this@ChangePasswordActivity) { response ->
                response.let {
                    val message = it.message

                    Log.e("change password " , message)

                    startActivity(Intent(this@ChangePasswordActivity , HomeActivity::class.java))
                }
            }

            changePasswordViewModels.errorLiveData.observe(this@ChangePasswordActivity) { error ->
                error.let {
                    try {
                        val errorMessage = JSONObject(error).getString("message")
                        Toast.makeText(this@ChangePasswordActivity, errorMessage, Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@ChangePasswordActivity, "Error Server", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun isValidInput(): Boolean {
        val currentPassword = et_current_password.text.toString().trim()
        val newPassword = et_new_password.text.toString().trim()
        val confirmPassword = et_confirm_new_password.text.toString().trim()

        if (currentPassword.isEmpty()){
            et_current_password.error = "Current password is not allowed to be empty."
            return false
        }
        if (newPassword.isEmpty()){
            et_new_password.error = "New password is not allowed to be empty."
            return false
        }

        if (confirmPassword.isEmpty()){
            et_confirm_new_password.error = "Confirm New password is not allowed to be empty."
            return false
        }
        if (newPassword!=confirmPassword){
            et_confirm_new_password.error = "Passwords do not match"
        }
        return true
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