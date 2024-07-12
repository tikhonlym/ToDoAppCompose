package com.todo.domain.usecase.sync

import com.todo.domain.NetworkStorage
import javax.inject.Inject

class UpdateSyncNeededUseCase @Inject constructor(
    private val networkStorage: NetworkStorage,
) {

    fun execute(state: Boolean) {
        networkStorage.saveSyncNeeded(state)
    }
}