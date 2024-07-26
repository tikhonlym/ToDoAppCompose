package com.todo.domain

import com.todo.domain.model.Task
import com.todo.domain.model.response.TaskResponse
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import com.todo.domain.usecase.DeleteTaskUseCase
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


class DeleteTaskUnitTest {

    private lateinit var useCase: DeleteTaskUseCase
    private val mockRepository: TaskRepository = mockk()
    private val mockDao: TaskDao = mockk()
    private val mockNetworkStorage: NetworkStorage = mockk()

    @Before
    fun setUp() {
        useCase = DeleteTaskUseCase(
            repository = mockRepository,
            local = mockDao,
            networkStorage = mockNetworkStorage
        )
        coEvery { mockDao.deleteTaskById(any()) } just Runs
    }

    @Test
    fun `execute should handle success correctly`() = runTest {
        val taskId = "1"
        val task = mockkTask(taskId)
        val revision = 1
        val taskResponse = TaskResponse("success ", task, revision)
        coEvery { mockNetworkStorage.getRevision() } returns revision
        coEvery { mockRepository.deleteTask(taskId, revision) } returns ResultWrapper.Success(
            taskResponse
        )
        coEvery { mockNetworkStorage.saveRevision(revision) } just Runs
        coEvery { mockNetworkStorage.saveSyncNeeded(false) } just Runs

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.Success)
        assertEquals(task, (result as UIResultWrapper.Success).value)
        coVerify { mockDao.deleteTaskById(taskId) }
        coVerify { mockNetworkStorage.saveRevision(revision) }
        coVerify { mockNetworkStorage.saveSyncNeeded(false) }
    }

    @Test
    fun `execute should handle network error correctly`() = runTest {
        val taskId = "2"
        val task = mockkTask(taskId)
        coEvery { mockNetworkStorage.getRevision() } returns 0
        coEvery { mockRepository.deleteTask(taskId, 0) } returns ResultWrapper.NetworkError
        coEvery { mockNetworkStorage.saveSyncNeeded(true) } just Runs
        coEvery { mockDao.getTask(taskId) } returns task

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.SuccessNoInternet)
        assertEquals(task, (result as UIResultWrapper.SuccessNoInternet).value)
        coVerify { mockDao.deleteTaskById(taskId) }
    }

    @Test
    fun `execute should handle unsynchronized data error correctly`() = runTest {
        val taskId = "3"
        val task = mockkTask(taskId)
        coEvery { mockNetworkStorage.getRevision() } returns 0
        coEvery {
            mockRepository.deleteTask(
                taskId,
                0
            )
        } returns ResultWrapper.UnSynchronizedDataError
        coEvery { mockNetworkStorage.saveSyncNeeded(true) } just Runs
        coEvery { mockDao.getTask(taskId) } returns task

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.SuccessSynchronizationNeeded)
        assertEquals(task, (result as UIResultWrapper.SuccessSynchronizationNeeded).value)
        coVerify { mockDao.deleteTaskById(taskId) }
    }

    @Test
    fun `execute should handle other errors correctly`() = runTest {
        val taskId = "4"
        val task = mockkTask(taskId)
        coEvery { mockNetworkStorage.getRevision() } returns 0
        coEvery { mockRepository.deleteTask(taskId, 0) } returns ResultWrapper.GenericError(
            500,
            "Server error"
        )
        coEvery { mockNetworkStorage.saveSyncNeeded(true) } just Runs
        coEvery { mockDao.getTask(taskId) } returns task
        val result = useCase.execute(taskId)
        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
        coVerify { mockDao.deleteTaskById(taskId) }
    }

    private fun mockkTask(idParam: String): Task {
        return mockk {
            every { id } returns idParam
            every { this@mockk == any<Task>() } answers { (firstArg() as Task).id == idParam }
            every { this@mockk.hashCode() } answers { idParam.hashCode() }
        }
    }
}