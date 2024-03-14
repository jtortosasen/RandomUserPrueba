package com.jtortosasen.domain.data

import arrow.core.Either
import com.jtortosasen.domain.models.Error
import com.jtortosasen.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserList(page: Int, resultSize: Int, seed: String): Flow<Either<Error, List<User>>>
}