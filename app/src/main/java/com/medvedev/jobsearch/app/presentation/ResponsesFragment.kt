package com.medvedev.jobsearch.app.presentation

import androidx.fragment.app.Fragment
import com.medvedev.jobsearch.R

class ResponsesFragment : Fragment(R.layout.fragment_responses) {

    companion object {
        fun getInstance(): ResponsesFragment =
            ResponsesFragment()
    }
}