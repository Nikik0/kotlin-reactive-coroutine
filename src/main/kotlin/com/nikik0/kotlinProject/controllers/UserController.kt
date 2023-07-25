package com.nikik0.kotlinProject.controllers

import com.nikik0.kotlinProject.dtos.UserDto
import com.nikik0.kotlinProject.entities.UserEntity
import com.nikik0.kotlinProject.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private fun UserEntity.toDto(): UserDto =
    UserDto(
        id = this.id,
        name = this.name,
        email = this.email,
        age = this.age,
        companyId = this.companyId
    )
private fun UserDto.toEntity(): UserEntity =
    UserEntity(
        id = this.id,
        name = this.name,
        email = this.email,
        age = this.age,
        companyId = this.companyId
    )

@RestController
@RequestMapping("/api/v1/users")
class UserController (
    private var userService: UserService
        ){

    @GetMapping("/{id}")
    suspend fun getSingle(@PathVariable id: Long): UserDto? =
        userService.getSingle(id)?.toDto()

    @GetMapping("/all")
    suspend fun getAll(): Flow<UserDto> =
        userService.getAll().map { entity -> entity.toDto() }

    @DeleteMapping("/delete")
    suspend fun delete(@RequestParam userDto: UserDto): HttpStatus {
        userService.deleteUser(userDto.toEntity())
        if (userService.getSingle(userDto.id) == null) return HttpStatus.OK else HttpStatus.BAD_REQUEST
        return HttpStatus.OK
    }
}