package com.jtortosasen.data

import arrow.core.Either
import com.jtortosasen.data.remote.ApiService
import com.jtortosasen.domain.data.UserRepository
import com.jtortosasen.domain.models.Error
import com.jtortosasen.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class UserRepositoryImpl(private val api: ApiService) : UserRepository {

    override suspend fun getUserList(page: Int, resultSize: Int, seed: String): Flow<Either<Error, List<User>>> = flow {
        try {
            emit(Either.Right(api.fetchRandomUserList(page, resultSize, seed).results.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Either.Left(e.toError()))
        }
    }
}