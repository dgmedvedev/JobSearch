package com.medvedev.jobsearch.app.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.app.presentation.adapter.VacancyAdapter
import com.medvedev.jobsearch.app.presentation.viewmodel.RelevantVacanciesViewModel
import com.medvedev.jobsearch.app.presentation.viewmodel.ViewModelFactory
import com.medvedev.jobsearch.databinding.FragmentRelevantVacanciesBinding
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy

class RelevantVacanciesFragment : Fragment(R.layout.fragment_relevant_vacancies) {

    private val binding by viewBinding(FragmentRelevantVacanciesBinding::bind)

    private val vm by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(requireContext().applicationContext)
        )[RelevantVacanciesViewModel::class.java]
    }

    private val vacancyAdapter by lazy {
        VacancyAdapter(
            onVacancyItemClickListener(),
            onVacancyIconClickListener(),
            onButtonApplyClickListener()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setListeners()
        bindViewModel()
        vm.loadData()
    }

    private fun onVacancyItemClickListener(): (Vacancy) -> Unit = { vacancy ->
        Toast.makeText(requireContext(), "${vacancy.company}", Toast.LENGTH_SHORT).show()
    }

    private fun onVacancyIconClickListener(): (Vacancy) -> Unit = { vacancy ->
        Toast.makeText(requireContext(), "${vacancy.isFavorite}", Toast.LENGTH_SHORT).show()
    }

    private fun onButtonApplyClickListener(): () -> Unit = {
        Toast.makeText(requireContext(), "Button is clicked", Toast.LENGTH_SHORT).show()
    }

    private fun setAdapter() {
        binding.rvVacancies.adapter = vacancyAdapter
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun bindViewModel() {
        vm.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RelevantVacanciesViewModel.State.Loaded -> {
                    vacancyAdapter.submitList(state.vacancies)
                    binding.tvNumberOfVacancies.text =
                        getString(
                            R.string.number_of_vacancies,
                            getVacancyGenitiveCase(state.vacancies.size)
                        )
                }

                is RelevantVacanciesViewModel.State.Error -> showToast(state.error)
            }
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