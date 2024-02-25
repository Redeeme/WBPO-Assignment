package sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos

import com.squareup.moshi.Json
import sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos.UserDto

data class UserResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "per_page")
    val perPage: Int,
    @Json(name = "total")
    val total: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "data")
    val users: List<UserDto>,
)
