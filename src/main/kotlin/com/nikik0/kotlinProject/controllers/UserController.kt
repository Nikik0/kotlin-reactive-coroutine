package com.nikik0.kotlinProject.controllers

import com.nikik0.kotlinProject.dtos.CompanyRequestDto
import com.nikik0.kotlinProject.dtos.UserDto
import com.nikik0.kotlinProject.dtos.UserRequestDto
import com.nikik0.kotlinProject.dtos.UserResponseDto
import com.nikik0.kotlinProject.entities.UserEntity
import com.nikik0.kotlinProject.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

private fun UserEntity.toResponseDto(): UserResponseDto =
    UserResponseDto(
        id = this.id,
        name = this.name,
        email = this.email,
        age = this.age,
        companyId = this.companyId
    )

private fun UserRequestDto.toEntity(): UserEntity =
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
    private val userService: UserService
        ){

    @GetMapping("/{id}")
    suspend fun getSingle(@PathVariable id: Long): UserResponseDto? =
        userService.getSingle(id).toResponseDto()

    @GetMapping("/all")
    suspend fun getAll(): Flow<UserResponseDto> =
        userService.getAll().map { entity -> entity.toResponseDto() }

    @DeleteMapping("/delete")
    suspend fun delete(@RequestBody userDto: UserRequestDto): HttpStatus {
        userService.deleteUser(userDto.toEntity())
        if (userService.getSingle(userDto.id) == null) return HttpStatus.OK else HttpStatus.BAD_REQUEST
        return HttpStatus.OK
    }

    @PostMapping("/save")
    suspend fun saveSingle(@RequestBody userDto: UserRequestDto): UserResponseDto =
        userService.saveUser(userDto.toEntity()).toResponseDto()

    @PostMapping("/update")
    suspend fun updateUser(@RequestBody userDto: UserRequestDto): UserResponseDto {
        if (userService.getSingle(userDto.id) == null) throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return userService.updateUser(userDto.toEntity()).toResponseDto()
    }

    @GetMapping("/age/{lowerAge}/{upperAge}")
    suspend fun getAllByAgeBetween(@PathVariable lowerAge: Int, @PathVariable upperAge: Int): Flow<UserResponseDto> =
        userService.getAllByAgeBetween(lowerAge, upperAge).map { entity -> entity.toResponseDto() }
}