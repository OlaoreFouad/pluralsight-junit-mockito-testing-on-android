package com.psdemo.todo

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.psdemo.todo.data.Todo
import com.psdemo.todo.data.TodoRepository
import com.psdemo.todo.list.ListViewModel
import com.psdemo.todo.util.TodoTestRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ListViewModelTest {
    private var todos = mutableListOf<Todo>()
    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24

    @get:Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Test
    fun test_allTodosEmpty() {
        val expectedCount = 0
        val repository: TodoRepository = mock()
        whenever(repository.getAllTodos())
            .thenReturn(MutableLiveData(arrayListOf()))
        val viewModel = ListViewModel(repository)

        val allTodos = viewModel.allTodos.value

        Assert.assertNotNull(allTodos)
        Assert.assertEquals(expectedCount, allTodos?.size)

    }

    @Test
    fun test_allTodosSingle() {
        val repository: TodoRepository = mock()
        val expected = 1
        whenever(repository.getAllTodos())
            .thenReturn(
                MutableLiveData(
                    arrayListOf(
                        Todo("Todo Id 2", "Todo 2", now - day, true, now)
                    )
                )
            )

        val viewModel = ListViewModel(repository)
        val actual = viewModel.allTodos.value

        Assert.assertNotNull(actual)
        Assert.assertEquals(expected, actual?.size)
    }

    @Test
    fun test_allTodosMultiple() {
        val repository: TodoRepository = mock()
        val expected = 3
        whenever(repository.getAllTodos())
            .thenReturn(
                MutableLiveData(
                    arrayListOf(
                        Todo("Todo Id 2", "Todo 2", now - day, true, now),
                        Todo("Todo Id 3", "Todo 3", now, false, now),
                        Todo("Todo Id 4", "Todo 4", now, false, now)
                    )
                )
            )

        val viewModel = ListViewModel(repository)
        val actual = viewModel.allTodos.value

        Assert.assertNotNull(actual)
        Assert.assertEquals(expected, actual?.size)
    }

    @Test
    fun test_upcomingTodos() {
        val expectedCount = 1
        val repository: TodoRepository = mock()

        whenever(repository.getUpcomingTodosCount())
            .thenReturn(MutableLiveData(expectedCount))

        val viewModel = ListViewModel(repository)

        val upcomingTodos = viewModel.upcomingTodosCount.value
        Assert.assertNotNull(upcomingTodos)
        Assert.assertEquals(expectedCount, upcomingTodos)

    }

    @Test
    fun test_upcomingTodosMultiple() {
        val expectedCount = 5
        val repository: TodoRepository = mock()

        whenever(repository.getUpcomingTodosCount())
            .thenReturn(MutableLiveData(expectedCount))

        val viewModel = ListViewModel(repository)

        val upcomingTodos = viewModel.upcomingTodosCount.value
        Assert.assertNotNull(upcomingTodos)
        Assert.assertEquals(expectedCount, upcomingTodos)

    }


    @Test
    fun test_toggleTodo() {
        val id = "fake"
        val repository: TodoRepository = mock()
        val viewModel = ListViewModel(repository)

        expectedException.expect(NotImplementedError::class.java)

        viewModel.toggleTodo(id)
        // TODO: work on logic for toggling todos
        // TODO: work on logic for verifying if todos were added, with title, without, with duedate and without

    }

}