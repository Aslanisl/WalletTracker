package tech.snowfox.wallettracker.domain.useCase

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import tech.snowfox.wallettracker.data.apiResponse.AuthResponse
import tech.snowfox.wallettracker.data.dto.Data
import tech.snowfox.wallettracker.domain.network.ApiService
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val apiService: ApiService) {

    fun login(email: String?, password: String?): LiveData<Data<AuthResponse>> {
        val mutableLiveData = MediatorLiveData<Data<AuthResponse>>()
        mutableLiveData.postValue(Data.loading())

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            //TODO add message
            mutableLiveData.postValue(Data.error())
            return mutableLiveData
        }

        mutableLiveData.addSource(
                apiService.login(email, password),
                { value ->
                    val body = value?.body
                    when {
                        body?.token == null -> mutableLiveData.postValue(Data.error(body?.message))

                        else -> mutableLiveData.postValue(Data.success(body))
                    }
                }
        )
        return mutableLiveData
    }
}