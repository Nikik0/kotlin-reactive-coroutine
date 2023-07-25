package com.nikik0.kotlinProject.repositoiries

import com.nikik0.kotlinProject.entities.CompanyEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

interface CompanyRepository: CoroutineCrudRepository<CompanyEntity, Long> {
    fun getCompanyEntitiesByAddress(address: String): Flow<CompanyEntity>
    fun getCompanyEntitiesByName(name: String): Flow<CompanyEntity>

    @Query("SELECT application.company.* FROM application.company WHERE application.company.id = 1 LIMIT 2")
    fun getTest(): CompanyEntity
}