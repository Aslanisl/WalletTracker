package tech.snowfox.wallettracker.domain.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import tech.snowfox.wallettracker.domain.di.factory.ViewModelFactory
import tech.snowfox.wallettracker.domain.di.factory.ViewModelKey
import tech.snowfox.wallettracker.ui.feature.login.model.LoginViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel
}