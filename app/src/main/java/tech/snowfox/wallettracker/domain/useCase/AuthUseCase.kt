package tech.snowfox.wallettracker.domain.useCase

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import tech.snowfox.wallettracker.data.apiResponse.AuthResponse
import tech.snowfox.wallettracker.data.dto.Data
import tech.snowfox.wallettracker.domain.network.ApiService
import javax.inject.Inject

class AuthUseCase
@Inject constructor(private val apiService: ApiService) {

    fun login(email: String?, password: String?): LiveData<Data<AuthResponse>> {
        val liveData = MediatorLiveData<Data<AuthResponse>>()
        liveData.postValue(Data.loading())

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            //TODO add message
            liveData.postValue(Data.error())
            return liveData
        }

        liveData.addSource(
                apiService.login(email, password),
                { liveData.postValue(it) }
        )
        return liveData
    }

    fun loginSocial(callbackManager: CallbackManager,
                    loginManager: LoginManager): LiveData<Data<AuthResponse>> {
        val liveData = MediatorLiveData<Data<AuthResponse>>()
        liveData.postValue(Data.loading())

        loginFacebook(callbackManager, loginManager, liveData)

        return liveData
    }

    private fun loginFacebook(callbackManager: CallbackManager,
                              loginManager: LoginManager,
                              liveData: MediatorLiveData<Data<AuthResponse>>) {

        val facebookCallback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val token = result?.accessToken?.token
                when (token) {
                    null -> liveData.postValue(Data.error())

                    else -> {
                        liveData.addSource(apiService.loginSocial(token, "facebook"), {
                            liveData.postValue(it)
                        })
                    }
                }
            }

            override fun onCancel() {
                liveData.postValue(Data.error())
            }

            override fun onError(error: FacebookException?) {
                liveData.postValue(Data.error(error?.message))
            }
        }

        loginManager.registerCallback(callbackManager, facebookCallback)
    }
}