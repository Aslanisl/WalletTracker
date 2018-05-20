package tech.snowfox.wallettracker.data.apiResponse

data class AuthResponse(
        val token: String?,
        val code: String?,
        val message: String?
)