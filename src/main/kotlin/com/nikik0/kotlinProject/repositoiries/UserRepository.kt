package com.nikik0.kotlinProject.repositoiries

import com.nikik0.kotlinProject.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CoroutineCrudRepository<UserEntity, Long> {
    fun getAllByAgeBetween(lowerAge: Int, upperAge: Int): Flow<UserEntity>
    fun getByEmail(email: String): Flow<UserEntity>
    fun findAllByNameContainingIgnoreCase(name: String): Flow<UserEntity>
    fun findAllByCompanyId(companyId: Long): Flow<UserEntity>
}