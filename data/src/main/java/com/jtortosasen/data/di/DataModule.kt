package com.jtortosasen.data.di

import com.jtortosasen.data.UserRepositoryImpl
import com.jtortosasen.data.remote.ApiService
import com.jtortosasen.data.remote.httpClient
import com.jtortosasen.domain.data.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single {
        ApiService(httpClient)
    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}