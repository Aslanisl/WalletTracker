package tech.snowfox.wallettracker.data.apiResponse

data class AuthResponse(
        val token: String? = null,
        val code: String? = null,
        val message: String? = null
)