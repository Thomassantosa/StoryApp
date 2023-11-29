package com.example.storyappsubmission.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.storyappsubmission.data.repository.Repository
import com.example.storyappsubmission.data.responses.ListStoryItem
import com.example.storyappsubmission.data.responses.LoginResult
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val story: LiveData<PagingData<ListStoryItem>> = repository.getStoryData()

    fun getUser(): LiveData<LoginResult> {
        return repository.getUser()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}