package tech.snowfox.wallettracker.domain.di.module

import dagger.Module
import dagger.Provides
import tech.snowfox.wallettracker.domain.network.ApiService
import tech.snowfox.wallettracker.domain.useCase.AuthUseCase

@Module
class UseCaseModule {

    @Provides
    fun provideAuthUseCase(apiService: ApiService) = AuthUseCase(apiService)
}