package com.nikik0.kotlinProject.services

import com.nikik0.kotlinProject.entities.CompanyEntity
import com.nikik0.kotlinProject.repositoiries.CompanyRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CompanyService (
    private val companyRepository: CompanyRepository
        ){

    suspend fun test():CompanyEntity? {
        println("entered")
        return companyRepository.getTest()//companyRepository.findById(1)
    }

    //todo let untested
    suspend fun getSingle(id: Long): CompanyEntity =
        companyRepository.findById(id).let { throw ResponseStatusException(HttpStatus.NOT_FOUND) }

//    suspend fun getAll(): Flow<CompanyEntity> = companyRepository.findAll()

    suspend fun getAll(): Flow<CompanyEntity> {
        println(companyRepository.findAll())
        return companyRepository.findAll()
    }


    suspend fun getAllByAddress(address: String): Flow<CompanyEntity> =
        companyRepository.getCompanyEntitiesByAddress(address)

    suspend fun getAllByName(name: String): Flow<CompanyEntity> =
        companyRepository.getCompanyEntitiesByName(name)

//    suspend fun saveCompany(company: CompanyDto): CompanyEntity =
//
//        val entity = CompanyEntity(id = , )
//        companyRepository.save()
    //todo all incoming entities should be dtos
    suspend fun saveCompany(company: CompanyEntity): CompanyEntity =
        companyRepository.findById(company.id)
            ?.let { throw ResponseStatusException(HttpStatus.BAD_REQUEST) }
            ?: companyRepository.save(company)

    suspend fun updateCompany(id: Long, company: CompanyEntity): CompanyEntity {
        val foundCompany = companyRepository.findById(company.id)
        return if (foundCompany == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        else companyRepository.save(company)
    }

    suspend fun deleteCompany(company: CompanyEntity): Unit =
        companyRepository.delete(company)
}