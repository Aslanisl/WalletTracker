package tech.snowfox.wallettracker.domain.di.component

import dagger.Component
import tech.snowfox.wallettracker.domain.di.module.AppModule
import tech.snowfox.wallettracker.domain.di.module.UseCaseModule
import tech.snowfox.wallettracker.domain.di.module.ViewModelModule
import tech.snowfox.wallettracker.ui.feature.login.activity.LoginActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, UseCaseModule::class, AppModule::class])
interface AppComponent {

    fun inject(loginActivity: LoginActivity)
}