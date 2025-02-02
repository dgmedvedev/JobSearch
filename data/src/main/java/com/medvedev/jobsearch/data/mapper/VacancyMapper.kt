package com.medvedev.jobsearch.data.mapper

import com.medvedev.jobsearch.data.network.model.vacancy.AddressDto
import com.medvedev.jobsearch.data.network.model.vacancy.ExperienceDto
import com.medvedev.jobsearch.data.network.model.vacancy.SalaryDto
import com.medvedev.jobsearch.data.network.model.vacancy.VacancyDto
import com.medvedev.jobsearch.domain.model.vacancy.Address
import com.medvedev.jobsearch.domain.model.vacancy.Experience
import com.medvedev.jobsearch.domain.model.vacancy.Salary
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy

fun VacancyDto.toDomain() = Vacancy(
    id = id,
    lookingNumber = lookingNumber,
    title = title,
    address = address?.toDomain(),
    company = company,
    experience = experience?.toDomain(),
    publishedDate = publishedDate,
    isFavorite = isFavorite,
    salary = salary?.toDomain(),
    schedules = schedules,
    appliedNumber = appliedNumber,
    description = description,
    responsibilities = responsibilities,
    questions = questions
)

fun List<VacancyDto>.toVacancyDomainList() = this.map { it.toDomain() }

fun AddressDto.toDomain() = Address(
    town = town,
    street = street,
    house = house
)

fun ExperienceDto.toDomain() = Experience(
    previewText = previewText,
    text = text
)

fun SalaryDto.toDomain() = Salary(
    fullSalary = fullSalary,
    shortSalary = shortSalary
)