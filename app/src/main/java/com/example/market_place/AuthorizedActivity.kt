package com.example.market_place

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log

class AuthorizedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorized)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        if (message != null) {
            Log.d("xxx", message)
        }
    }
}