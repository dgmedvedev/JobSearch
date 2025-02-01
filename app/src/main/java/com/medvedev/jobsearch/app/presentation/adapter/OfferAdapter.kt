package com.medvedev.jobsearch.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.OfferItemBinding
import com.medvedev.jobsearch.domain.model.offer.Offer

class OfferAdapter(private val onOfferItemClickListener: (Offer) -> Unit) :
    ListAdapter<Offer, OfferAdapter.OfferHolder>(OfferDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferHolder {
        val binding = OfferItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OfferHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferHolder, position: Int) {
        val offer: Offer = getItem(position)
        holder.bind(offer = offer)
    }

    inner class OfferHolder(private val binding: OfferItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var offerItem: Offer

        fun bind(offer: Offer) {
            offerItem = offer
            binding.apply {
                ivIcon.setImageResource(
                    when (offerItem.id) {
                        root.context.getString(R.string.id_near_vacancies) -> R.drawable.near_vacancies
                        root.context.getString(R.string.id_level_up_resume) -> R.drawable.level_up_resume
                        root.context.getString(R.string.id_temporary_job) -> R.drawable.temporary_job_icon
                        else -> UNKNOWN_ICON_ID
                    }
                )
                tvTitle.text = offerItem.title?.trim()
                if (offerItem.button != null) {
                    tvTitle.maxLines = 2
                    tvTitle.ellipsize = android.text.TextUtils.TruncateAt.END
                } else {
                    tvTitle.maxLines = 3
                    tvTitle.ellipsize = android.text.TextUtils.TruncateAt.END
                }
                offerItem.button?.let { button ->
                    tvUplift.text = button.text?.trim()
                }
                root.setOnClickListener {
                    onOfferItemClickListener.invoke(offerItem)
                }
            }
        }
    }

    private class OfferDiffCallback : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val UNKNOWN_ICON_ID = 0
    }
}