package com.medvedev.jobsearch.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.VacancyItemBinding
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy

class VacancyAdapter(
    private val onVacancyItemClickListener: () -> Unit,
    private val onVacancyIconClickListener: (Vacancy) -> Unit,
    private val onButtonApplyClickListener: (() -> Unit)? = null
) :
    ListAdapter<Vacancy, VacancyAdapter.VacancyHolder>(VacancyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyHolder {
        val binding = VacancyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VacancyHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyHolder, position: Int) {
        val vacancy: Vacancy = getItem(position)
        holder.bind(vacancy = vacancy)
    }

    inner class VacancyHolder(private val binding: VacancyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var vacancyItem: Vacancy

        fun bind(vacancy: Vacancy) {
            vacancyItem = vacancy
            binding.apply {
                vacancyItem.lookingNumber?.let { lookingNumber ->
                    tvLookingNumber.text =
                        root.context.getString(
                            R.string.text_looking_number,
                            "$lookingNumber",
                            declineWord(lookingNumber)
                        )
                }
                tvTitle.text = vacancyItem.title?.trim()
                vacancyItem.address?.let { address ->
                    tvTown.text = address.town?.trim()
                }
                tvCompany.text = vacancyItem.company
                vacancyItem.experience?.let { experience ->
                    tvExperience.text = experience.previewText?.trim()
                }
                vacancyItem.publishedDate?.let { publishedDate ->
                    val day = publishedDate.substring(8, 10).toInt()
                    val month = publishedDate.substring(5, 7).toInt()
                    val monthName = getMonthName(month)
                    tvPublishDate.text =
                        root.context.getString(R.string.text_publish_date, "$day", monthName)
                }
                btnApply.text = root.context.getString(R.string.apply)
                setFavoriteVacancyIcon(ivFavoriteVacancyIcon, vacancyItem.isFavorite)

                btnApply.setOnClickListener {
                    onButtonApplyClickListener?.invoke()
                }

                ivFavoriteVacancyIcon.setOnClickListener {
                    onVacancyIconClickListener.invoke(vacancyItem)
                    vacancyItem.isFavorite = !vacancyItem.isFavorite
                    setFavoriteVacancyIcon(ivFavoriteVacancyIcon, vacancyItem.isFavorite)
                }

                root.setOnClickListener {
                    onVacancyItemClickListener.invoke()
                }
            }
        }
    }

    private class VacancyDiffCallback : DiffUtil.ItemCallback<Vacancy>() {
        override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
            return oldItem == newItem
        }
    }

    private fun setFavoriteVacancyIcon(imageView: ImageView, isFavorite: Boolean) {
        imageView.setImageResource(
            if (isFavorite) R.drawable.heart_is_favorite else R.drawable.heart
        )
    }

    private fun getMonthName(month: Int): String {
        val monthsGenitiveCase = arrayOf(
            JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
            JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
        )
        return monthsGenitiveCase[month - 1]
    }

    private fun declineWord(numberOfPeople: Int): String {
        return when {
            numberOfPeople in 12..14 -> PERSON_NOMINATIVE_CASE
            numberOfPeople % 10 in 0..1 -> PERSON_NOMINATIVE_CASE
            numberOfPeople % 10 in 5..9 -> PERSON_NOMINATIVE_CASE
            else -> PERSON_GENITIVE_CASE
        }
    }

    companion object {
        private const val PERSON_NOMINATIVE_CASE = "человек"
        private const val PERSON_GENITIVE_CASE = "человека"
        private const val JANUARY = "января"
        private const val FEBRUARY = "февраля"
        private const val MARCH = "марта"
        private const val APRIL = "апреля"
        private const val MAY = "мая"
        private const val JUNE = "июня"
        private const val JULY = "июля"
        private const val AUGUST = "августа"
        private const val SEPTEMBER = "сентября"
        private const val OCTOBER = "октября"
        private const val NOVEMBER = "ноября"
        private const val DECEMBER = "декабря"
    }
}