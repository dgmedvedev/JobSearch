package com.medvedev.jobsearch.app.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.app.presentation.adapter.OfferAdapter
import com.medvedev.jobsearch.app.presentation.adapter.VacancyAdapter
import com.medvedev.jobsearch.app.presentation.viewmodel.MainViewModel
import com.medvedev.jobsearch.app.presentation.viewmodel.ViewModelFactory
import com.medvedev.jobsearch.databinding.FragmentMainBinding
import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val vm by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(requireContext().applicationContext)
        )[MainViewModel::class.java]
    }

    private val offerAdapter by lazy {
        OfferAdapter(onOfferItemClickListener())
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
        setAdapters()
        bindViewModel()
        vm.loadData()
    }

    private fun onOfferItemClickListener(): (Offer) -> Unit = { offer ->
        Toast.makeText(requireContext(), "${offer.id}", Toast.LENGTH_SHORT).show()
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

    private fun bindViewModel() {
        vm.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainViewModel.State.OffersLoaded -> offerAdapter.submitList(state.offers)
                is MainViewModel.State.VacanciesLoaded -> {
                    val vacancies = state.vacancies
                    val visibleVacancies = if (vacancies.size > 3) vacancies.take(3) else vacancies
                    vacancyAdapter.submitList(visibleVacancies)
                    binding.btnAllVacancies.text =
                        getString(
                            R.string.text_all_vacancies,
                            getVacancyGenitiveCase(vacancies.size)
                        )
                }

                is MainViewModel.State.ErrorLoadingOffers -> showToast(state.error)
                is MainViewModel.State.ErrorLoadingVacancies -> showToast(state.error)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setAdapters() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvOffers.layoutManager = layoutManager
        binding.rvOffers.adapter = offerAdapter

        binding.rvVacancies.adapter = vacancyAdapter
        binding.rvVacancies.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                binding.btnAllVacancies.visibility =
                    if (visibleItemPosition >= 1) View.VISIBLE else View.GONE
            }
        })
    }

    private fun getVacancyGenitiveCase(numberOfVacancies: Int): String {
        return when {
            numberOfVacancies in 11..14 -> "$numberOfVacancies $VACANCY_FORM_MANY"
            numberOfVacancies % 10 == 1 -> "$numberOfVacancies $VACANCY_FORM_SINGULAR"
            numberOfVacancies % 10 in 2..4 -> "$numberOfVacancies $VACANCY_FORM_FEW"
            else -> "$numberOfVacancies $VACANCY_FORM_MANY"
        }
    }

    companion object {
        private const val VACANCY_FORM_SINGULAR = "вакансия"
        private const val VACANCY_FORM_FEW = "вакансии"
        private const val VACANCY_FORM_MANY = "вакансий"
    }
}