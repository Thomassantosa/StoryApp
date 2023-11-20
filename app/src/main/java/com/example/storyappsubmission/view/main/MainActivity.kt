package com.example.storyappsubmission.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappsubmission.R
import com.example.storyappsubmission.adapter.ListStoryAdapter
import com.example.storyappsubmission.data.responses.ListStoryItem
import com.example.storyappsubmission.databinding.ActivityMainBinding
import com.example.storyappsubmission.helper.ViewModelFactory
import com.example.storyappsubmission.view.addStory.AddStoryActivity
import com.example.storyappsubmission.view.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

//        setupView()
        setupViewModel()
        setupAction()
    }

//    private fun setupView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) {user ->
            if (user.token.isNotEmpty()) {
                binding.greeting.text = getString(R.string.greeting, user.name)
                mainViewModel.getStory(user.token)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.listStory.observe(this) { stories ->
            setStoriesData(stories.listStory)
        }
    }

    private fun setStoriesData(stories: List<ListStoryItem>) {
        binding.rvUser.adapter = ListStoryAdapter(stories)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupAction() {
        binding.postingPageButton.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }
}