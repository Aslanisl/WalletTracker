package tech.snowfox.wallettracker.domain.di.module

import dagger.Module
import dagger.Provides
import tech.snowfox.wallettracker.domain.network.ApiBuilder
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApi() = ApiBuilder.build()
}