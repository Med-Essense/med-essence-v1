package com.example.test.model

import java.util.Date

data class DreamEntry(
    val id: Long = 0,
    val title: String,
    val content: String,
    val category: String,
    val date: Date = Date(),
    val isImportant: Boolean = false,

)