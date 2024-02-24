package sk.doxxbet.doxxbetui.wpbo_assignment

import android.content.Context
import com.chuckerteam.chucker.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun getHttpClient(@ApplicationContext context: Context) : OkHttpClient {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return if (BuildConfig.DEBUG) {
            // logging
            OkHttpClient.Builder()
                .cookieJar(JavaNetCookieJar(cookieManager))
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor(ChuckerInterceptor(context))
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .callTimeout(15, TimeUnit.SECONDS)
                .build()

        } else {
            OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .callTimeout(15, TimeUnit.SECONDS)
                .build()
        }
    }
    @Provides
    @Singleton
    fun getRetrofit(moshi: Moshi,okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .baseUrl("https://reqres.in/")
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun getMoshi() : Moshi
            = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun getUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun getUserRepository(userService: UserService): UserRepository =
        UserRepository(userService)
}

suspend fun <T> executeRequest(
    request : suspend () -> Response<T>,
) : Resource<T> {
    return try {

        val result = request.invoke()
        println("${result} 77777771")
        val body = result.body()
        println("${body} 77777771")

        if (result.isSuccessful && body != null) {
            Resource.Success(body)
        } else {
            result.errorBody()?.source()?.let { bs ->
                val parsedBody = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(ErrorResponse::class.java).fromJson(bs)
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