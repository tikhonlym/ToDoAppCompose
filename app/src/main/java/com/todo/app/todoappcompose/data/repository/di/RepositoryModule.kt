package com.todo.app.todoappcompose.data.repository.di

import com.todo.app.todoappcompose.app.dispatchers.AppDispatchers
import com.todo.app.todoappcompose.data.repository.todo.TodoItemsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideTodoRepository(dispatchers: AppDispatchers): TodoItemsRepositoryImpl =
        TodoItemsRepositoryImpl(
            dispatcher = dispatchers
        )

}