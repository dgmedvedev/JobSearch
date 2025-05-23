package com.medvedev.jobsearch.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.FragmentRelevantVacanciesBinding
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.presentation.adapter.VacancyAdapter
import com.medvedev.jobsearch.presentation.viewmodel.RelevantVacanciesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RelevantVacanciesFragment : Fragment(R.layout.fragment_relevant_vacancies) {

    private val binding by viewBinding(FragmentRelevantVacanciesBinding::bind)

    private val vm by viewModel<RelevantVacanciesViewModel>()

    private val vacancyAdapter by lazy {
        VacancyAdapter(
            onVacancyItemClickListener(),
            onVacancyIconClickListener()
        )
    }

    private lateinit var onEditingFavoriteVacanciesListener: OnEditingFavoriteVacanciesListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onEditingFavoriteVacanciesListener = context as OnEditingFavoriteVacanciesListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setListener()
        bindViewModel()
    }

    private fun onVacancyItemClickListener(): () -> Unit = {
        launchFragment(VacancyPageFragment.getInstance())
    }

    private fun onVacancyIconClickListener(): (Vacancy) -> Unit = { vacancy ->
        vm.onVacancyIconPressed(vacancy)
    }

    private fun setAdapter() {
        binding.rvVacancies.adapter = vacancyAdapter
    }

    private fun setListener() {
        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun bindViewModel() {
        vm.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.submitList(vacancies)
            binding.tvNumberOfVacancies.text =
                getString(
                    R.string.number_of_vacancies,
                    getVacancyGenitiveCase(vacancies.size)
                )
        }
        vm.vacanciesFavorite.observe(viewLifecycleOwner) { vacanciesFavorite ->
            onEditingFavoriteVacanciesListener.onEditingFavoriteVacancies(vacanciesFavorite.size)
        }
        vm.error.observe(viewLifecycleOwner) {
            showToast(
                getString(R.string.error_loading)
            )
        }
    }

    private fun getVacancyGenitiveCase(numberOfVacancies: Int): String {
        return when {
            numberOfVacancies in 11..14 -> "$numberOfVacancies $VACANCY_FORM_MANY"
            numberOfVacancies % 10 == 1 -> "$numberOfVacancies $VACANCY_FORM_SINGULAR"
            numberOfVacancies % 10 in 2..4 -> "$numberOfVacancies $VACANCY_FORM_FEW"
            else -> "$numberOfVacancies $VACANCY_FORM_MANY"
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
        private const val VACANCY_FORM_SINGULAR = "вакансия"
        private const val VACANCY_FORM_FEW = "вакансии"
        private const val VACANCY_FORM_MANY = "вакансий"

        fun getInstance(): RelevantVacanciesFragment =
            RelevantVacanciesFragment()
    }
}