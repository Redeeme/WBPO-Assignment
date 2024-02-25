package sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sk.doxxbet.doxxbetui.wpbo_assignment.network.Resource
import sk.doxxbet.doxxbetui.wpbo_assignment.network.executeRequest
import sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos.RegisterRequest
import sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos.RegisterResponse
import sk.doxxbet.doxxbetui.wpbo_assignment.network.userRepo.dtos.UserResponse

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

