package com.todo.app.todoappcompose.data

import android.content.Context
import androidx.room.Room
import com.todo.app.todoappcompose.app.dispatchers.AppDispatchers
import com.todo.app.todoappcompose.data.repository.TodoApiCalls
import com.todo.app.todoappcompose.data.repository.TodoRepositoryImpl
import com.todo.app.todoappcompose.data.retrofit.ApiFactory
import com.todo.app.todoappcompose.data.room.TaskDao
import com.todo.app.todoappcompose.data.room.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineName
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideTodoRepository(
        dispatchers: AppDispatchers,
        api: TodoApiCalls,
    ): TodoRepositoryImpl {
        return TodoRepositoryImpl(
            dispatcher = dispatchers,
            api = api
        )
    }

    @Singleton
    @Provides
    fun provideTodoApi(): TodoApiCalls {
        return ApiFactory.todoApi
    }

    @Singleton
    @Provides
    fun provideAppUserStorage(@ApplicationContext context: Context): AppUserStorage {
        return AppUserStorage(context = context)
    }

    @Singleton
    @Provides
    fun provideTaskDataBaseDao(
        @ApplicationContext context: Context,
        dispatchers: AppDispatchers,
    ): TaskDao {
        val dataBase = Room.databaseBuilder(
            context = context,
            klass = TaskDatabase::class.java,
            name = TASK_DATABASE
        )
            .setQueryCoroutineContext(dispatchers.io + CoroutineName("Room coroutines"))
            .build()
        return dataBase.dao
    }

    companion object {
        private const val TASK_DATABASE = "task.db"
    }
}