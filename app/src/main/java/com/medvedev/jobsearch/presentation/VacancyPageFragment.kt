package com.medvedev.jobsearch.presentation

import androidx.fragment.app.Fragment
import com.medvedev.jobsearch.R

class VacancyPageFragment : Fragment(R.layout.fragment_vacancy_page) {

    companion object {
        fun getInstance(): VacancyPageFragment =
            VacancyPageFragment()
    }
}