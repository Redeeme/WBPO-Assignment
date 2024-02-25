package sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos

import com.squareup.moshi.Json

data class RegisterRequest(
    @Json(name = "password")
    val password: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "email")
    val email: String,
)