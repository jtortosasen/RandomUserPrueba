package com.jtortosasen.data.console

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import com.jtortosasen.data.di.dataModule
import com.jtortosasen.data.remote.ApiService
import com.jtortosasen.domain.data.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.GlobalContext
import kotlin.system.measureTimeMillis

suspend fun main() {
    Logger.setLogWriters(CommonWriter())
    GlobalContext.startKoin { modules(dataModule) }
    TestApi().runTests()
    GlobalContext.stopKoin()
}


private class TestApi : KoinComponent {
    private val userRepository: UserRepository = get()
    private val apiService: ApiService = get()

    suspend fun runTests() {
        val timeTaken = measureTimeMillis {
            testGetUserList()
        }
        Logger.i { "Tiempo de ejecución: $timeTaken ms" }
        apiService.close()

    }

    private suspend fun testGetUserList() {
        userRepository.getUserList(1, 10, "abc").collect {
            showResponse(it)
        }

    }
}

