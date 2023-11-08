package com.example.storyappsubmission.di

import android.content.Context
import com.example.storyappsubmission.data.pref.UserPreference
import com.example.storyappsubmission.data.pref.dataStore
import com.example.storyappsubmission.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}