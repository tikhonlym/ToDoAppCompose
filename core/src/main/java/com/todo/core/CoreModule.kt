package com.todo.core

import android.content.Context
import com.todo.core.config.theme.ThemeConfig
import com.todo.core.dispatchers.AppDispatchers
import com.todo.core.dispatchers.DispatchersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideThemeConfig(@ApplicationContext context: Context): ThemeConfig {
        return ThemeConfig(context)
    }
}