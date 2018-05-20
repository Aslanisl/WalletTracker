package tech.snowfox.wallettracker.data.dto

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
        fun <T> success(body: T) = Data(Status.LOADING, body)
        fun error(message: String? = null, code: Int? = null): Data<Nothing> {
            return Data(Status.FAILURE, null, message = message, code = code)
        }
    }

    fun isLoading() = status == Status.LOADING
}