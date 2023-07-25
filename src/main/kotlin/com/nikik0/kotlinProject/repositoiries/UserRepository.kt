package com.nikik0.kotlinProject.repositoiries

import com.nikik0.kotlinProject.entities.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CoroutineCrudRepository<UserEntity, Long> {
    //fun deleteById(id: Long): Boolean
}