package com.nikik0.kotlinProject.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "application.company")
data class CompanyEntity(
    @Id
    val id: Long,
    val name: String,
    val address: String
)
