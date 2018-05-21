package tech.snowfox.wallettracker.domain.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import tech.snowfox.wallettracker.domain.network.ApiBuilder
import tech.snowfox.wallettracker.domain.network.ApiService
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideApi(context: Context) = ApiBuilder.build(context)
}