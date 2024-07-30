package com.todo.domain

import com.todo.domain.model.Task
import com.todo.domain.model.response.TaskResponse
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import com.todo.domain.usecase.CreateTaskUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateTaskUnitTest {

    private lateinit var useCase: CreateTaskUseCase
    private val mockRepository: TaskRepository = mockk()
    private val mockLocalDao: TaskDao = mockk()
    private val mockNetworkStorage: NetworkStorage = mockk()
    private val task = mockkTask("1")

    @Before
    fun setUp() {
        useCase = CreateTaskUseCase(
            repository = mockRepository,
            local = mockLocalDao,
            networkStorage = mockNetworkStorage
        )
        coEvery { mockLocalDao.upsertTask(any()) } just Runs
    }

    @Test
    fun `execute should handle success correctly`() = runTest {
        val revision = 1
        coEvery { mockNetworkStorage.getRevision() } returns revision
        coEvery { mockRepository.createTask(task, revision) } returns ResultWrapper.Success(
            TaskResponse("success", task, revision)
        )
        coEvery { mockNetworkStorage.saveRevision(revision) } just Runs
        coEvery { mockNetworkStorage.saveSyncNeeded(false) } just Runs

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.Success)
        assertEquals(task, (result as UIResultWrapper.Success).value)
        coVerify { mockLocalDao.upsertTask(task) }
        coVerify { mockNetworkStorage.saveRevision(revision) }
        coVerify { mockNetworkStorage.saveSyncNeeded(false) }
    }

    @Test
    fun `execute should handle network error correctly`() = runTest {
        coEvery { mockNetworkStorage.getRevision() } returns 0
        coEvery { mockRepository.createTask(task, 0) } returns ResultWrapper.NetworkError
        coEvery { mockNetworkStorage.saveSyncNeeded(true) } just Runs

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.SuccessNoInternet)
        assertEquals(task, (result as UIResultWrapper.SuccessNoInternet).value)
        coVerify { mockLocalDao.upsertTask(task) }
        coVerify { mockNetworkStorage.saveSyncNeeded(true) }
    }

    @Test
    fun `execute should handle unsynchronized data error correctly`() = runTest {
        coEvery { mockNetworkStorage.getRevision() } returns 0
        coEvery { mockRepository.createTask(task, 0) } returns ResultWrapper.UnSynchronizedDataError
        coEvery { mockNetworkStorage.saveSyncNeeded(true) } just Runs

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.SuccessSynchronizationNeeded)
        assertEquals(task, (result as UIResultWrapper.SuccessSynchronizationNeeded).value)
        coVerify { mockLocalDao.upsertTask(task) }
        coVerify { mockNetworkStorage.saveSyncNeeded(true) }
    }

    @Test
    fun `execute should handle other errors correctly`() = runTest {
        coEvery { mockNetworkStorage.getRevision() } returns 0
        coEvery { mockRepository.createTask(task, 0) } returns ResultWrapper.GenericError(
            500,
            "Server error"
        )
        coEvery { mockNetworkStorage.saveSyncNeeded(true) } just Runs

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
        coVerify { mockLocalDao.upsertTask(task) }
        coVerify { mockNetworkStorage.saveSyncNeeded(true) }
    }

    private fun mockkTask(idParam: String): Task {
        return mockk {
            every { id } returns idParam
            every { this@mockk == any<Task>() } answers { (firstArg() as Task).id == idParam }
            every { this@mockk.hashCode() } answers { idParam.hashCode() }
        }
    }
}