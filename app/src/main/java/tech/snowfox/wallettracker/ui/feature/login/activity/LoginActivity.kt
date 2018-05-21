package tech.snowfox.wallettracker.ui.feature.login.activity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import tech.snowfox.wallettracker.R
import tech.snowfox.wallettracker.data.apiResponse.AuthResponse
import tech.snowfox.wallettracker.data.dto.Data
import tech.snowfox.wallettracker.domain.di.component.AppComponent
import tech.snowfox.wallettracker.domain.useCase.AuthUseCase
import tech.snowfox.wallettracker.extension.getViewModel
import tech.snowfox.wallettracker.ui.base.activity.BaseActivity
import tech.snowfox.wallettracker.ui.feature.login.model.LoginViewModel

class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private var observer: Observer<Data<AuthResponse>>? = null

    private var callbackManager: CallbackManager? = null

    override fun injectDI(appComponent: AppComponent) = appComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = getViewModel(viewModelFactory)
        initObserver()

        loginButton.setOnClickListener {
            loginUser(loginViewModel.login(loginEdit.text, passwordEdit.text))
        }

        loginFacebook.setOnClickListener {
            loginFacebook()
        }
    }

    private fun initObserver() {
        observer = Observer { data ->
            if (data == null) return@Observer
            when (data.status) {
                Data.Status.LOADING -> loginButton.isEnabled = false
                Data.Status.SUCCESS -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    loginButton.isEnabled = true
                }
                Data.Status.FAILURE -> {
                    data.message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
                    loginButton.isEnabled = true
                }
            }
        }
    }

    private fun loginUser(liveData: LiveData<Data<AuthResponse>>) {
        observer?.let {
            liveData.observe(this, it)
        }
    }

    private fun loginFacebook() {
        if (this.callbackManager == null){
            this.callbackManager = CallbackManager.Factory.create()
        }
        val callbackManager = this.callbackManager ?: return

        val loginManager = LoginManager.getInstance()
        loginUser(loginViewModel.authSocial(callbackManager, loginManager))
        loginManager.logInWithReadPermissions(this, listOf("email"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}
