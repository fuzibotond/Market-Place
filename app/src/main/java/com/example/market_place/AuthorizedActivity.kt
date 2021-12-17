package com.example.market_place

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import com.example.market_place.viewmodels.LoginViewModel
import com.example.market_place.viewmodels.SharedViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class AuthorizedActivity : AppCompatActivity() {
    private lateinit var navigationView: BottomNavigationView
    lateinit var topAppBar: MaterialToolbar
    lateinit var profileMennu:ActionMenuItemView
    lateinit var  sharedPreferences: SharedPreferences
    val sharedViewModel:SharedViewModel by viewModels()

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AUTH_ACTIV","OnDestroy"  )
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorized_activity)
        Log.d("AUTH_ACTIV","onCreate"  )

        initializeView()
        initMenu()
    }

    override fun onStart() {
        super.onStart()
        Log.d("AUTH_ACTIV","onStart"  )
    }
    private fun initializeView() {
        navigationView = findViewById(R.id.bottomNavigationView)
        topAppBar = findViewById(R.id.main_top_app_bar)
        profileMennu = findViewById(R.id.profile)
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu,menu)
        val searchItem = menu?.findItem(R.id.search_action)
        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.queryHint = "Type for searching..."
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        sharedViewModel.searchWithKeyword(query)
                    }
                    //show submitted text for testing purposes.
                    Toast.makeText(applicationContext, "Looking for $query", Toast.LENGTH_SHORT).show()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        sharedViewModel.searchWithKeyword(newText)
                    }
                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

//

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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.authorized_nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}