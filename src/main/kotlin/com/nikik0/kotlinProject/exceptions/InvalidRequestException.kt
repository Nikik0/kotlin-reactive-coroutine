package com.nikik0.kotlinProject.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidRequestException: ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request, please check parameters for this request") {
}