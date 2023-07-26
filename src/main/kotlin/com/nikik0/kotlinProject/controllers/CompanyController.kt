package com.nikik0.kotlinProject.controllers

import com.nikik0.kotlinProject.dtos.CompanyRequestDto
import com.nikik0.kotlinProject.dtos.CompanyResponseDto
import com.nikik0.kotlinProject.entities.CompanyEntity
import com.nikik0.kotlinProject.services.CompanyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        address = this.address
    )


@RestController
@RequestMapping("/api/v1/companies")
class CompanyController(
    private val companyService: CompanyService
) {

    @GetMapping("/test")
    suspend fun test(): CompanyEntity? {
        println("test is entered")
        return companyService.getSingle(1)
    }

    @GetMapping("/{id}")
    suspend fun getSingleById(@PathVariable id: Long): CompanyResponseDto =
        companyService.getSingle(id)
            ?.toResponseDto()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @GetMapping("/all")
    suspend fun getAll(): Flow<CompanyResponseDto> {
        return companyService.getAll().map { it.toResponseDto() }
    }

    @PostMapping("/save")
    suspend fun saveSingle(@RequestBody companyDto: CompanyRequestDto): CompanyResponseDto {
    return companyService.saveCompany(companyDto.toEntity()).toResponseDto()
}

    @PostMapping("/update")
    suspend fun updateSingle(@RequestBody companyDto: CompanyRequestDto): CompanyResponseDto {
        if (companyService.getSingle(companyDto.id) == null) throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return companyService.updateCompany(companyDto.id, companyDto.toEntity()).toResponseDto()
    }

    @PostMapping("/delete")
    suspend fun deleteSingle(@RequestBody companyDto: CompanyRequestDto): HttpStatus {
        companyService.deleteCompany(companyDto.toEntity())
        return if (companyService.getSingle(companyDto.id) == null) HttpStatus.OK
        else HttpStatus.BAD_REQUEST
    }

    @GetMapping("/find/address/{address}")
    suspend fun getAllByAddress(@PathVariable address: String): Flow<CompanyResponseDto> =
        companyService.getAllByAddress(address).map { it.toResponseDto() }

    @GetMapping("/find/name/{name}")
    suspend fun getAllByName(@PathVariable name: String): Flow<CompanyResponseDto> =
        companyService.getAllByName(name).map { it.toResponseDto() }

    @DeleteMapping("delete/{id}")
    suspend fun deleteById(@PathVariable id: Long): HttpStatus {
        companyService.deleteCompanyById(id)
        return if (companyService.getSingle(id) == null) HttpStatus.OK else HttpStatus.NOT_FOUND
    }

}