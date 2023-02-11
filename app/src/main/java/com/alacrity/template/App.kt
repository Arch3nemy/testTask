package com.alacrity.template

import android.app.Application
import com.alacrity.template.di.ApiModule
import com.alacrity.template.di.AppComponent
import com.alacrity.template.di.AppModule
import com.alacrity.template.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    companion object {

        const val TARGET_URL = "https://jsonplaceholder.typicode.com/"

        lateinit var appComponent: AppComponent

    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent
            .builder()
            .apiModule(ApiModule(TARGET_URL))
            .appModule(AppModule(this))
            .build()
            .apply { inject(this@App) }
    }

}