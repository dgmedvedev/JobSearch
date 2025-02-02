package com.medvedev.jobsearch.data.mapper

import com.medvedev.jobsearch.data.network.model.offer.ButtonDto
import com.medvedev.jobsearch.data.network.model.offer.OfferDto
import com.medvedev.jobsearch.domain.model.offer.Button
import com.medvedev.jobsearch.domain.model.offer.Offer

fun OfferDto.toDomain() = Offer(
    id = id,
    title = title,
    link = link,
    button = button?.toDomain()
)

fun ButtonDto.toDomain() = Button(
    text = text
)

fun List<OfferDto>.toOfferDomainList() = this.map { it.toDomain() }