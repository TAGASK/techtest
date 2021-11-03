package com.example.techtest.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.techtest.data.entities.Profile
import com.example.techtest.domain.repository.ProfileRepository
import com.example.techtest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _profile = _id.switchMap {
        repository.getUsers(it)
    }

    val profile: LiveData<Resource<List<Profile>>> = _profile

    fun start() {
        _id.value = 0
    }

    fun loadMore() {
        if (_profile.value?.data?.isNotEmpty() == true) {
            _id.value = _id.value?.plus(1)
        }
    }
}