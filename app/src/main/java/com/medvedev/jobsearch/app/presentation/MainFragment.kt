package com.medvedev.jobsearch.app.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.data.network.ApiFactory
import com.medvedev.jobsearch.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testNetwork()
    }

    private fun testNetwork() {
        val apiService = ApiFactory.apiService

        val jsonDeferred = lifecycleScope.async {
            withContext(Dispatchers.IO) {
                apiService.getJson()
            }
        }
        lifecycleScope.launch {
            val json = jsonDeferred.await()
            Log.d("TEST_NETWORK", "$json")
        }
    }
}