package com.nikik0.kotlinProject.controllers

import com.nikik0.kotlinProject.dtos.UserRequestDto
import com.nikik0.kotlinProject.dtos.UserResponseDto
import com.nikik0.kotlinProject.entities.UserEntity
import com.nikik0.kotlinProject.exceptions.NotFoundResponseException
import com.nikik0.kotlinProject.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
    suspend fun getSingleUserById(@PathVariable id: Long): UserResponseDto? =
        userService.getSingleUserById(id)
            ?.toResponseDto()
            ?: throw NotFoundResponseException()

    @GetMapping("/all")
    suspend fun getAllUsers(): Flow<UserResponseDto> =
        userService.getAllUsers()
            .onEmpty { throw NotFoundResponseException() }
            .map { it.toResponseDto() }

    @DeleteMapping("/delete")
    suspend fun deleteSingleUser(@RequestBody userDto: UserRequestDto): HttpStatus {
        userService.deleteUser(userDto.toEntity())
        return if (userService.getSingleUserById(userDto.id) == null) HttpStatus.OK else HttpStatus.BAD_REQUEST
    }

    @PostMapping("/save")
    suspend fun saveSingleUser(@RequestBody userDto: UserRequestDto): UserResponseDto =
        userService.createUser(userDto.toEntity()).toResponseDto()

    @PostMapping("/update")
    suspend fun updateSingleUser(@RequestBody userDto: UserRequestDto): UserResponseDto {
        if (userService.getSingleUserById(userDto.id) == null) throw NotFoundResponseException()
        return userService.updateUser(userDto.toEntity()).toResponseDto()
    }

    @GetMapping("/age/{lowerAge}/{upperAge}")
    suspend fun getAllByAgeBetween(@PathVariable lowerAge: Int, @PathVariable upperAge: Int): Flow<UserResponseDto> =
        userService.getAllUsersByAgeBetween(lowerAge, upperAge)
            .onEmpty { throw NotFoundResponseException() }
            .map { it.toResponseDto() }

    @GetMapping("/find/email/{email}")
    suspend fun getUserByEmail(@PathVariable email: String): UserResponseDto =
        userService.getUserByEmail(email)
            .firstOrNull()
            ?.toResponseDto()
            ?:throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @DeleteMapping("/delete/{id}")
    suspend fun deleteUserById(@PathVariable id: Long): HttpStatus {
        userService.deleteUserById(id)
        return if (userService.getSingleUserById(id) == null) HttpStatus.OK else HttpStatus.NOT_FOUND
    }
}