package com.nikik0.kotlinProject.controllers

import com.nikik0.kotlinProject.dtos.CompanyDto
import com.nikik0.kotlinProject.entities.CompanyEntity
import com.nikik0.kotlinProject.services.CompanyService
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private fun CompanyEntity.toDto(): CompanyDto =
    CompanyDto(
        id = this.id,
        name = this.name,
        address = this.address
    )

private fun CompanyDto.toEntity(): CompanyEntity =
    CompanyEntity(
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
    suspend fun test():CompanyEntity {
        println("test is entered")
        return companyService.getSingle(1)
    }

    @GetMapping("/{id}")
    suspend fun getSingleById(@PathVariable id: Long): CompanyEntity =
        companyService.getSingle(id) //todo fighting nulls here might be a bad idea

//    @GetMapping("/all")
//    suspend fun getAll(): Flow<CompanyEntity> =
//        companyService.getAll()

    @GetMapping("/all")
    suspend fun getAll(): Flow<CompanyEntity> {
        println(companyService.getAll())
        return companyService.getAll()
    }

    @PostMapping("/save")
    suspend fun saveSingle(@RequestBody companyDto: CompanyDto): CompanyDto {
 println("entered save contr")
    return companyService.saveCompany(companyDto.toEntity()).toDto()
}

    @PostMapping("/update")
    suspend fun updateSingle(@RequestBody companyDto: CompanyDto): CompanyDto =
        companyService.updateCompany(companyDto.id, companyDto.toEntity()).toDto()

    //todo should return meaningful response
    @PostMapping("/delete")
    suspend fun deleteSingle(@RequestBody companyDto: CompanyDto): Unit =
        companyService.deleteCompany(companyDto.toEntity())
}