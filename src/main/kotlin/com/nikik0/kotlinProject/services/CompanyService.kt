package com.nikik0.kotlinProject.services

import com.nikik0.kotlinProject.entities.CompanyEntity
import com.nikik0.kotlinProject.exceptions.InvalidRequestException
import com.nikik0.kotlinProject.exceptions.NotFoundResponseException
import com.nikik0.kotlinProject.repositoiries.CompanyRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CompanyService (
    private val companyRepository: CompanyRepository
        ){

    suspend fun getSingleCompany(id: Long): CompanyEntity? {
        return companyRepository.findById(id)
    }

    suspend fun getAllCompanies(): Flow<CompanyEntity> = companyRepository.findAll()

    suspend fun getAllCompaniesByAddress(address: String): Flow<CompanyEntity> =
        companyRepository.getCompanyEntitiesByAddress(address)

    suspend fun getAllCompaniesByName(name: String): Flow<CompanyEntity> =
        companyRepository.getCompanyEntitiesByName(name)

    suspend fun createCompany(company: CompanyEntity): CompanyEntity =
        companyRepository.findById(company.id)
            ?.let { throw InvalidRequestException() }
            ?: companyRepository.save(company)

    suspend fun updateCompany(company: CompanyEntity): CompanyEntity =
         companyRepository.save(company)

    suspend fun deleteCompany(company: CompanyEntity): Unit =
        companyRepository.delete(company)

    suspend fun deleteCompanyById(id: Long): Unit =
        companyRepository.deleteById(id)

    suspend fun getAllCompaniesByNameWith(name: String): Flow<CompanyEntity> =
        companyRepository.findAllByNameContainingIgnoreCase(name)
}