package com.nikik0.kotlinProject.services

import com.nikik0.kotlinProject.entities.UserEntity
import com.nikik0.kotlinProject.repositoiries.UserRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
        private var userRepository: UserRepository
    ) {

    suspend fun saveUser(user: UserEntity): UserEntity =
        userRepository.save(user)

    suspend fun getSingle(id: Long): UserEntity? =
        userRepository.findById(id)

    suspend fun getAll(): Flow<UserEntity> =
        userRepository.findAll()

    suspend fun updateUser(user: UserEntity): UserEntity {
        val foundUser = userRepository.findById(user.id)
        return if (foundUser == null) throw ResponseStatusException(HttpStatus.NOT_FOUND)
            else userRepository.save(user)
    }

    suspend fun deleteUser(user: UserEntity): Unit =
        userRepository.delete(user)

    suspend fun deleteUserById(id: Long): Unit =
        userRepository.deleteById(id)
}