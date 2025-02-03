package com.medvedev.jobsearch.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.FragmentFavoriteVacanciesBinding
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.presentation.adapter.VacancyAdapter
import com.medvedev.jobsearch.presentation.viewmodel.FavoriteVacanciesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteVacanciesFragment : Fragment(R.layout.fragment_favorite_vacancies) {

    private val binding by viewBinding(FragmentFavoriteVacanciesBinding::bind)

    private val vm by viewModel<FavoriteVacanciesViewModel>()

    private val vacancyAdapter by lazy {
        VacancyAdapter(
            onVacancyItemClickListener(),
            onVacancyIconClickListener()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        bindViewModel()
    }

    private fun onVacancyItemClickListener(): () -> Unit = {
        launchFragment(VacancyPageFragment.getInstance())
    }

    private fun onVacancyIconClickListener(): (Vacancy) -> Unit = { vacancy ->
        vm.onVacancyIconPressed(vacancy)
    }

    private fun setAdapter() {
        binding.rvFavoriteVacancies.adapter = vacancyAdapter
    }

    private fun bindViewModel() {
        vm.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.submitList(vacancies)
        }
        vm.error.observe(viewLifecycleOwner) {
            showToast(
                getString(R.string.error_loading)
            )
        }
    }

    private fun launchFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getInstance(): FavoriteVacanciesFragment =
            FavoriteVacanciesFragment()
    }
}