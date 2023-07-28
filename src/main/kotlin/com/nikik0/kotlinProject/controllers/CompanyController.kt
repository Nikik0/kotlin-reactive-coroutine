package com.nikik0.kotlinProject.controllers

import com.nikik0.kotlinProject.dtos.CompanyRequestDto
import com.nikik0.kotlinProject.dtos.CompanyResponseDto
import com.nikik0.kotlinProject.entities.CompanyEntity
import com.nikik0.kotlinProject.entities.UserEntity
import com.nikik0.kotlinProject.exceptions.NotFoundResponseException
import com.nikik0.kotlinProject.services.CompanyService
import com.nikik0.kotlinProject.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

private fun CompanyRequestDto.toEntity(): CompanyEntity =
    CompanyEntity(
        id = this.id,
        name = this.name,
        address = this.address
    )
private fun CompanyEntity.toResponseDto(): CompanyResponseDto =
    CompanyResponseDto(
        id = this.id,
        name = this.name,
        address = this.address,
        users = emptyList<UserEntity>()
    )
private fun CompanyEntity.toResponseDto(users: List<UserEntity>): CompanyResponseDto =
    CompanyResponseDto(
        id = this.id,
        name = this.name,
        address = this.address,
        users = users
    )

@RestController
@RequestMapping("/api/v1/companies")
class CompanyController(
    private val companyService: CompanyService,
    private val userService: UserService
) {

    suspend fun CompanyEntity.toFullResponseDto(): CompanyResponseDto =
        CompanyResponseDto(
            id = this.id,
            name = this.name,
            address = this.address,
            users = userService.getAllUsersByCompanyId(this.id).toList()
        )

    @GetMapping("/test")
    suspend fun test(): CompanyResponseDto? {
        println("test is entered")
        return companyService.getSingleCompany(1)?.toFullResponseDto()
    }

    @GetMapping("/{id}")
    suspend fun getSingleCompanyById(@PathVariable id: Long): CompanyResponseDto =
        companyService.getSingleCompany(id)?.toFullResponseDto()
            ?: throw NotFoundResponseException()

    @GetMapping("/all")
    suspend fun getAllCompanies(): Flow<CompanyResponseDto> =
        companyService.getAllCompanies()
            .onEmpty { throw NotFoundResponseException() }
            .map { it.toFullResponseDto() }

    @PostMapping("/save")
    suspend fun saveSingleCompany(@RequestBody companyDto: CompanyRequestDto): CompanyResponseDto =
        companyService.createCompany(companyDto.toEntity()).toFullResponseDto()

    @PostMapping("/update")
    suspend fun updateSingleCompany(@RequestBody companyDto: CompanyRequestDto): CompanyResponseDto {
        return if (companyService.getSingleCompany(companyDto.id) == null) throw NotFoundResponseException()
        else companyService.updateCompany(companyDto.toEntity()).toFullResponseDto()
    }

    @DeleteMapping("/delete")
    suspend fun deleteSingleCompany(@RequestBody companyDto: CompanyRequestDto): HttpStatus {
        companyService.deleteCompany(companyDto.toEntity())
        return if (companyService.getSingleCompany(companyDto.id) == null) HttpStatus.OK
        else HttpStatus.BAD_REQUEST
    }

    @GetMapping("/find/address/{address}")
    suspend fun getAllCompaniesByAddress(@PathVariable address: String): Flow<CompanyResponseDto> =
        companyService.getAllCompaniesByAddress(address)
            .onEmpty { throw NotFoundResponseException() }
            .map { it.toFullResponseDto() }

    @GetMapping("/find/name/{name}")
    suspend fun getAllCompaniesByName(@PathVariable name: String): Flow<CompanyResponseDto> =
        companyService.getAllCompaniesByName(name)
            .onEmpty { throw NotFoundResponseException() }
            .map { it.toFullResponseDto() }

    @DeleteMapping("delete/{id}")
    suspend fun deleteCompanyById(@PathVariable id: Long): HttpStatus {
        companyService.deleteCompanyById(id)
        return if (companyService.getSingleCompany(id) == null) HttpStatus.OK else HttpStatus.NOT_FOUND
    }

}