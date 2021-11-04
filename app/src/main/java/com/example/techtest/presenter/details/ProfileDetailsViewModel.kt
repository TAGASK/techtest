package com.example.techtest.presenter.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.Resource
import com.example.techtest.data.entities.ProfileDetails
import com.example.techtest.domain.usecase.GetProfileDetailsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailsViewModel @Inject constructor(
    private val getProfileDetailsByIdUseCase: GetProfileDetailsByIdUseCase
) : ViewModel() {

    private val state = MutableStateFlow<ItemDetailsFragmentState>(ItemDetailsFragmentState.Init)
    val mState: StateFlow<ItemDetailsFragmentState> get() = state

    private fun setLoading() {
        state.value = ItemDetailsFragmentState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = ItemDetailsFragmentState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = ItemDetailsFragmentState.ShowToast(message)
    }

    private var _id = ""

    private val _profileDetails = MutableStateFlow<ProfileDetails?>(null)
    val profileDetails: StateFlow<ProfileDetails?> get() = _profileDetails

    fun start(id: String) {
        _id = id
        getProfileDetails(id)
    }

    private fun getProfileDetails(id: String) {
        viewModelScope.launch {
            getProfileDetailsByIdUseCase.invoke(id)
                .onStart {
                    setLoading()
                }
                .catch { ex ->
                    hideLoading()
                    showToast(ex.stackTraceToString())
                }
                .collect { result ->
                    when (result.status) {
                        Resource.Status.SUCCESS -> {
                            result.data?.let {
                                hideLoading()
                                _profileDetails.value = it
                            }

                        }
                        Resource.Status.ERROR -> {
                            result.message?.let {
                                hideLoading()
                                ItemDetailsFragmentState.Error(it)
                            }
                        }
                        Resource.Status.LOADING -> {
                            setLoading()
                        }
                    }
                }
        }

    }
}

sealed class ItemDetailsFragmentState {
    object Init : ItemDetailsFragmentState()
    data class IsLoading(val isLoading: Boolean) : ItemDetailsFragmentState()
    data class ShowToast(val message: String) : ItemDetailsFragmentState()
    data class Error(val error: String) : ItemDetailsFragmentState()
}
