package com.example.antique_pavbhaji

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class new_orders_notifications : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_orders_notifications)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "New Order Notification"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}