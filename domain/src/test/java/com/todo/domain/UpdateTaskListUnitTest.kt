package com.todo.domain

import com.todo.domain.model.Task
import com.todo.domain.model.response.TaskListResponse
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import com.todo.domain.usecase.UpdateTaskListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateTaskListUnitTest {

    private lateinit var useCase: UpdateTaskListUseCase
    private val mockRepository: TaskRepository = mockk()
    private val networkStorage: NetworkStorage = mockk()
    private val mockDao: TaskDao = mockk()

    @Before
    fun setUp() {
        useCase = UpdateTaskListUseCase(
            repository = mockRepository,
            local = mockDao,
            networkStorage = networkStorage
        )
    }

    @Test
    fun `execute should return merged taskList and replace local when repository Success`() =
        runTest {
            val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
            val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
            coEvery {
                mockRepository.updateTaskList(taskList, taskResponse.revision)
            } returns ResultWrapper.Success(taskResponse)
            coEvery { networkStorage.getRevision() } returns 0
            coEvery { mockDao.getTaskList() } returns taskList
            coEvery { mockDao.replaceTable(taskList) } returns Unit
            coEvery { networkStorage.saveRevision(taskResponse.revision) } returns Unit
            coEvery { networkStorage.saveSyncNeeded(false) } returns Unit

            val result = useCase.execute()

            assertTrue(result is UIResultWrapper.Success)
            assertEquals(taskList, (result as UIResultWrapper.Success).value)
            coVerify { networkStorage.saveRevision(taskResponse.revision) }
        }

    @Test
    fun `execute should return local when repository NetworkError`() = runTest {
        val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
        val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
        coEvery {
            mockRepository.updateTaskList(taskList, taskResponse.revision)
        } returns ResultWrapper.NetworkError
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { mockDao.getTaskList() } returns taskList

        val result = useCase.execute()

        assertTrue(result is UIResultWrapper.SuccessNoInternet)
        assertEquals(taskList, (result as UIResultWrapper.SuccessNoInternet).value)
    }

    @Test
    fun `execute should return local when repository UnSynchronizedDataError`() = runTest {
        val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
        val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
        coEvery {
            mockRepository.updateTaskList(taskList, taskResponse.revision)
        } returns ResultWrapper.UnSynchronizedDataError
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { mockDao.getTaskList() } returns taskList

        val result = useCase.execute()

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    @Test
    fun `execute should return local when repository AuthorizationError`() = runTest {
        val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
        val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
        coEvery {
            mockRepository.updateTaskList(taskList, taskResponse.revision)
        } returns ResultWrapper.AuthorizationError
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { mockDao.getTaskList() } returns taskList

        val result = useCase.execute()

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    @Test
    fun `execute should return local when repository NoSuchDataError`() = runTest {
        val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
        val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
        coEvery {
            mockRepository.updateTaskList(taskList, taskResponse.revision)
        } returns ResultWrapper.NoSuchDataError
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { mockDao.getTaskList() } returns taskList

        val result = useCase.execute()

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    @Test
    fun `execute should return local when repository UnknownSeverError`() = runTest {
        val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
        val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
        coEvery {
            mockRepository.updateTaskList(taskList, taskResponse.revision)
        } returns ResultWrapper.UnknownSeverError(code = 123)
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { mockDao.getTaskList() } returns taskList

        val result = useCase.execute()

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    @Test
    fun `execute should return local when repository GenericError`() = runTest {
        val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
        val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
        coEvery {
            mockRepository.updateTaskList(taskList, taskResponse.revision)
        } returns ResultWrapper.GenericError()
        coEvery { networkStorage.getRevision() } returns 0
        coEvery { mockDao.getTaskList() } returns taskList

        val result = useCase.execute()

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    private fun mockkTask(idParam: String): Task {
        return mockk {
            every { id } returns idParam
            every { this@mockk == any<Task>() } answers { (firstArg() as Task).id == idParam }
            every { this@mockk.hashCode() } answers { idParam.hashCode() }
        }
    }
}