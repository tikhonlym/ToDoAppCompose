package com.todo.domain

import com.todo.domain.model.Task
import com.todo.domain.model.response.TaskResponse
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import com.todo.domain.usecase.UpdateTaskUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateTaskUnitTest {

    private lateinit var useCase: UpdateTaskUseCase
    private val mockRepository: TaskRepository = mockk()
    private val networkStorage: NetworkStorage = mockk()
    private val mockDao: TaskDao = mockk()

    @Before
    fun setUp() {
        useCase = UpdateTaskUseCase(
            repository = mockRepository,
            local = mockDao,
            networkStorage = networkStorage
        )
    }

    @Test
    fun `execute should return saved task and upsert local when repository Success`() = runTest {
        val taskId = "1"
        val task = mockkTask(taskId)
        val taskResponse = TaskResponse(status = "success", element = task, revision = 0)
        coEvery {
            mockRepository.updateTask(task, taskResponse.revision)
        } returns ResultWrapper.Success(taskResponse)
        coEvery { mockDao.upsertTask(task) } returns Unit
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { networkStorage.saveRevision(taskResponse.revision) } returns Unit

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.Success)
        assertEquals(task, (result as UIResultWrapper.Success).value)
        coVerify { networkStorage.saveRevision(taskResponse.revision) }
        coVerify { mockDao.upsertTask(task) }
    }

    @Test
    fun `execute should return local saved task when repository NetworkError`() = runTest {
        val taskId = "2"
        val task = mockkTask(taskId)
        val taskResponse = TaskResponse(status = "success", element = task, revision = 0)
        coEvery {
            mockRepository.updateTask(task, taskResponse.revision)
        } returns ResultWrapper.NetworkError
        coEvery { mockDao.upsertTask(task) } returns Unit
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { networkStorage.saveSyncNeeded(true) } returns Unit

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.SuccessNoInternet)
        assertEquals(task, (result as UIResultWrapper.SuccessNoInternet).value)
        coVerify { mockDao.upsertTask(task) }
    }

    @Test
    fun `execute should return local saved task when repository UnknownSeverError`() = runTest {
        val taskId = "3"
        val task = mockkTask(taskId)
        val taskResponse = TaskResponse(status = "success", element = task, revision = 0)
        coEvery {
            mockRepository.updateTask(task, taskResponse.revision)
        } returns ResultWrapper.UnknownSeverError(123)
        coEvery { mockDao.upsertTask(task) } returns Unit
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { networkStorage.saveSyncNeeded(true) } returns Unit

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.SuccessSynchronizationNeeded)
        assertEquals(task, (result as UIResultWrapper.SuccessSynchronizationNeeded).value)
        coVerify { mockDao.upsertTask(task) }
    }

    @Test
    fun `execute should return local saved task when repository UnSynchronizedDataError`() =
        runTest {
            val taskId = "4"
            val task = mockkTask(taskId)
            val taskResponse = TaskResponse(status = "success", element = task, revision = 0)
            coEvery {
                mockRepository.updateTask(task, taskResponse.revision)
            } returns ResultWrapper.UnSynchronizedDataError
            coEvery { mockDao.upsertTask(task) } returns Unit
            coEvery { networkStorage.getRevision() } returns 0
            coEvery { networkStorage.saveSyncNeeded(true) } returns Unit

            val result = useCase.execute(task)

            assertTrue(result is UIResultWrapper.SuccessWithServerError)
            assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
            coVerify { mockDao.upsertTask(task) }
        }

    @Test
    fun `execute should return local saved task when repository AuthorizationError`() = runTest {
        val taskId = "5"
        val task = mockkTask(taskId)
        val taskResponse = TaskResponse(status = "success", element = task, revision = 0)
        coEvery {
            mockRepository.updateTask(task, taskResponse.revision)
        } returns ResultWrapper.AuthorizationError
        coEvery { mockDao.upsertTask(task) } returns Unit
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { networkStorage.saveSyncNeeded(true) } returns Unit

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
        coVerify { mockDao.upsertTask(task) }
    }

    @Test
    fun `execute should return local saved task when repository NoSuchDataError`() = runTest {
        val taskId = "6"
        val task = mockkTask(taskId)
        val taskResponse = TaskResponse(status = "success", element = task, revision = 0)
        coEvery {
            mockRepository.updateTask(task, taskResponse.revision)
        } returns ResultWrapper.NoSuchDataError
        coEvery { mockDao.upsertTask(task) } returns Unit
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { networkStorage.saveSyncNeeded(true) } returns Unit

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
        coVerify { mockDao.upsertTask(task) }
    }

    @Test
    fun `execute should return local saved task when repository GenericError`() = runTest {
        val taskId = "7"
        val task = mockkTask(taskId)
        val taskResponse = TaskResponse(status = "success", element = task, revision = 0)
        coEvery {
            mockRepository.updateTask(task, taskResponse.revision)
        } returns ResultWrapper.GenericError()
        coEvery { mockDao.upsertTask(task) } returns Unit
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { networkStorage.saveSyncNeeded(true) } returns Unit

        val result = useCase.execute(task)

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
        coVerify { mockDao.upsertTask(task) }
    }

    private fun mockkTask(idParam: String): Task {
        return mockk {
            every { id } returns idParam
            every { this@mockk == any<Task>() } answers { (firstArg() as Task).id == idParam }
            every { this@mockk.hashCode() } answers { idParam.hashCode() }
        }
    }
}