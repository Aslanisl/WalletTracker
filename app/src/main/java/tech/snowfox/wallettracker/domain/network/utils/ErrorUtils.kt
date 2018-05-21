package tech.snowfox.wallettracker.domain.network.utils

import retrofit2.Response
import retrofit2.Retrofit
import tech.snowfox.wallettracker.data.apiResponse.ApiError

object ErrorUtils {

    fun <T> parseError(retrofit: Retrofit, response: Response<T>?): ApiError {
        response ?: return ApiError()

        val converter = retrofit
                .responseBodyConverter<T>(ApiError::class.java, arrayOfNulls<Annotation>(0))

        val errorBody = response.errorBody()
        errorBody ?: return ApiError()

        try {
            return converter?.convert(errorBody) as ApiError
        } catch (e: Exception) {
            return ApiError()
        }
    }
}