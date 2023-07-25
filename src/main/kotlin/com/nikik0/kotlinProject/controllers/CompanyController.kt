package com.nikik0.kotlinProject.controllers

import com.nikik0.kotlinProject.dtos.CompanyDto
import com.nikik0.kotlinProject.dtos.CompanyRequestDto
import com.nikik0.kotlinProject.dtos.CompanyResponseDto
import com.nikik0.kotlinProject.entities.CompanyEntity
import com.nikik0.kotlinProject.services.CompanyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
    suspend fun test(): CompanyEntity {
        println("test is entered")
        return companyService.getSingle(1)
    }

    @GetMapping("/{id}")
    suspend fun getSingleById(@PathVariable id: Long): CompanyResponseDto =
        companyService.getSingle(id).toResponseDto() //todo fighting nulls here might be a bad idea


    @GetMapping("/all")
    suspend fun getAll(): Flow<CompanyResponseDto> {
        println(companyService.getAll())
        return companyService.getAll().map { entity -> entity.toResponseDto() }
    }

    @PostMapping("/save")
    suspend fun saveSingle(@RequestBody companyDto: CompanyRequestDto): CompanyResponseDto {
 println("entered save contr")
    return companyService.saveCompany(companyDto.toEntity()).toResponseDto()
}

    @PostMapping("/update")
    suspend fun updateSingle(@RequestBody companyDto: CompanyRequestDto): CompanyResponseDto =
        companyService.updateCompany(companyDto.id, companyDto.toEntity()).toResponseDto()

    @PostMapping("/delete")
    suspend fun deleteSingle(@RequestBody companyDto: CompanyRequestDto): HttpStatus {
        companyService.deleteCompany(companyDto.toEntity())
        return if (companyService.getSingle(companyDto.id) == null) HttpStatus.OK
        else HttpStatus.BAD_REQUEST
    }

    @PostMapping("/find/address")
    suspend fun getAllByAddress(@RequestBody companyDto: CompanyRequestDto): Flow<CompanyResponseDto> =
        companyService.getAllByAddress(companyDto.address).map { entity -> entity.toResponseDto() }

    @PostMapping("/find/name")
    suspend fun getAllByName(@RequestBody companyDto: CompanyRequestDto): Flow<CompanyResponseDto> =
        companyService.getAllByName(companyDto.name).map { entity -> entity.toResponseDto() }
}