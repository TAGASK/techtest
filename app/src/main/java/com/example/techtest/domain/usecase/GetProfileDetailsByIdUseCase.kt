package com.example.techtest.domain.usecase

import com.example.base.utils.Resource
import com.example.techtest.data.entities.ProfileDetails
import com.example.techtest.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileDetailsByIdUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend fun invoke(id: String): Flow<Resource<ProfileDetails>> {
        return profileRepository.getUser(id)
    }
}