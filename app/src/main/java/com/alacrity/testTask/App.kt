package com.alacrity.testTask

import android.app.Application
import com.alacrity.testTask.di.ApiModule
import com.alacrity.testTask.di.AppComponent
import com.alacrity.testTask.di.AppModule
import com.alacrity.testTask.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    companion object {

        const val TARGET_URL = "https://s3-us-west-2.amazonaws.com/androidexam/"

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