package com.psdemo.todo

import com.nhaarman.mockitokotlin2.*
import com.psdemo.todo.add.AddViewModel
import com.psdemo.todo.data.TodoRepository
import org.junit.Assert
import org.junit.Test

class AddViewModelTest {

    private val now = System.currentTimeMillis()
    private val day = 60 * 60 * 24 * 1000

//    test saving a _todo with correct data
    @Test
    fun test_save() {

        val repository: TodoRepository = mock()
        val viewModel = AddViewModel(repository)

        viewModel.todo.title = "Todo Title"
        viewModel.todo.dueDate = now + day

        val actual: Any? = viewModel.save()
        Assert.assertNull(actual)
        verify(repository).insert(any())

    }

    //    test saving a _todo with no title
    @Test
    fun test_saveNoTitle() {
        val expected = "Title is required"
        val repository: TodoRepository = mock()
        val viewModel = AddViewModel(repository)
        viewModel.todo.dueDate = now + day

        val actual: String? = viewModel.save()

        Assert.assertEquals(expected, actual)
        verify(repository, never()).insert(any())

    }

    @Test
    fun test_saveNoDueDate() {

        val actualTitle = "Todo Title"
        val actualDueDate = null
        val repository: TodoRepository = mock()
        val viewModel = AddViewModel(repository)

        viewModel.todo.title = actualTitle
        val actual: String? = viewModel.save()

        Assert.assertNull(actual)
        verify(repository).insert(argThat {
            this.title == actualTitle && this.dueDate == actualDueDate
        })

    }


}