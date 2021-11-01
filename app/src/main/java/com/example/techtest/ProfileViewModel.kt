package com.example.techtest

import androidx.lifecycle.ViewModel
import com.example.techtest.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    val profile = repository.getUsers()
}