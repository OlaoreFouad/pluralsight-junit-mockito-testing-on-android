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

    @get:Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {

        val now = System.currentTimeMillis()
        val day = 1000 * 60 * 60 * 24

        var todo = Todo("Todo Id", "Todo 1", now, false, now)
        todos.add(todo)
        todo = Todo("Todo Id 2", "Todo 2", now - day, true, now)
        todos.add(todo)
        todo = Todo("Todo Id 3", "Todo 3", now + day, false, now)
        todos.add(todo)
        todo = Todo("Todo Id 4", "Todo 4", now + day + day, true, now)
        todos.add(todo)
        todo = Todo("Todo Id 5", "Todo 5", now + day, true, now)
        todos.add(todo)

    }

    @Test
    fun test_allTodosEmpty() {
        val expectedCount = 0
        val repository: TodoRepository = mock()
        val viewModel = ListViewModel(repository)

        whenever(repository.getAllTodos())
            .thenReturn(MutableLiveData(arrayListOf()))

        val allTodos = viewModel.allTodos?.value

        Assert.assertNotNull(allTodos)
        Assert.assertEquals(expectedCount, allTodos?.size)

    }

//    @Test
//    fun test_upcomingTodos() {
//        val expectedCount = 1
//        val viewModel = ListViewModel(repository)
//
//        val upcomingTodos = viewModel.upcomingTodosCount.value
//        Assert.assertNotNull(upcomingTodos)
//        Assert.assertEquals(expectedCount, upcomingTodos)
//
//    }
//
//    @Test
//    fun test_toggleTodo() {
//        val id = "fake"
//        val viewModel = ListViewModel(repository)
//
//        expectedException.expect(NotImplementedError::class.java)
//
//        viewModel.toggleTodo(id)
//
//    }

}