package com.nikik0.kotlinProject.dtos

import com.nikik0.kotlinProject.entities.UserEntity
import org.springframework.data.annotation.Id

data class CompanyResponseDto(
    @Id
    val id: Long,
    val name: String,
    val address: String,
    val users: List<UserEntity>
)

