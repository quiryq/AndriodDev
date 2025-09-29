package com.example.sis2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sis2.databinding.ItemPostBinding
import com.example.sis2.model.Post
import com.example.sis2.viewmodel.PostViewModel

class PostAdapter(
    private val viewModel: PostViewModel
) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position)) // getItem() из ListAdapter
    }

    // DiffUtil для smooth updates (без flicker)
    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id // Сравниваем по ID
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem // Полное сравнение (вкл. isLiked)
        }
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.textViewPost.text = post.text

            // Улучшенная загрузка Glide: размер, no cache для теста, centerCrop
            Glide.with(binding.root)
                .load(post.imageUrl)
                .override(300, 200) // Фиксированный размер для RecyclerView
                .diskCacheStrategy(DiskCacheStrategy.NONE) // Отключаем кэш, если проблемы с recycling
                .placeholder(android.R.color.darker_gray)
                .error(android.R.color.holo_red_dark)
                .centerCrop() // Для scaleType=centerCrop в XML
                .into(binding.imageViewPost)

            // Лайк
            binding.imageViewLike.setImageResource(
                if (post.isLiked) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
            )
            binding.imageViewLike.setOnClickListener {
                viewModel.toggleLike(post.id)
                // Нет нужды notify — DiffUtil сделает через submitList в Activity
            }
        }
    }
}