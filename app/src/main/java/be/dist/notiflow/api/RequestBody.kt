package be.dist.notiflow.api

data class RequestBody(
    var `package`: String,
    var name: String,
    var message: String,
    var title: String
)
