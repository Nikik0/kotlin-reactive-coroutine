package com.nikik0.kotlinProject.dtos

import org.springframework.data.annotation.Id

data class UserResponseDto(
    @Id
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
    val companyId: Long
)
