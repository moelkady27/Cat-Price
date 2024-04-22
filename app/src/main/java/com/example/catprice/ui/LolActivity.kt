package com.example.catprice.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.R
import com.example.catprice.storage.AppReferences
import com.example.catprice.ui.auth.activities.SignInActivity
import com.example.catprice.ui.auth.viewModel.LolViewModel
import kotlinx.android.synthetic.main.activity_lol.button
import org.json.JSONException
import org.json.JSONObject

class LolActivity : AppCompatActivity() {

    private lateinit var lolViewModel: LolViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lol)

        lolViewModel = ViewModelProvider(this@LolActivity)[LolViewModel::class.java]

        lolViewModel.logOutResponseLiveData.observe(this@LolActivity) { response ->
            response.let {
                val message = it.message

                Log.e("Log out", message)

                AppReferences.setLoginState(this@LolActivity, false)

                startActivity(Intent(this@LolActivity, SignInActivity::class.java))
            }
        }

        lolViewModel.errorLiveData.observe(this@LolActivity) { error->
            error.let {
                try {
                    val errorMessage = JSONObject(error).getString("message")
                    Toast.makeText(this@LolActivity, errorMessage, Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    Toast.makeText(this@LolActivity, error, Toast.LENGTH_LONG).show()
                }
            }
        }

        button.setOnClickListener {

            val token = AppReferences.getToken(this@LolActivity)
            val userId = AppReferences.getUserId(this@LolActivity)

            if (token.isEmpty() || userId.isEmpty()) {
                Toast.makeText(this, "Token or User ID cannot be empty.", Toast.LENGTH_LONG).show()
            } else {
                lolViewModel.logOut(token, userId)
            }
        }
    }
}