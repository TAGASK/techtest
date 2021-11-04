package com.example.techtest.presenter.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.Resource
import com.example.techtest.data.entities.Profile
import com.example.techtest.domain.usecase.GetProfileByPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileByPageUseCase: GetProfileByPageUseCase
) : ViewModel() {

    private val state = MutableStateFlow<ItemListFragmentState>(ItemListFragmentState.Init)
    val mState: StateFlow<ItemListFragmentState> get() = state

    private fun setLoading() {
        state.value = ItemListFragmentState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = ItemListFragmentState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = ItemListFragmentState.ShowToast(message)
    }

    private fun getUsers(page: Int) {
        viewModelScope.launch {
            getProfileByPageUseCase.invoke(page)
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
                            hideLoading()
                            _profile.value = result.data
                        }
                        Resource.Status.ERROR -> {
                            hideLoading()
                            result.message?.let { showToast(it) }
                        }
                        Resource.Status.LOADING -> {
                            setLoading()
                        }
                    }
                }
        }
    }

    private var _id = 0


    private val _profile = MutableStateFlow<List<Profile>?>(null)
    val profile: StateFlow<List<Profile>?> get() = _profile


    fun start() {
        getUsers(_id)
    }

    fun loadMore() {
        if (_profile?.value?.isNotEmpty() == true) {
            _id = _id.plus(1)
            getUsers(_id)
        }
    }
}

sealed class ItemListFragmentState {
    object Init : ItemListFragmentState()
    data class IsLoading(val isLoading: Boolean) : ItemListFragmentState()
    data class ShowToast(val message: String) : ItemListFragmentState()
    data class Error(val error: String) : ItemListFragmentState()
}