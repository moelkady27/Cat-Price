package com.example.catprice.ui.setting.activities

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.R
import com.example.catprice.ntwork.NetworkUtils
import com.example.catprice.retrofit.RetrofitClient
import com.example.catprice.storage.AppReferences
import com.example.catprice.ui.setting.factory.ContactUsViewModelFactory
import com.example.catprice.ui.setting.repository.ContactUsRepository
import com.example.catprice.ui.setting.viewModels.ContactUsViewModel
import kotlinx.android.synthetic.main.activity_contact_us.button_send_to_admin
import kotlinx.android.synthetic.main.activity_contact_us.toolbar_contact_us
import org.json.JSONObject

class ContactUsActivity : AppCompatActivity() {

    private lateinit var networkUtils: NetworkUtils

    private lateinit var contactUsViewModel: ContactUsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        networkUtils = NetworkUtils(this@ContactUsActivity)

        initView()
        setUpActionBar()
    }

    private fun initView() {
        val contactUsRepository = ContactUsRepository(RetrofitClient.instance)
        val factory = ContactUsViewModelFactory(contactUsRepository)
        contactUsViewModel = ViewModelProvider(this@ContactUsActivity, factory)[ContactUsViewModel::class.java]

        button_send_to_admin.setOnClickListener {
            showContactDialog()
        }
    }

    private fun showContactDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_contact_us, null)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        val imageClose = view.findViewById<ImageView>(R.id.image_view_close)
        imageClose.setOnClickListener {
            dialog.dismiss()
        }

        val buttonSend = view.findViewById<AppCompatButton>(R.id.button)
        buttonSend.setOnClickListener {
            if (networkUtils.isNetworkAvailable()) {

                val editText = view.findViewById<EditText>(R.id.et_add_review_detailed)
                if (editText != null) {
                    sendReview(editText.text.toString(), dialog)
                } else {
                    Toast.makeText(this@ContactUsActivity, "Input field not found.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun sendReview(message: String, dialog: androidx.appcompat.app.AlertDialog) {
        val token = AppReferences.getToken(this@ContactUsActivity)
        val userId = AppReferences.getUserId(this@ContactUsActivity)

        contactUsViewModel.contactUs(token, userId, message)

        contactUsViewModel.contactUsLiveData.observe(this@ContactUsActivity) { response ->
            response?.let {
                val messageResponse = it.message
                Log.e("Contact Us", messageResponse)

                Toast.makeText(this@ContactUsActivity, messageResponse, Toast.LENGTH_LONG).show()

                dialog.dismiss()
            }
        }

        contactUsViewModel.errorLiveData.observe(this@ContactUsActivity) { error ->
            error?.let {
                try {
                    val errorMessage = JSONObject(error).getString("message")
                    Toast.makeText(this@ContactUsActivity, errorMessage, Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(this@ContactUsActivity, "Server error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_contact_us)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.title = ""
        }

        toolbar_contact_us.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}