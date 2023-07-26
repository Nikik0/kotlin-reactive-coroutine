package com.nikik0.kotlinProject.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class NotFoundResponseException : ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found, request is incorrect")