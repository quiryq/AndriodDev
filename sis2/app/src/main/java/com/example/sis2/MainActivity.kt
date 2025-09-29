package com.example.sis2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sis2.adapter.PostAdapter
import com.example.sis2.databinding.ActivityMainBinding
import com.example.sis2.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter // Один раз
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adapter один раз
        postAdapter = PostAdapter(viewModel)
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPosts.adapter = postAdapter // Устанавливаем сразу

        // Observe: submitList для DiffUtil (smooth update, no flicker/scroll)
        viewModel.posts.observe(this) { posts ->
            postAdapter.submitList(posts) // Diff'ит и обновляет только changes
        }
    }
}