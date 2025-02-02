package com.medvedev.jobsearch.data.repository

import com.google.gson.Gson
import com.medvedev.jobsearch.data.database.dao.JsonDataDao
import com.medvedev.jobsearch.data.database.model.JsonData
import com.medvedev.jobsearch.data.mapper.toOfferDomainList
import com.medvedev.jobsearch.data.mapper.toVacancyDomainList
import com.medvedev.jobsearch.data.network.ApiService
import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.repository.JobRepository
import java.util.concurrent.CopyOnWriteArrayList

class JobRepositoryImpl(
    private val apiService: ApiService,
    private val jsonDataDao: JsonDataDao
) :
    JobRepository {
    override suspend fun getOffers(): List<Offer> =
        apiService.getOffers().offers?.toOfferDomainList() ?: listOf()

    override suspend fun getVacancies(): List<Vacancy> =
        apiService.getVacancies().vacancies?.toVacancyDomainList() ?: listOf()

//    override suspend fun getVacanciesFavorite(): List<Vacancy> =
//        getVacancies().filter { vacancy -> vacancy.isFavorite }

    override suspend fun getVacanciesFavorite(): List<Vacancy> {
        val existingJsonData = jsonDataDao.getJsonData()
        return if (existingJsonData != null) {
            Gson().fromJson(existingJsonData.jsonVacancies, Array<Vacancy>::class.java)
                .toMutableList()
        } else mutableListOf()
    }

    override suspend fun insertVacancyFavorite(vacancy: Vacancy) {
        val currentList = getVacanciesFavorite()
        val synchronizedList = CopyOnWriteArrayList<Vacancy>()
        synchronizedList.addAll(currentList)
        var isExists = false
        for (v in synchronizedList) {
            if (vacancy.id == v.id) {
                isExists = true
            }
        }
        if (!isExists) synchronizedList.add(vacancy)
        val newJsonVacancies = Gson().toJson(synchronizedList)
        jsonDataDao.insertJsonData(JsonData(id = 0, jsonVacancies = newJsonVacancies))
    }

    override suspend fun deleteVacancyFavorite(vacancy: Vacancy) {
        val currentList = getVacanciesFavorite()
        val synchronizedList = CopyOnWriteArrayList<Vacancy>()
        synchronizedList.addAll(currentList)
        for (v in synchronizedList) {
            if (vacancy.id == v.id) {
                synchronizedList.remove(v)
            }
        }
        val newJsonVacancies = Gson().toJson(synchronizedList)
        jsonDataDao.insertJsonData(JsonData(id = 0, jsonVacancies = newJsonVacancies))
    }
}