package tech.snowfox.wallettracker.ui.base.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import tech.snowfox.wallettracker.domain.App
import tech.snowfox.wallettracker.domain.di.component.AppComponent
import tech.snowfox.wallettracker.domain.di.factory.ViewModelFactory
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject protected lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDI((application as App).appComponent)
    }

    abstract fun injectDI(appComponent: AppComponent)
}