package com.todo.domain

import com.todo.domain.model.Task
import com.todo.domain.model.response.TaskResponse
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import com.todo.domain.usecase.GetTaskUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetTaskUnitTest {

    private lateinit var useCase: GetTaskUseCase
    private val mockRepository: TaskRepository = mockk()
    private val mockDao: TaskDao = mockk()

    @Before
    fun setUp() {
        useCase = GetTaskUseCase(repository = mockRepository, local = mockDao)
    }

    @Test
    fun `execute should return success and upsert local when repository Success`() = runTest {
        val taskId = "1"
        val task = mockkTask(taskId)
        val taskResponse = TaskResponse(status = "success", element = task, revision = 0)
        coEvery { mockRepository.getTask(taskId) } returns ResultWrapper.Success(taskResponse)
        coEvery { mockDao.getTask(taskId) } returns task
        coEvery { mockDao.upsertTask(task) } returns Unit

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.Success)
        assertEquals(task, (result as UIResultWrapper.Success).value)
        coVerify { mockDao.upsertTask(task) }
    }

    @Test
    fun `execute should return local task when NetworkError`() = runTest {
        val taskId = "2"
        val task = mockkTask(taskId)
        coEvery { mockRepository.getTask(taskId) } returns ResultWrapper.NetworkError
        coEvery { mockDao.getTask(taskId) } returns task

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.SuccessNoInternet)
        assertEquals(task, (result as UIResultWrapper.SuccessNoInternet).value)
    }

    @Test
    fun `execute should return local task when NoSuchDataError`() = runTest {
        val taskId = "3"
        val task = mockkTask(taskId)
        coEvery { mockRepository.getTask(taskId) } returns ResultWrapper.NoSuchDataError
        coEvery { mockDao.getTask(taskId) } returns task

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    @Test
    fun `execute should return local task when UnSynchronizedDataError`() = runTest {
        val taskId = "3"
        val task = mockkTask(taskId)
        coEvery { mockRepository.getTask(taskId) } returns ResultWrapper.NoSuchDataError
        coEvery { mockDao.getTask(taskId) } returns task

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    @Test
    fun `execute should return local task when AuthorizationError`() = runTest {
        val taskId = "3"
        val task = mockkTask(taskId)
        coEvery { mockRepository.getTask(taskId) } returns ResultWrapper.NoSuchDataError
        coEvery { mockDao.getTask(taskId) } returns task

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    @Test
    fun `execute should return local task when UnknownSeverError`() = runTest {
        val taskId = "3"
        val task = mockkTask(taskId)
        coEvery { mockRepository.getTask(taskId) } returns ResultWrapper.NoSuchDataError
        coEvery { mockDao.getTask(taskId) } returns task

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    @Test
    fun `execute should return local task when GenericError`() = runTest {
        val taskId = "3"
        val task = mockkTask(taskId)
        coEvery { mockRepository.getTask(taskId) } returns ResultWrapper.NoSuchDataError
        coEvery { mockDao.getTask(taskId) } returns task

        val result = useCase.execute(taskId)

        assertTrue(result is UIResultWrapper.SuccessWithServerError)
        assertEquals(task, (result as UIResultWrapper.SuccessWithServerError).value)
    }

    private fun mockkTask(idParam: String): Task {
        return mockk {
            every { id } returns idParam
            every { this@mockk == any<Task>() } answers { (firstArg() as Task).id == idParam }
            every { this@mockk.hashCode() } answers { idParam.hashCode() }
        }
    }
}