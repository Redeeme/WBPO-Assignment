package sk.doxxbet.doxxbetui.wpbo_assignment.ui

import sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos.UserDto

data class UserModel(
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val email: String,
    val followed: Boolean,
    val id: Int
)

fun UserDto.toUserModel(followed: Boolean): UserModel {
    return UserModel(
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar,
        email = this.email,
        followed = followed,
        id = this.id
    )
}
