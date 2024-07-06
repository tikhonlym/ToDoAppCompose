package com.todo.app.todoappcompose.data

import android.content.Context
import android.content.SharedPreferences

/** It is responsible for the user's local state, the state of the current version,
 *  if necessary, it will be updated */

class AppUserStorage(
    context: Context,
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(APP_USER_PREFS, Context.MODE_PRIVATE)

    fun saveRevision(revision: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_REVISION, revision)
        editor.apply()
    }

    fun getRevision(): Int {
        return sharedPreferences.getInt(KEY_REVISION, 0)
    }

    fun getSyncNeeded(): Boolean {
        return sharedPreferences.getBoolean(KEY_REFRESH, false)
    }

    fun saveSyncNeeded(status: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_REFRESH, status)
        editor.apply()
    }

    companion object {
        private const val APP_USER_PREFS = "AppUserPrefs"
        private const val KEY_REVISION = "revision"
        private const val KEY_REFRESH = "refresh"
    }
}