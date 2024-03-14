package com.jtortosasen.randomuserapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.jtortosasen.domain.data.UserRepository
import com.jtortosasen.domain.models.Error
import com.jtortosasen.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OverviewViewModel(private val userRepository: UserRepository): ViewModel() {

    private var canPaginate = true
    private var currentPage = 1
    private val resultSize = 20
    private val seed = "abc"

    data class UiState(
        val loading: Boolean = false,
        val refreshing: Boolean = false,
        val toggleSearch: Boolean = false,
        val error: Error? = null,
        val listUsers: List<User> = emptyList(),
    )

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    val searchResults: StateFlow<List<User>> =
        snapshotFlow { searchQuery }
            .combine(state) { searchQuery, uiState ->
                when {
                    searchQuery.isNotEmpty() -> uiState.listUsers.filter { user ->
                        user.name.contains(searchQuery, ignoreCase = true) || user.email.contains(searchQuery, ignoreCase = true)

                    }
                    else -> uiState.listUsers
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    init {
        _state.value =  _state.value.copy(loading = true)
        paginateUserList()
    }

    fun paginateUserList() {
        if (!canPaginate)
            return

        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(loading = true)
            userRepository.getUserList(page = currentPage, resultSize = resultSize, seed).collect {
                it.fold(::showError, ::updatePaginatedUserList)
            }
        }
    }

    private fun updatePaginatedUserList(list: List<User>){
        currentPage++
        canPaginate = list.size == resultSize
        _state.value = _state.value.copy(listUsers = _state.value.listUsers + list, loading = false)
    }

    private fun showError(error: Error) {
        Logger.d { error.toString() }
        _state.value =  _state.value.copy(error = error, loading = false)
    }

    fun changeSearchQuery(query: String){
        searchQuery = query
    }

    fun toggleSearch(toggle: Boolean){
        _state.value = _state.value.copy(toggleSearch = toggle)
    }

    fun refreshList() = viewModelScope.launch {
        _state.value = _state.value.copy(refreshing = true)
        currentPage = 1
        userRepository.getUserList(page = currentPage, resultSize = resultSize, seed).collect {
            it.fold(
                { error -> showError(error)},
                { result ->  _state.value = _state.value.copy(listUsers = result, loading = false, refreshing = false)}
            )
        }
    }



}