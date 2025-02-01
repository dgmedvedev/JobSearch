package com.medvedev.jobsearch.data.mapper

import com.medvedev.jobsearch.data.network.model.offer.ButtonDto
import com.medvedev.jobsearch.data.network.model.offer.OfferDto
import com.medvedev.jobsearch.domain.model.offer.Button
import com.medvedev.jobsearch.domain.model.offer.Offer

fun Offer.toDto() = OfferDto(
    id = id,
    title = title,
    link = link,
    button = button?.toDto()
)

fun OfferDto.toDomain() = Offer(
    id = id,
    title = title,
    link = link,
    button = button?.toDomain()
)

fun Button.toDto() = ButtonDto(
    text = text
)

fun ButtonDto.toDomain() = Button(
    text = text
)

fun List<OfferDto>.toOfferDomainList() = this.map { it.toDomain() }