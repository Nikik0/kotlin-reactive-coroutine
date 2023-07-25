package com.nikik0.kotlinProject.dtos

import org.springframework.data.annotation.Id

data class CompanyRequestDto(
    @Id
    val id: Long,
    val name: String,
    val address: String
)

