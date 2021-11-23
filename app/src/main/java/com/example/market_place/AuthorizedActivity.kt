package com.example.market_place

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import androidx.navigation.findNavController

class AuthorizedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorized_activity)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        if (message != null) {
            Log.d("xxx", message)
        }
        val navController = findNavController(R.id.authorized_nav_host_fragment)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.authorized_nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}