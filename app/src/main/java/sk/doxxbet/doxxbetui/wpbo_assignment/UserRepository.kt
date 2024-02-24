package sk.doxxbet.doxxbetui.wpbo_assignment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userService: UserService) {

    suspend fun register(request: RegisterRequest): Resource<RegisterResponse> =
        withContext(Dispatchers.IO) {
            return@withContext executeRequest(
                request = {

                    userService.register(request)

                }
            )
        }

    suspend fun getUsers(): Resource<UserResponse> =
        withContext(Dispatchers.IO) {
            return@withContext executeRequest(
                request = {
                    userService.getUsers(1,5)
                }
            )
        }
}

