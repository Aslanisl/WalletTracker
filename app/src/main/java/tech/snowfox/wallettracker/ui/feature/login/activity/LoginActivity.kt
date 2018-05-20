package tech.snowfox.wallettracker.ui.feature.login.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import tech.snowfox.wallettracker.R
import tech.snowfox.wallettracker.data.apiResponse.AuthResponse
import tech.snowfox.wallettracker.data.dto.Data
import tech.snowfox.wallettracker.domain.di.component.AppComponent
import tech.snowfox.wallettracker.extension.getViewModel
import tech.snowfox.wallettracker.ui.base.activity.BaseActivity
import tech.snowfox.wallettracker.ui.feature.login.model.LoginViewModel

class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun injectDI(appComponent: AppComponent) = appComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = getViewModel(viewModelFactory)

        loginButton.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        val observer = Observer<Data<AuthResponse>> { data ->
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

        loginViewModel.login(loginEdit.text, passwordEdit.text).observe(this, observer)
    }
}
