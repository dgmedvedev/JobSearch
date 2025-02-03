package com.medvedev.jobsearch.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.FragmentMainBinding
import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.presentation.adapter.OfferAdapter
import com.medvedev.jobsearch.presentation.adapter.VacancyAdapter
import com.medvedev.jobsearch.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val vm by viewModel<MainViewModel>()

    private val offerAdapter by lazy {
        OfferAdapter(onOfferItemClickListener())
    }

    private val vacancyAdapter by lazy {
        VacancyAdapter(
            onVacancyItemClickListener(),
            onVacancyIconClickListener()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        setListener()
        bindViewModel()
    }

    private fun onOfferItemClickListener(): (Offer) -> Unit = { offer ->
        launchFragment(WebViewFragment.getInstance(offer.link))
    }

    private fun onVacancyItemClickListener(): () -> Unit = {
        launchFragment(VacancyPageFragment.getInstance())
    }

    private fun onVacancyIconClickListener(): (Vacancy) -> Unit = { vacancy ->
        vm.onVacancyIconPressed(vacancy)
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

    private fun setListener() {
        binding.btnAllVacancies.setOnClickListener {
            launchFragment(RelevantVacanciesFragment.getInstance())
        }
    }

    private fun bindViewModel() {
        vm.offers.observe(viewLifecycleOwner) { offers ->
            offerAdapter.submitList(offers)
        }
        vm.vacancies.observe(viewLifecycleOwner) { vacancies ->
            val visibleVacancies = if (vacancies.size > 3) vacancies.take(3) else vacancies
            vacancyAdapter.submitList(visibleVacancies)
            binding.btnAllVacancies.text =
                getString(
                    R.string.text_all_vacancies,
                    getVacancyGenitiveCase(vacancies.size)
                )
        }
        vm.error.observe(viewLifecycleOwner) {
            showToast(getString(R.string.error_loading))
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

        fun getInstance(): MainFragment =
            MainFragment()
    }
}