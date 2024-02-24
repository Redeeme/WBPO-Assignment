package sk.doxxbet.doxxbetui.wpbo_assignment

import com.squareup.moshi.Json

//
//"id": 0,
//"email": "string",
//"first_name": "string",
//"last_name": "string",
//"avatar": "string"
data class UserDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "email")
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "avatar")
    val avatar: String,
)