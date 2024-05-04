package com.example.catprice.ui.home.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.R
import com.example.catprice.ntwork.NetworkUtils
import com.example.catprice.retrofit.RetrofitClient
import com.example.catprice.storage.AppReferences
import com.example.catprice.ui.auth.activities.SignInActivity
import com.example.catprice.ui.auth.factory.LogOutViewModelFactory
import com.example.catprice.ui.auth.repository.LogOutRepository
import com.example.catprice.ui.auth.viewModel.LogOutViewModel
import com.example.catprice.ui.setting.activities.SettingsActivity
import com.example.catprice.ui.setting.factory.ProfileViewModelFactory
import com.example.catprice.ui.setting.repository.ProfileRepository
import com.example.catprice.ui.setting.viewModels.ProfileViewModel
import kotlinx.android.synthetic.main.activity_edit_profile.btn_select_company_edit_profile
import kotlinx.android.synthetic.main.activity_edit_profile.btn_select_individual_edit_profile
import kotlinx.android.synthetic.main.activity_edit_profile.edt_email
import kotlinx.android.synthetic.main.activity_edit_profile.edt_full_name
import kotlinx.android.synthetic.main.activity_edit_profile.edt_phone
import kotlinx.android.synthetic.main.activity_home.drawerLayout
import kotlinx.android.synthetic.main.activity_home.navView
import kotlinx.android.synthetic.main.activity_home.toolbar
import kotlinx.android.synthetic.main.nav_header.imageViewCloseDrawer
import kotlinx.android.synthetic.main.nav_header.iv_user_full_name
import kotlinx.android.synthetic.main.nav_header.textView23
import org.json.JSONException
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var networkUtils: NetworkUtils

    private lateinit var logOutViewModel: LogOutViewModel
    private lateinit var profileViewModel: ProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        networkUtils = NetworkUtils(this@HomeActivity)

        setUpActionBar()
        initView()

    }

    private fun initView() {

        val logOutRepository = LogOutRepository(RetrofitClient.instance)
        val factoryLogOut = LogOutViewModelFactory(logOutRepository)
        logOutViewModel = ViewModelProvider(this@HomeActivity, factoryLogOut)[LogOutViewModel::class.java]

        val getProfileRepository = ProfileRepository(RetrofitClient.instance)
        val factoryProfile = ProfileViewModelFactory(getProfileRepository)
        profileViewModel = ViewModelProvider(this@HomeActivity, factoryProfile)[ProfileViewModel::class.java]

        if (networkUtils.isNetworkAvailable()){
            getProfileData()
        }

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imageViewCloseDrawer.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.searchItem -> {

                }

                R.id.favouriteItem -> {

                }

                R.id.calculatorItem -> {

                }

                R.id.MyListsItem -> {

                }

                R.id.brandsItem -> {

                }

                R.id.inboxItem -> {

                }

                R.id.contactUsItem -> {

                }

                R.id.settingsItem -> {
                    startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
                }

                R.id.shareAppItem -> {

                }

                R.id.subscriptionsItem -> {

                }

                R.id.logoutItem -> {
                    val token = AppReferences.getToken(this@HomeActivity)
                    val userId = AppReferences.getUserId(this@HomeActivity)

                    if (token.isEmpty() || userId.isEmpty()) {
                        Toast.makeText(this, "Token or User ID cannot be empty.", Toast.LENGTH_LONG).show()
                    } else {
                        logOutViewModel.logOut(token, userId)

                        logOutViewModel.logOutResponseLiveData.observe(this@HomeActivity) { response ->
                            response.let {
                                val message = it.message

                                Log.e("Log out", message)

                                AppReferences.setLoginState(this@HomeActivity, false)

                                startActivity(Intent(this@HomeActivity, SignInActivity::class.java))
                            }
                        }

                        logOutViewModel.errorLiveData.observe(this@HomeActivity) { error ->
                            error.let {
                                try {
                                    val errorMessage = JSONObject(error).getString("message")
                                    Toast.makeText(this@HomeActivity, errorMessage, Toast.LENGTH_LONG).show()
                                } catch (e: JSONException) {
                                    Toast.makeText(this@HomeActivity, error, Toast.LENGTH_LONG).show()
                                }
                            }
                        }

                    }
                }

            }
            true
        }
    }

    private fun getProfileData() {
        val token = AppReferences.getToken(this@HomeActivity)
        val userId = AppReferences.getUserId(this@HomeActivity)
        if (networkUtils.isNetworkAvailable()) {
            profileViewModel.getUser(token, userId)

            profileViewModel.userViewModelLiveData.observe(this@HomeActivity) { response ->
                response.let {
                    val message = it.code
                    val userData = it.userData
                    if (userData != null) {
                        val name = userData.name
                        val email = userData.email

                        iv_user_full_name.text = name ?: ""
                        textView23.text = email ?: ""

                        Log.e("get user", message.toString())

                    }
                }
            }

            profileViewModel.errorLiveData.observe(this@HomeActivity) { error ->
                error.let {
                    try {
                        val errorMessage = JSONObject(error).getString("message")
                        Toast.makeText(this@HomeActivity, errorMessage, Toast.LENGTH_LONG)
                            .show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@HomeActivity,
                            "Error Server",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.title = ""
        }

    }
}