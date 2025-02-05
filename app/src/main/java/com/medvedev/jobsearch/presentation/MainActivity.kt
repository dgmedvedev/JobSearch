package com.medvedev.jobsearch.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main), OnEditingFavoriteVacanciesListener {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListener()
    }

    override fun onEditingFavoriteVacancies(count: Int) {
        if (count > 0) {
            binding.bottomNavigation.showBubble(count = count)
        } else {
            binding.bottomNavigation.hideBubble()
        }
    }

    private fun setListener() {
        binding.bottomNavigation.setOnNavItemClickListener { index ->
            when (index) {
                0 -> launchFragment(MainFragment.getInstance())
                1 -> launchFragment(FavoriteVacanciesFragment.getInstance())
                2 -> launchFragment(ResponsesFragment.getInstance())
                3 -> launchFragment(MessagesFragment.getInstance())
                4 -> launchFragment(ProfileFragment.getInstance())
                else -> return@setOnNavItemClickListener
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .commit()
    }
}