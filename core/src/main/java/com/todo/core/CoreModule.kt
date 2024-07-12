package com.todo.core

import com.todo.core.dispatchers.AppDispatchers
import com.todo.core.dispatchers.DispatchersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideDispatchers(): AppDispatchers {
        return DispatchersImpl()
    }
}