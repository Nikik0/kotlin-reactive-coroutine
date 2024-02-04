package com.nikik0.kotlinProject.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "user")
data class UserEntity(
    @Id
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
    @Column("company_id")
    val companyId: Long
)
