package sk.wpbo_assignment.network.userRepo

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import sk.wpbo_assignment.network.userRepo.dtos.RegisterRequest
import sk.wpbo_assignment.network.userRepo.dtos.RegisterResponse
import sk.wpbo_assignment.network.userRepo.dtos.UserResponse

interface UserService {

    @POST("/api/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @GET("/api/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<UserResponse>
}