package com.todo.data

import android.content.Context
import androidx.room.Room
import com.todo.core.dispatchers.AppDispatchers
import com.todo.data.repository.task.TaskApiCalls
import com.todo.data.repository.task.TaskRepositoryImpl
import com.todo.data.retrofit.ApiFactory
import com.todo.data.db.TaskDatabase
import com.todo.data.storage.NetworkStorageImpl
import com.todo.domain.NetworkStorage
import com.todo.domain.TaskDao
import com.todo.domain.TaskRepository
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
    fun provideTaskRepository(
        dispatchers: AppDispatchers,
        api: TaskApiCalls,
    ): TaskRepository {
        return TaskRepositoryImpl(
            dispatcher = dispatchers,
            api = api
        )
    }

    @Singleton
    @Provides
    fun provideTaskApi(): TaskApiCalls {
        return ApiFactory.todoApi
    }

    @Singleton
    @Provides
    fun provideNetworkStorage(@ApplicationContext context: Context): NetworkStorage {
        return NetworkStorageImpl(context = context)
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