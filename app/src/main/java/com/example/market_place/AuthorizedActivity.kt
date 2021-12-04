package com.example.market_place

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.view.forEach
import androidx.navigation.findNavController
import com.example.market_place.viewmodels.LoginViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class AuthorizedActivity : AppCompatActivity() {
    private lateinit var navigationView: BottomNavigationView
    lateinit var topAppBar: MaterialToolbar
    lateinit var profileMennu:ActionMenuItemView
    private val loginViewModel:LoginViewModel by viewModels()
    lateinit var  sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorized_activity)

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        if (message != null) {
            Log.d("xxx", message)
        }
        val navController = findNavController(R.id.authorized_nav_host_fragment)
        sharedPreferences = getSharedPreferences("MarketSharedPreferences",
            MODE_PRIVATE)
        initializeView()
        initMenu()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                Toast.makeText(this,"You pushed search button!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.filter -> {
                Toast.makeText(this,"You pushed filter button!", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.profile ->{
                findNavController(R.id.authorized_nav_host_fragment).navigate(R.id.profileFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeView() {
        navigationView = findViewById(R.id.bottomNavigationView)
        topAppBar = findViewById(R.id.main_top_app_bar)
        profileMennu = findViewById(R.id.profile)
    }

    private fun initMenu() {
        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.marketPlaceFragment -> {
                    findNavController(R.id.authorized_nav_host_fragment).navigate(R.id.marketPlaceFragment)
                }
                R.id.myMarketFragment -> {
                    findNavController(R.id.authorized_nav_host_fragment).navigate(R.id.myMarketFragment)
                }
                R.id.myFaresFragment -> {
                    findNavController(R.id.authorized_nav_host_fragment).navigate(R.id.myFaresFragment)
                }
            }
            true
        }
        profileMennu.setOnClickListener {
            findNavController(R.id.authorized_nav_host_fragment).navigate(R.id.profileFragment)
        }




    }

    override fun onResume() {
        super.onResume()
        Log.d("asd", "token: ${sharedPreferences.getString("token","defaultName")}")
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.authorized_nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    override fun onPause() {
        super.onPause()

        val authEditor: SharedPreferences.Editor  = sharedPreferences.edit()
        authEditor.apply()
        authEditor.commit()

    }
}