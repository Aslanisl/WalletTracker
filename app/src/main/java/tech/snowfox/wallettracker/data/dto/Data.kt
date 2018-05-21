package tech.snowfox.wallettracker.data.dto

import retrofit2.Response
import retrofit2.Retrofit
import tech.snowfox.wallettracker.domain.network.utils.ErrorUtils

class Data<out T>(
        val status: Status,
        val body: T? = null,
        val message: String? = null,
        val code: Int? = null
) {

    enum class Status {
        LOADING,
        SUCCESS,
        FAILURE
    }

    companion object {
        fun loading() = Data(Status.LOADING, null)
        fun <T> success(body: T?) = Data(Status.SUCCESS, body)
        fun error(message: String? = null, code: Int? = null): Data<Nothing> {
            return Data(Status.FAILURE, null, message = message, code = code)
        }

        fun <T> create(retrofit: Retrofit, response: Response<T>?): Data<T> {
            if (response?.isSuccessful == true) {
                return success(response.body())
            }

            val error = ErrorUtils.parseError(retrofit, response)

            return error(error.message)
        }
    }

    fun isLoading() = status == Status.LOADING
}