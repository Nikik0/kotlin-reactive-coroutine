package com.nikik0.kotlinProject.services

import com.nikik0.kotlinProject.entities.UserEntity
import com.nikik0.kotlinProject.exceptions.InvalidRequestException
import com.nikik0.kotlinProject.exceptions.NotFoundResponseException
import com.nikik0.kotlinProject.repositoiries.UserRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
        private var userRepository: UserRepository
    ) {

    suspend fun createUser(user: UserEntity): UserEntity =
        userRepository.findById(user.id)
            ?.let { throw InvalidRequestException() }
            ?:userRepository.save(user)

    suspend fun getSingleUserById(id: Long): UserEntity? =
            userRepository.findById(id)

    suspend fun getAllUsers(): Flow<UserEntity> =
        userRepository.findAll()

    suspend fun updateUser(user: UserEntity): UserEntity =
            userRepository.save(user)

    suspend fun deleteUser(user: UserEntity): Unit =
        userRepository.delete(user)

    suspend fun getAllUsersByAgeBetween(lowerAge: Int, upperAge: Int): Flow<UserEntity> =
        userRepository.getAllByAgeBetween(lowerAge, upperAge)
    suspend fun deleteUserById(id: Long): Unit =
        userRepository.deleteById(id)

    suspend fun getUserByEmail(email: String): Flow<UserEntity> =
        userRepository.getByEmail(email)

    suspend fun getAllUsersByNameWith(name: String): Flow<UserEntity> =
        userRepository.findAllByNameContainingIgnoreCase(name)

    suspend fun getAllUsersByCompanyId(companyId: Long): Flow<UserEntity> =
        userRepository.findAllByCompanyId(companyId)
}