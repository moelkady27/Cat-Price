package com.example.catprice.ui.list.activities

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catprice.R
import com.example.catprice.ntwork.NetworkUtils
import com.example.catprice.retrofit.RetrofitClient
import com.example.catprice.storage.AppReferences
import com.example.catprice.ui.list.adapter.MyListAdapter
import com.example.catprice.ui.list.factory.CreateListViewModelFactory
import com.example.catprice.ui.list.repository.CreateListRepository
import com.example.catprice.ui.list.viewModel.CreateListViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_my_list.imageView3
import kotlinx.android.synthetic.main.activity_my_list.toolbar_my_lists
import okio.IOException
import org.json.JSONException
import org.json.JSONObject

class MyListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var networkUtils: NetworkUtils

    private lateinit var adapter: MyListAdapter

    private lateinit var createListViewModel: CreateListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)

        networkUtils = NetworkUtils(this@MyListActivity)

        recyclerView = findViewById(R.id.recycler_view_my_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this@MyListActivity)
        recyclerView.adapter = MyListAdapter()

        initView()
        setUpActionBar()
    }

    private fun initView() {
        val createListRepository = CreateListRepository(RetrofitClient.instance)
        val factory = CreateListViewModelFactory(createListRepository)
        createListViewModel = ViewModelProvider(
            this@MyListActivity,
            factory
        )[CreateListViewModel::class.java]

        imageView3.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_add_list, null)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        val imageClose = view.findViewById<ImageView>(R.id.image_view_close_add_list)
        imageClose.setOnClickListener {
            dialog.dismiss()
        }

        val buttonSave = view.findViewById<AppCompatButton>(R.id.btn_save_list)
        buttonSave.setOnClickListener {
            val listName = view.findViewById<TextInputEditText>(R.id.et_list_name)
            createList(listName.text.toString().trim())
        }
    }

    private fun createList(name: String) {
        if (networkUtils.isNetworkAvailable()) {
            if (name.isNotEmpty()) {
                val token = AppReferences.getToken(this@MyListActivity)
                val userId = AppReferences.getUserId(this@MyListActivity)
                createListViewModel.createList(token, userId, name)

                createListViewModel.createListLiveData.observe(this@MyListActivity){ response ->
                    response.let {
                        val message = it.success.toString()

                        Toast.makeText(this@MyListActivity, message, Toast.LENGTH_SHORT).show()
                        Log.e("CreateList", message)
                    }
                }

                createListViewModel.errorLiveData.observe(this@MyListActivity){ error ->
                    error?.let {
                        try {
                            val errorMessage = JSONObject(error).getString("message")
                            Toast.makeText(this@MyListActivity, errorMessage, Toast.LENGTH_LONG).show()
                            Log.e("CreateList errorMessage", errorMessage)
                        } catch (e: JSONException) {
                            Toast.makeText(this@MyListActivity, error, Toast.LENGTH_LONG).show()
                            Log.e("CreateList error", error)
                        } catch (e: IOException) {
                            Log.e("CreateList error", "Error reading response body", e)
                        }
                    }
                }

            }
            else {
                Toast.makeText(this@MyListActivity, "List name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this@MyListActivity, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_my_lists)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.title = ""
        }

        toolbar_my_lists.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}