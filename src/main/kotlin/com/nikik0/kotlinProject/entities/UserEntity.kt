package com.nikik0.kotlinProject.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "application.app_user")
data class UserEntity(
    @Id
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
    val companyId: Long
)
