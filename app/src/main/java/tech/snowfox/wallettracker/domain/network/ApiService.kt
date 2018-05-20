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
}