package com.medvedev.jobsearch.app.presentation

import androidx.fragment.app.Fragment
import com.medvedev.jobsearch.R

class MessagesFragment : Fragment(R.layout.fragment_messages) {

    companion object {
        fun getInstance(): MessagesFragment =
            MessagesFragment()
    }
}