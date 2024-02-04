package com.nikik0.kotlinProject.controllers

import com.nikik0.kotlinProject.dtos.BaseResponse
import com.nikik0.kotlinProject.dtos.EntityType
import com.nikik0.kotlinProject.entities.CompanyEntity
import com.nikik0.kotlinProject.entities.UserEntity
import com.nikik0.kotlinProject.exceptions.NotFoundResponseException
import com.nikik0.kotlinProject.services.CompanyService
import com.nikik0.kotlinProject.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEmpty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private fun UserEntity.toBaseResponse(): BaseResponse =
    BaseResponse(
        id = this.id,
        name = this.name,
        type = EntityType.USER
    )

private fun CompanyEntity.toBaseResponse(): BaseResponse =
    BaseResponse(
        id = this.id,
        name = this.name,
        type = EntityType.COMPANY
    )

@RestController
@RequestMapping("/api/v1/search")
class SearchController(
    private val userService: UserService,
    private val companyService: CompanyService
) {

    @GetMapping("/{name}")
    suspend fun searchByName(@PathVariable name: String): Flow<BaseResponse> {
        val users = userService.getAllUsersByNameWith(name).map { it.toBaseResponse() }
        val companies = companyService.getAllCompaniesByNameWith(name).map { it.toBaseResponse() }
        return merge(users, companies).onEmpty { throw NotFoundResponseException() }
    }
}