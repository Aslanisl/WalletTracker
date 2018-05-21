package tech.snowfox.wallettracker.domain

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import tech.snowfox.wallettracker.domain.di.component.DaggerAppComponent
import tech.snowfox.wallettracker.domain.di.module.AppModule

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        AppEventsLogger.activateApp(this)
    }
}