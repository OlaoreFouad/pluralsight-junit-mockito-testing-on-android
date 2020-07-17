package com.psdemo.todo

import com.psdemo.todo.data.Todo
import com.psdemo.todo.data.TodoRepository
import com.psdemo.todo.list.ListViewModel
import com.psdemo.todo.util.TodoTestRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ListViewModelTest {

    private lateinit var repository: TodoRepository
    private var todos = mutableListOf<Todo>()

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

        repository = TodoTestRepository(todos)

    }

    @Test
    fun test_allTodos() {
        val expectedCount = 5
        val viewModel = ListViewModel(repository)

        val allTodos = viewModel.allTodos.value

        Assert.assertNotNull(allTodos)
        Assert.assertEquals(expectedCount, allTodos?.size)

    }

    @Test
    fun test_upcomingTodos() {
        val expectedCount = 1
        val viewModel = ListViewModel(repository)

        val upcomingTodos = viewModel.upcomingTodosCount.value
        Assert.assertNotNull(upcomingTodos)
        Assert.assertEquals(expectedCount, upcomingTodos)

    }

}