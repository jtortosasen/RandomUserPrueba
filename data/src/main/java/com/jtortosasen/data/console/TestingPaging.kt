package com.jtortosasen.data.console

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import com.jtortosasen.data.di.dataModule
import com.jtortosasen.data.remote.ApiService
import com.jtortosasen.domain.data.UserRepository
import com.jtortosasen.domain.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.GlobalContext


suspend fun main() {
    Logger.setLogWriters(CommonWriter())
    GlobalContext.startKoin { modules(dataModule) }
    TestPaging().run()
    GlobalContext.stopKoin()
}

private class TestPaging : KoinComponent {
    private val userRepository: UserRepository = get()
    private val apiService: ApiService = get()

    val _userListFlow = MutableStateFlow<List<User>>(emptyList())
    val userListFlow: StateFlow<List<User>> = _userListFlow.asStateFlow()

    var page = 1
    var resultSize = 10
    var canPaginate = true


    suspend fun run() {
        CoroutineScope(Dispatchers.IO).launch {
            userListFlow.collect { list ->
                Logger.i { "Collecting" }
                // Reading paginated list
                list.forEach { Logger.i { it.toString() } }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            repeat (2) {
                getUserList()
                delay(1000)
            }
        }.join()

        apiService.close()
    }

    suspend fun getUserList() {
        if (!canPaginate)
            return

        userRepository.getUserList(page = page, resultSize = resultSize, "abc").collect {
            it.fold(
                { error ->
                    canPaginate = false
                    printError(error)
                },
                { result ->
                    _userListFlow.value += result
                    page++
                    canPaginate = result.size == resultSize
                })
        }
    }
}
