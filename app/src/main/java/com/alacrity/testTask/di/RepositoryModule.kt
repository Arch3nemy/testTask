package com.alacrity.testTask.di

import com.alacrity.testTask.repository.Repository
import com.alacrity.testTask.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRepository(impl: RepositoryImpl): Repository

}