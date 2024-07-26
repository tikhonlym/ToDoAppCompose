package com.todo.data

import android.content.Context
import android.content.SharedPreferences
import com.todo.data.storage.NetworkStorageImpl
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NetworkImplUnitTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    private lateinit var mockEditor: SharedPreferences.Editor

    private lateinit var networkStorage: NetworkStorageImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(mockContext.getSharedPreferences(APP_USER_PREFS, Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        networkStorage = NetworkStorageImpl(mockContext)
    }

    @Test
    fun `test saveRevision`() {
        val revision = 10

        networkStorage.saveRevision(revision)

        verify(mockEditor).putInt(KEY_REVISION, revision)
        verify(mockEditor).apply()
    }

    @Test
    fun `test getRevision`() {
        val expectedRevision = 5
        `when`(mockSharedPreferences.getInt(KEY_REVISION, 0)).thenReturn(expectedRevision)

        val revision = networkStorage.getRevision()

        assertEquals(expectedRevision, revision)
    }

    @Test
    fun `test saveSyncNeeded`() {
        val syncNeeded = true

        networkStorage.saveSyncNeeded(syncNeeded)

        verify(mockEditor).putBoolean(KEY_REFRESH, syncNeeded)
        verify(mockEditor).apply()
    }

    @Test
    fun `test getSyncNeeded`() {
        val expectedSyncNeeded = true
        `when`(mockSharedPreferences.getBoolean(KEY_REFRESH, false)).thenReturn(expectedSyncNeeded)

        val syncNeeded = networkStorage.getSyncNeeded()

        assertEquals(expectedSyncNeeded, syncNeeded)
    }

    companion object {
        private const val APP_USER_PREFS = "AppUserPrefs"
        private const val KEY_REVISION = "revision"
        private const val KEY_REFRESH = "refresh"
    }
}