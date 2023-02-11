package com.alacrity.testTask.di

import com.alacrity.testTask.App
import com.alacrity.testTask.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, UseCaseModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(app: App)

}