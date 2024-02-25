package sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "error")
    val message: String? = null
)
