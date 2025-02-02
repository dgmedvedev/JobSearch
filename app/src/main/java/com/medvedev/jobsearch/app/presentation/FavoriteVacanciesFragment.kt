package com.medvedev.jobsearch.app.presentation

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.FragmentFavoriteVacanciesBinding

class FavoriteVacanciesFragment : Fragment(R.layout.fragment_favorite_vacancies) {

    private val binding by viewBinding(FragmentFavoriteVacanciesBinding::bind)

    companion object {
        fun getInstance(): FavoriteVacanciesFragment =
            FavoriteVacanciesFragment()
    }
}