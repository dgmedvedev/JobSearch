package com.medvedev.jobsearch.domain.model.offer

import com.medvedev.jobsearch.data.network.model.offer.ButtonDto

data class Offer(
    val id: String?,
    val title: String?,
    val link: String?,
    val button: ButtonDto?
)