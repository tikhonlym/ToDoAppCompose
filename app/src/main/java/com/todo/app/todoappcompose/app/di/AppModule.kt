package com.todo.app.todoappcompose.app.di

import com.todo.app.todoappcompose.app.ToDoApp
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
    fun provideApplication(): ToDoApp {
        return ToDoApp()
    }
}