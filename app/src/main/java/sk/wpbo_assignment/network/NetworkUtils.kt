package sk.wpbo_assignment.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import retrofit2.Response
import sk.wpbo_assignment.network.userRepo.dtos.ErrorResponse

suspend fun <T> executeRequest(
    request : suspend () -> Response<T>,
) : Resource<T> {
    return try {

        val result = request.invoke()
        val body = result.body()

        if (result.isSuccessful && body != null) {
            Resource.Success(body)
        } else {
            result.errorBody()?.source()?.let { bs ->
                val parsedBody = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(
                    ErrorResponse::class.java).fromJson(bs)
                Resource.Error<T>(
                    message = parsedBody?.message ?: result.message()
                )
            } ?: Resource.Error(message = result.message())
        }
    } catch (e: HttpException) {
        println(e)
        Resource.Error(message = e.message())
    } catch (e: Exception){
        println("error: ${e.message}")
        Resource.Error(message = e.message)
    }
}