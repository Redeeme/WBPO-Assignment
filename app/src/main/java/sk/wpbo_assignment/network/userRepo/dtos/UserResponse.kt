package sk.wpbo_assignment.network.userRepo.dtos

import com.squareup.moshi.Json

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
