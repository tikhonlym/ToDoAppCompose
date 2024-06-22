package com.todo.app.todoappcompose.data.repository.di

import com.todo.app.todoappcompose.data.repository.todo.TodoItemsRepositoryImpl
import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
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
    fun provideTodoRepository(): TodoItemsRepository = TodoItemsRepositoryImpl()

}