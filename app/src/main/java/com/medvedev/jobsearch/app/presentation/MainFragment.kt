package com.medvedev.jobsearch.app.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.app.presentation.adapter.OfferAdapter
import com.medvedev.jobsearch.app.presentation.adapter.VacancyAdapter
import com.medvedev.jobsearch.data.network.ApiFactory
import com.medvedev.jobsearch.databinding.FragmentMainBinding
import com.medvedev.jobsearch.domain.model.offer.Button
import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Address
import com.medvedev.jobsearch.domain.model.vacancy.Experience
import com.medvedev.jobsearch.domain.model.vacancy.Salary
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val offerAdapter by lazy {
        OfferAdapter(onOfferItemClickListener())
    }

    private val vacancyAdapter by lazy {
        VacancyAdapter(onVacancyItemClickListener())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        testNetwork()
        testOfferAdapter()
        testVacancyAdapter()
    }

    private fun onOfferItemClickListener(): (Offer) -> Unit = { offer ->
        Toast.makeText(requireContext(), "${offer.id}", Toast.LENGTH_SHORT).show()
    }

    private fun onVacancyItemClickListener(): (Vacancy) -> Unit = { vacancy ->
        Toast.makeText(requireContext(), "${vacancy.company}", Toast.LENGTH_SHORT).show()
    }

    private fun setAdapters() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvOffers.layoutManager = layoutManager
        binding.rvOffers.adapter = offerAdapter
        binding.rvVacancies.adapter = vacancyAdapter
    }

    private fun testNetwork() {
        val apiService = ApiFactory.apiService

        val offersDeferred = lifecycleScope.async {
            withContext(Dispatchers.IO) {
                apiService.getOffers()
            }
        }
        val vacanciesDeferred = lifecycleScope.async {
            withContext(Dispatchers.IO) {
                apiService.getVacancies()
            }
        }
        lifecycleScope.launch {
            val offers = offersDeferred.await().offers
            val vacancies = vacanciesDeferred.await().vacancies
            Log.d(TAG, "offers: $offers")
            Log.d(TAG, "vacancies: $vacancies")
        }
    }

    private fun testOfferAdapter() {
        val offers = listOf(
            Offer("near_vacancies", "Вакансии рядом с вами", "https://hh.ru/", null),
            Offer(
                "level_up_resume",
                "Поднять резюме в поиске Временная работа или подработка",
                "https://hh.ru/mentors?from=footer_new&hhtmFromLabel=footer_new&hhtmFrom=main&purposeId=1",
                Button("Поднять")
            ),
            Offer("temporary_job", "  Временная работа или подработка", "https://hh.ru/", null),
            Offer(null, "Полезные статьи и советы", "https://hh.ru/articles?hhtmFrom=main", null)
        )
        offerAdapter.submitList(offers)
    }

    private fun testVacancyAdapter() {
        val vacancies = listOf(
            Vacancy(
                "1",
                5,
                "UI/UX Designer",
                Address("Minsk", "Belinskogo", "5"),
                "Mobyrix",
                Experience("Опыт от 1 до 3 лет", "1-3 года"),
                "2025-02-20",
                true,
                Salary("от 60 000 ₽ до вычета налогов", "от 60 000 ₽"),
                listOf("частичная занятость", "полный день"),
                7,
                "Мы – аутсорсинговая аккредитованная IT-компания",
                "- совместно с Product Owner",
                listOf("Где располагается место работы?", "Какой график работы?")
            ),
            Vacancy(
                "2",
                null,
                "Cleaner",
                Address("Moscow", "Bel", "18"),
                "ChinaTown",
                Experience("Опыт от 1 до 3 лет", "1-3 года"),
                "2025-01-02",
                true,
                Salary("от 60 000 ₽ до вычета налогов", "от 60 000 ₽"),
                listOf("частичная занятость", "полный день"),
                7,
                "Мы – аутсорсинговая аккредитованная IT-компания",
                "- совместно с Product Owner",
                listOf("Где располагается место работы?", "Какой график работы?")
            ),
            Vacancy(
                "3",
                1,
                "UI/UX Designer",
                Address("Minsk", "Belinskogo", "5"),
                "Mobyrix",
                Experience("Опыт от 1 до 3 лет", "1-3 года"),
                "2025-03-29",
                true,
                Salary("от 60 000 ₽ до вычета налогов", "от 60 000 ₽"),
                listOf("частичная занятость", "полный день"),
                7,
                "Мы – аутсорсинговая аккредитованная IT-компания",
                "- совместно с Product Owner",
                listOf("Где располагается место работы?", "Какой график работы?")
            ),
            Vacancy(
                "4",
                8,
                "UI/UX Designer",
                Address("Minsk", "Belinskogo", "5"),
                "Mobyrix",
                Experience("Опыт от 1 до 3 лет", "1-3 года"),
                "2025-01-25",
                true,
                Salary("от 60 000 ₽ до вычета налогов", "от 60 000 ₽"),
                listOf("частичная занятость", "полный день"),
                7,
                "Мы – аутсорсинговая аккредитованная IT-компания",
                "- совместно с Product Owner",
                listOf("Где располагается место работы?", "Какой график работы?")
            )
        )
        vacancyAdapter.submitList(vacancies)

        binding.btnAllVacancies.text =
            getString(R.string.text_all_vacancies, getVacancyGenitiveCase(vacancies.size))
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
        private const val TAG = "Job Search Test"
        private const val VACANCY_FORM_SINGULAR = "вакансия"
        private const val VACANCY_FORM_FEW = "вакансии"
        private const val VACANCY_FORM_MANY = "вакансий"
    }
}