package tech.snowfox.wallettracker.domain

import android.app.Application
import tech.snowfox.wallettracker.domain.di.component.DaggerAppComponent

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent.builder().build()
    }
}