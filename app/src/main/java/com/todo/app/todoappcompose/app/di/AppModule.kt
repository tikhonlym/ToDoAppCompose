package com.todo.app.todoappcompose.app.di

import android.app.Application
import com.todo.app.todoappcompose.app.ToDoApp
import com.todo.app.todoappcompose.app.dispatchers.AppDispatchers
import com.todo.app.todoappcompose.app.dispatchers.DispatchersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return ToDoApp()
    }

    @Provides
    @Singleton
    fun provideDispatchers(): AppDispatchers {
        return DispatchersImpl()
    }
}