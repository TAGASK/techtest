package com.example.techtest.domain.usecase

import com.example.techtest.data.entities.Profile
import com.example.techtest.domain.repository.ProfileRepository
import com.example.techtest.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileByPageUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend fun invoke(page: Int): Flow<Resource<List<Profile>>> {
        return profileRepository.getUsers(page)
    }
}