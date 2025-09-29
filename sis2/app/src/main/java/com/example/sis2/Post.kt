package com.example.sis2.model // Замени на свой package

data class Post(
    val id: Int,
    val text: String,
    val imageUrl: String,
    val isLiked: Boolean = false // Теперь val — immutable
)