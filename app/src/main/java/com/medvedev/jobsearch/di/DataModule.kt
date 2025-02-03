package com.medvedev.jobsearch.di

import com.medvedev.jobsearch.data.database.JobDatabase
import com.medvedev.jobsearch.data.database.dao.JsonDataDao
import com.medvedev.jobsearch.data.network.ApiFactory
import com.medvedev.jobsearch.data.network.ApiService
import com.medvedev.jobsearch.data.repository.JobRepositoryImpl
import com.medvedev.jobsearch.domain.repository.JobRepository
import org.koin.dsl.module

val dataModule = module {

    single<ApiService> {
        ApiFactory.apiService
    }

    single<JsonDataDao> {
        JobDatabase.getInstance(context = get()).jsonDataDao()
    }

    single<JobRepository> {
        JobRepositoryImpl(
            apiService = get(),
            jsonDataDao = get()
        )
    }
}