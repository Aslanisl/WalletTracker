package tech.snowfox.wallettracker.domain.network

import android.arch.lifecycle.LiveData
import retrofit2.http.POST
import retrofit2.http.Query
import tech.snowfox.wallettracker.data.apiResponse.AuthResponse
import tech.snowfox.wallettracker.data.dto.Data

interface ApiService {

    @POST("users/login")
    fun login(
            @Query("email") email: String,
            @Query("password") password: String
    ): LiveData<Data<AuthResponse>>

    @POST("users/social_auth")
    fun loginSocial(
            @Query("access_token") token: String,
            @Query("social_type") socialType: String
    ): LiveData<Data<AuthResponse>>
}