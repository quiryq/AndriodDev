package com.example.sis2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sis2.model.Post

class PostViewModel : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    init {
        // Мок-данные: те же, но теперь immutable
        _posts.value = listOf(
            Post(1, "С ДР хлебушка!", "https://funny.klev.club/uploads/posts/2024-03/thumbs/funny-klev-club-p-smeshnie-kartinki-postironiya-5.jpg"),
            Post(2, "Новый дроп шансона???", "https://funny.klev.club/uploads/posts/2024-03/thumbs/funny-klev-club-p-smeshnie-kartinki-postironiya-3.jpg"),
            Post(3, "Как выбрать качественный арбуз", "https://cs20.pikabu.ru/s/2025/09/10/11/4hlkxtdu.webp"),
            Post(4, "Вагончик", "https://cs14.pikabu.ru/post_img/2023/11/28/2/1701136194161736101.webp"),
            Post(5, "Все люди марионетки", "https://cs13.pikabu.ru/post_img/2023/11/24/4/1700799394157136940.webp")
        )
    }

    fun toggleLike(postId: Int) {
        val currentPosts = _posts.value.orEmpty() // Безопасно, без toMutableList
        val newPosts = currentPosts.map { post ->
            if (post.id == postId) {
                post.copy(isLiked = !post.isLiked) // Создаём новый Post с инвертированным isLiked
            } else {
                post // Оставляем старый
            }
        }
        _posts.value = newPosts // Новый immutable список с новым объектом Post
    }
}