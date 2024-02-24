package sk.doxxbet.doxxbetui.wpbo_assignment

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "message")
    val message: String? = null
)
