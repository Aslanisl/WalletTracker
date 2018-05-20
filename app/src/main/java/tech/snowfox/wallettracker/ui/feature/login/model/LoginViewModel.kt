package tech.snowfox.wallettracker.ui.feature.login.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import tech.snowfox.wallettracker.data.apiResponse.AuthResponse
import tech.snowfox.wallettracker.data.dto.Data
import tech.snowfox.wallettracker.domain.useCase.AuthUseCase
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {

    fun login(login: String?, password: String?) : LiveData<Data<AuthResponse>> {
        return authUseCase.login(login, password)
    }

    fun authSocial() {
        //TODO add social auth
    }
}