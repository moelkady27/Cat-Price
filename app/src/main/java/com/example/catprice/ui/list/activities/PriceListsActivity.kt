package com.example.catprice.ui.list.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.catprice.R
import kotlinx.android.synthetic.main.activity_price_lists.card_add_item
import kotlinx.android.synthetic.main.activity_price_lists.card_myLists
import kotlinx.android.synthetic.main.activity_price_lists.textViewAddItem
import kotlinx.android.synthetic.main.activity_price_lists.textViewMyLists
import kotlinx.android.synthetic.main.activity_price_lists.toolbar_price_lists

class PriceListsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_lists)

        initView()
        setUpActionBar()
    }

    private fun initView() {
        textViewAddItem.setOnClickListener {
            openDialog()
        }

        card_add_item.setOnClickListener {
            openDialog()
        }

        card_myLists.setOnClickListener {
            startActivity(Intent(this, MyListActivity::class.java))
        }

        textViewMyLists.setOnClickListener {
            startActivity(Intent(this, MyListActivity::class.java))
        }
    }

    private fun openDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_add_item, null)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        val imageClose = view.findViewById<ImageView>(R.id.image_view_close_item)
        imageClose.setOnClickListener {
            dialog.dismiss()
        }

    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_price_lists)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.title = ""
        }

        toolbar_price_lists.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}