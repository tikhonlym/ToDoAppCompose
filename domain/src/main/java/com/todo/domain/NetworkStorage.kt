package com.todo.domain

interface NetworkStorage {
    fun saveRevision(revision: Int)
    fun getRevision(): Int 
    fun getSyncNeeded(): Boolean
    fun saveSyncNeeded(status: Boolean)
} 