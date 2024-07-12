package com.todo.domain.usecase.sync

import com.todo.domain.NetworkStorage
import javax.inject.Inject

class GetSyncNeededUseCase @Inject constructor(
    private val networkStorage: NetworkStorage,
) {

    fun execute() = networkStorage.getSyncNeeded()
}