package com.example.catprice.ui.setting.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.R
import com.example.catprice.ntwork.NetworkUtils
import com.example.catprice.retrofit.RetrofitClient
import com.example.catprice.storage.AppReferences
import com.example.catprice.ui.setting.factory.ProfileViewModelFactory
import com.example.catprice.ui.setting.repository.ProfileRepository
import com.example.catprice.ui.setting.viewModels.ProfileViewModel
import kotlinx.android.synthetic.main.activity_edit_profile.btn_select_company_edit_profile
import kotlinx.android.synthetic.main.activity_edit_profile.btn_select_individual_edit_profile
import kotlinx.android.synthetic.main.activity_edit_profile.button_edit_profile
import kotlinx.android.synthetic.main.activity_edit_profile.edt_email
import kotlinx.android.synthetic.main.activity_edit_profile.edt_full_name
import kotlinx.android.synthetic.main.activity_edit_profile.edt_phone
import kotlinx.android.synthetic.main.activity_edit_profile.toolbar_edit_profile
import org.json.JSONObject

class EditProfileActivity : AppCompatActivity() {

    private lateinit var networkUtils: NetworkUtils

    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        networkUtils = NetworkUtils(this@EditProfileActivity)

        initView()
        setUpActionBar()
    }

    private fun initView() {
        val getProfileRepository = ProfileRepository(RetrofitClient.instance)
        val factory = ProfileViewModelFactory(getProfileRepository)
        profileViewModel = ViewModelProvider(this@EditProfileActivity, factory)[ProfileViewModel::class.java]

//        get profile data
        val token = AppReferences.getToken(this@EditProfileActivity)
        val userId = AppReferences.getUserId(this@EditProfileActivity)
        if (networkUtils.isNetworkAvailable()){
            profileViewModel.getUser(token, userId)

            profileViewModel.userViewModelLiveData.observe(this@EditProfileActivity) { response ->
                response.let {
                    val message = it.code
                    val userData = it.userData
                    if (userData != null) {
                        val name = userData.name
                        val email = userData.email
                        val phone = userData.phoneNumber
                        val type = userData.type

                        edt_full_name.setText(name ?: "")
                        edt_email.setText(email ?: "")
                        edt_phone.setText(phone ?: "")

                        if (type == "individual") {
                            btn_select_individual_edit_profile.isChecked = true
                        } else {
                            btn_select_company_edit_profile.isChecked = true
                        }

                        Log.e("get user", message.toString())

                    }
                }
            }

            profileViewModel.errorLiveData.observe(this@EditProfileActivity) { error ->
                error.let {
                    try {
                        val errorMessage = JSONObject(error).getString("message")
                        Toast.makeText(this@EditProfileActivity, errorMessage, Toast.LENGTH_LONG)
                            .show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Error Server",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }

//        update profile data

        button_edit_profile.setOnClickListener {
            if (networkUtils.isNetworkAvailable()){
                updateProfile()
            }
        }
    }

    private fun updateProfile() {
        val token = AppReferences.getToken(this@EditProfileActivity)
        val userId = AppReferences.getUserId(this@EditProfileActivity)
        val name = edt_full_name.text.toString()
        val email = edt_email.text.toString()
        val phone = edt_phone.text.toString()
        val type = if (btn_select_individual_edit_profile.isChecked) {
            "individual"
        } else {
            "company"
        }

        profileViewModel.updateUser(token, userId, name, phone, email, type)

        profileViewModel.userViewModelLiveData.observe(this@EditProfileActivity) { response ->
            response.let {
                val message = it.code
                Log.e("update user " , message.toString())

                onBackPressed()

            }
        }

        profileViewModel.errorLiveData.observe(this@EditProfileActivity) { error ->
            error.let {
                try {
                    val errorMessage = JSONObject(error).getString("message")
                    Toast.makeText(this@EditProfileActivity, errorMessage, Toast.LENGTH_LONG)
                        .show()
                } catch (e: Exception) {
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Error Server",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_edit_profile)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.title = ""
        }

        toolbar_edit_profile.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}