package com.psdemo.todo

import com.psdemo.todo.list.determineCardColor
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*
import kotlin.math.exp

@RunWith(Parameterized::class)
class ListUtilsTest(
    private val expected: Int,
    private val dueDate: Long?,
    private val done: Boolean,
    private val scenario: String?
) {

    companion object {

        val now = System.currentTimeMillis()
        const val day = 60 * 60 * 24 * 1000

        @JvmStatic
        @Parameterized.Parameters(name = "determineCardColor: {3}")
        fun todos() = listOf(
            arrayOf(R.color.todoDone, null, true, "Todo Done"),
            arrayOf(R.color.todoNotDue, null, false, "Todo Not Done"),
            arrayOf(R.color.todoOverDue, now - day, false, "Todo Overdue"),
            arrayOf(R.color.todoDueStrong, now + day + day, false, "Todo Strong")
        )

    }

    @Test
    fun test_determineCardColor() {
//        Act
        val actual = determineCardColor(dueDate, done)

//        Assert
        Assert.assertEquals(expected, actual)
    }

}