package com.example.storyappsubmission.view.detailstory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyappsubmission.data.responses.ListStoryItem
import com.example.storyappsubmission.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
    }

    private fun setupData() {
        val story = intent.getParcelableExtra<ListStoryItem>("Story") as ListStoryItem
        Glide.with(applicationContext)
            .load(story.photoUrl)
            .into(binding.storyImageView)

        binding.apply {
            nameTextView.text = story.name
            descTextView.text = story.description
            timestamp.text = story.createdAt
        }
    }
}