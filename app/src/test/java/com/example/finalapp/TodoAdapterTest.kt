package com.example.finalapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.junit.Assert.*

import org.junit.Test

class TodoAdapterTest {
    private val todos: ArrayList<Todo> = TODO()


    @Test
    fun onCreateViewHolder() {

    }

    @Test
    fun addTodo() {
        val todo = Todo("hello")
        addTodo(todo)
        assertEquals(todos[0], todo)
    }

    @Test
    fun deleteDoneTodos() {
    }

    @Test
    fun onBindViewHolder() {
    }

    @Test
    fun getItemCount() {
    }

}

