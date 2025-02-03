package com.medvedev.jobsearch.presentation

import androidx.fragment.app.Fragment
import com.medvedev.jobsearch.R

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        fun getInstance(): ProfileFragment =
            ProfileFragment()
    }
}