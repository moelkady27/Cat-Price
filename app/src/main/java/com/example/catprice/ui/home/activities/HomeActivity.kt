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
import com.example.catprice.storage.AppReferences
import com.example.catprice.ui.auth.activities.SignInActivity
import com.example.catprice.ui.auth.viewModel.LogOutViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.toolbar_forget_password
import kotlinx.android.synthetic.main.activity_home.drawerLayout
import kotlinx.android.synthetic.main.activity_home.navView
import kotlinx.android.synthetic.main.activity_home.toolbar
import kotlinx.android.synthetic.main.nav_header.imageViewCloseDrawer
import org.json.JSONException
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var logOutViewModel: LogOutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)


        logOutViewModel = ViewModelProvider(this@HomeActivity)[LogOutViewModel::class.java]

        logOutViewModel.logOutResponseLiveData.observe(this@HomeActivity) { response ->
            response.let {
                val message = it.message

                Log.e("Log out", message)

                AppReferences.setLoginState(this@HomeActivity, false)

                startActivity(Intent(this@HomeActivity, SignInActivity::class.java))
            }
        }

        logOutViewModel.errorLiveData.observe(this@HomeActivity) { error->
            error.let {
                try {
                    val errorMessage = JSONObject(error).getString("message")
                    Toast.makeText(this@HomeActivity, errorMessage, Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    Toast.makeText(this@HomeActivity, error, Toast.LENGTH_LONG).show()
                }
            }
        }

        setUpActionBar()

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white) // Set drawer icon color to white


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
                    }
                }

            }
            true
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