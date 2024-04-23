package com.example.catprice.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.catprice.R
import kotlinx.android.synthetic.main.activity_settings.spinner
import kotlinx.android.synthetic.main.activity_settings.spinner1
import kotlinx.android.synthetic.main.activity_settings.toolbar_setting

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val option = arrayListOf("USD","EGP")

        val arrayAdapter = ArrayAdapter(
            this@SettingsActivity ,
            android.R.layout.simple_spinner_dropdown_item ,
            option
        )

        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCurrency = parent?.getItemAtPosition(position).toString()

                Log.e("selectedCurrency is " , selectedCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        val option1 = arrayListOf("0","1","2","3")

        val arrayAdapter1 = ArrayAdapter(
            this@SettingsActivity ,
            android.R.layout.simple_spinner_dropdown_item ,
            option1
        )

        spinner1.adapter = arrayAdapter1

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCurrency = parent?.getItemAtPosition(position).toString()

                Log.e("selectedCurrency is " , selectedCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        setUpActionBar()
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_setting)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.title = ""
        }

        toolbar_setting.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}