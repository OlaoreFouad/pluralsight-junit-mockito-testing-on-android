package com.psdemo.todo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.psdemo.todo.data.Todo
import com.psdemo.todo.data.TodoDao
import com.psdemo.todo.data.TodoRoomDatabase
import com.psdemo.todo.data.TodoRoomRepository
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoRoomRepositoryTest {

    private val now = System.currentTimeMillis()
    private val day = 60 * 60 * 24 * 1000
    private lateinit var todoDatabase: TodoRoomDatabase
    private lateinit var todoDao: TodoDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        todoDatabase = Room.inMemoryDatabaseBuilder(
            context, TodoRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        todoDao = spy(todoDatabase.todoDao())
    }

    @Test
    fun test_getUpcomingTodosEmpty() {
        val expected = 0
        val dao = spy(todoDatabase.todoDao())
        val repository = TodoRoomRepository(dao)

        val actual = repository.getUpcomingTodosCount().test().value()

        Assert.assertEquals(expected, actual)
        verify(dao).getDateCount(any())

    }

    @Test
    fun test_getUpcomingTestTodosSingleMatch() {

        val todo = Todo("1", "Todo 1", now + day, false, System.currentTimeMillis())
        val dao = spy(todoDatabase.todoDao())
        val expected = 1
        val repository = TodoRoomRepository(dao)

        repository.insert(todo)
        repository.insert(Todo("2", "Todo 2", now - day, false, System.currentTimeMillis()))
        repository.insert(Todo("3", "Todo 3", now + day, true, System.currentTimeMillis()))

        val actual = repository.getUpcomingTodosCount().test().value()
        Assert.assertEquals(expected, actual)
        verify(dao).getDateCount(any())

    }

    @Test
    fun test_getAllTodos() {

        val todo = Todo("1", "Todo 1", now + day, false, System.currentTimeMillis())
        val dao = spy(todoDatabase.todoDao())
        val expected = 3
        val repository = TodoRoomRepository(dao)

        repository.insert(todo)
        repository.insert(Todo("2", "Todo 2", now - day, false, System.currentTimeMillis()))
        repository.insert(Todo("3", "Todo 3", now + day, true, System.currentTimeMillis()))

        val actual = repository.getAllTodos().test().value()
        Assert.assertEquals(expected, actual.size)
        Assert.assertTrue(actual.contains(todo))

    }

    @After
    fun teardown() {
        todoDatabase.close()
    }

}