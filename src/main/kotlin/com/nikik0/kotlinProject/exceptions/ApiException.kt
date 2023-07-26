package com.nikik0.kotlinProject.exceptions

class ApiException : RuntimeException {
    val code: Int? = null
    override val message: String? = null

    constructor() : super() {}
    constructor(code: Int?, message: String?) {
        var code = code
        var message = message
        code = code
        message = message
    }
}