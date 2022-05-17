package com.example.finalapp

import java.io.Serializable


data class Todo (
    val title : String,
    var isChecked: Boolean = false
) : Serializable
