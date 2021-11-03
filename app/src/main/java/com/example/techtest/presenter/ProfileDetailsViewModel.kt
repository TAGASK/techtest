package com.example.techtest.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.techtest.data.entities.ProfileDetails
import com.example.techtest.domain.repository.ProfileRepository
import com.example.techtest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileDetailsViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _profileDetails = _id.switchMap {
        repository.getUser(it)
    }

    val profileDetails: LiveData<Resource<ProfileDetails>> = _profileDetails

    fun start(id: String) {
        _id.value = id
    }
}