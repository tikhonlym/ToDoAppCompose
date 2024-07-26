package com.todo.domain

import com.todo.domain.model.Task
import com.todo.domain.model.response.TaskListResponse
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import com.todo.domain.usecase.GetTaskListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetTaskListUnitTest {

    private lateinit var useCase: GetTaskListUseCase
    private val mockRepository: TaskRepository = mockk()
    private val networkStorage: NetworkStorage = mockk()
    private val mockDao: TaskDao = mockk()

    @Before
    fun setUp() {
        useCase = GetTaskListUseCase(
            repository = mockRepository,
            local = mockDao,
            networkStorage = networkStorage
        )
    }

    @Test
    fun `execute should return taskList and upsert local when repository Success`() =
        runTest {
            val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
            val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
            coEvery { mockRepository.getTaskList() } returns ResultWrapper.Success(taskResponse)
            coEvery { mockDao.getTaskList() } returns taskList
            coEvery { mockDao.upsertTasks(taskList) } returns Unit
            coEvery { networkStorage.getRevision() } returns 0
            coEvery { networkStorage.saveSyncNeeded(taskResponse.revision != networkStorage.getRevision()) } returns Unit

            val result = useCase.execute()

            assertTrue(result is UIResultWrapper.Success)
            assertEquals(taskList, (result as UIResultWrapper.Success).value)
            coVerify { mockDao.getTaskList() }
        }

    @Test
    fun `execute should return local task list when repository NetworkError`() =
        runTest {
            val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
            val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
            coEvery { mockRepository.getTaskList() } returns ResultWrapper.NetworkError
            coEvery { mockDao.getTaskList() } returns taskList
            coEvery { mockDao.upsertTasks(taskList) } returns Unit
            coEvery { networkStorage.getRevision() } returns 0
            coEvery { networkStorage.saveSyncNeeded(taskResponse.revision != networkStorage.getRevision()) } returns Unit

            val result = useCase.execute()

            assertTrue(result is UIResultWrapper.SuccessNoInternet)
            assertEquals(taskList, (result as UIResultWrapper.SuccessNoInternet).value)
            coVerify { mockDao.getTaskList() }
        }

    @Test
    fun `execute should return local task list when repository UnSynchronizedDataError`() =
        runTest {
            val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
            val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
            coEvery { mockRepository.getTaskList() } returns ResultWrapper.UnSynchronizedDataError
            coEvery { mockDao.getTaskList() } returns taskList
            coEvery { mockDao.upsertTasks(taskList) } returns Unit
            coEvery { networkStorage.getRevision() } returns 0
            coEvery { networkStorage.saveSyncNeeded(taskResponse.revision != networkStorage.getRevision()) } returns Unit

            val result = useCase.execute()

            assertTrue(result is UIResultWrapper.SuccessWithServerError)
            assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
            coVerify { mockDao.getTaskList() }
        }

    @Test
    fun `execute should return local task list when repository AuthorizationError`() =
        runTest {
            val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
            val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
            coEvery { mockRepository.getTaskList() } returns ResultWrapper.AuthorizationError
            coEvery { mockDao.getTaskList() } returns taskList
            coEvery { mockDao.upsertTasks(taskList) } returns Unit
            coEvery { networkStorage.getRevision() } returns 0
            coEvery { networkStorage.saveSyncNeeded(taskResponse.revision != networkStorage.getRevision()) } returns Unit

            val result = useCase.execute()

            assertTrue(result is UIResultWrapper.SuccessWithServerError)
            assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
            coVerify { mockDao.getTaskList() }
        }

    @Test
    fun `execute should return local task list when repository NoSuchDataError`() =
        runTest {
            val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
            val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
            coEvery { mockRepository.getTaskList() } returns ResultWrapper.NoSuchDataError
            coEvery { mockDao.getTaskList() } returns taskList
            coEvery { mockDao.upsertTasks(taskList) } returns Unit
            coEvery { networkStorage.getRevision() } returns 0
            coEvery { networkStorage.saveSyncNeeded(taskResponse.revision != networkStorage.getRevision()) } returns Unit

            val result = useCase.execute()

            assertTrue(result is UIResultWrapper.SuccessWithServerError)
            assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
            coVerify { mockDao.getTaskList() }
        }

    @Test
    fun `execute should return local task list when repository UnknownSeverError`() =
        runTest {
            val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
            val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
            coEvery { mockRepository.getTaskList() } returns ResultWrapper.UnknownSeverError(123)
            coEvery { mockDao.getTaskList() } returns taskList
            coEvery { mockDao.upsertTasks(taskList) } returns Unit
            coEvery { networkStorage.getRevision() } returns 0
            coEvery { networkStorage.saveSyncNeeded(taskResponse.revision != networkStorage.getRevision()) } returns Unit

            val result = useCase.execute()

            assertTrue(result is UIResultWrapper.SuccessWithServerError)
            assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
            coVerify { mockDao.getTaskList() }
        }

    @Test
    fun `execute should return local task list when repository GenericError`() =
        runTest {
            val taskList = listOf(mockkTask("1"), mockkTask("2"), mockkTask("3"))
            val taskResponse = TaskListResponse(status = "success", list = taskList, revision = 0)
            coEvery { mockRepository.getTaskList() } returns ResultWrapper.GenericError()
            coEvery { mockDao.getTaskList() } returns taskList
            coEvery { mockDao.upsertTasks(taskList) } returns Unit
            coEvery { networkStorage.getRevision() } returns 0
            coEvery { networkStorage.saveSyncNeeded(taskResponse.revision != networkStorage.getRevision()) } returns Unit

            val result = useCase.execute()

            assertTrue(result is UIResultWrapper.SuccessWithServerError)
            assertEquals(taskList, (result as UIResultWrapper.SuccessWithServerError).value)
            coVerify { mockDao.getTaskList() }
        }

    private fun mockkTask(idParam: String): Task {
        return mockk {
            every { id } returns idParam
            every { this@mockk == any<Task>() } answers { (firstArg() as Task).id == idParam }
            every { this@mockk.hashCode() } answers { idParam.hashCode() }
        }
    }
}