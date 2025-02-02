package com.medvedev.jobsearch.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListener()
    }


    private fun setListener() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_search -> {
                    launchFragment(MainFragment.getInstance())
                    true
                }

                R.id.nav_favorite -> {
                    launchFragment(FavoriteVacanciesFragment.getInstance())
                    true
                }

                R.id.nav_responses -> {
                    launchFragment(ResponsesFragment.getInstance())
                    true
                }

                R.id.nav_messages -> {
                    launchFragment(MessagesFragment.getInstance())
                    true
                }

                R.id.nav_profile -> {
                    launchFragment(ProfileFragment.getInstance())
                    true
                }

                else -> false
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .commit()
    }
}