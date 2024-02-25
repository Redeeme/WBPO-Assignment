package sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos

import com.squareup.moshi.Json

data class RegisterResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "token")
    val token: String,
)
