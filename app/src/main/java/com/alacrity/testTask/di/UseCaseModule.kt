package com.alacrity.testTask.di

import com.alacrity.testTask.use_cases.GetSimpleResponseUseCase
import com.alacrity.testTask.use_cases.GetSimpleResponseUseCaseImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface UseCaseModule {

    @Binds
    @Singleton
    fun bindNewMessageReceivedUseCase(impl: GetSimpleResponseUseCaseImpl): GetSimpleResponseUseCase

}