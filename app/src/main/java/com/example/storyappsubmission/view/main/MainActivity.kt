package com.example.storyappsubmission.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappsubmission.R
import com.example.storyappsubmission.adapter.ListStoryAdapter
import com.example.storyappsubmission.adapter.LoadingStateAdapter
import com.example.storyappsubmission.databinding.ActivityMainBinding
import com.example.storyappsubmission.helper.ViewModelFactory
import com.example.storyappsubmission.view.addStory.AddStoryActivity
import com.example.storyappsubmission.view.map.MapsActivity
import com.example.storyappsubmission.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels { factory }
    private lateinit var adapter: ListStoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        setupViewModel()
        setupView(this)
        getStories()
        setupAction()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)

        mainViewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
                binding.greeting.text = getString(R.string.greeting, user.name)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setupView(context: Context) {
        val storiesRv = binding.rvStory

        storiesRv.layoutManager = LinearLayoutManager(context)

        adapter = ListStoryAdapter()
        storiesRv.adapter = adapter
    }

    private fun getStories() {

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        mainViewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.rvStory.scrollToPosition(0)
            }, 300)
        }
    }

    private fun setupAction() {
        binding.postingPageButton.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuLogout -> {
                mainViewModel.logout()
            }
            R.id.story_map ->{
                startActivity(Intent(this, MapsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}