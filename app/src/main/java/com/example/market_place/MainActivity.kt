package com.example.market_place

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.market_place.viewmodels.LoginViewModel
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)


    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        val sh:SharedPreferences = getSharedPreferences("MarketSharedPreferences", MODE_PRIVATE)
//        MarketPlaceApplication.token = sh.getString("token","").toString()
        val sharedPreferences:SharedPreferences = getSharedPreferences("MarketSharedPreferences",
            MODE_PRIVATE)
        Log.d("asd", "token: ${sharedPreferences.getString("token","")}")
    }

}