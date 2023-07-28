package com.nikik0.kotlinProject.dtos

data class BaseResponse(
    val id: Long,
    val name: String,
    val type: EntityType
)
 enum class EntityType{
     USER,
     COMPANY
 }