package com.example.techtest.presenter.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.base.utils.showToast
import com.example.techtest.databinding.FragmentItemDetailBinding
import com.example.techtest.domain.entities.ProfileDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
@AndroidEntryPoint
class ItemDetailFragment : Fragment() {


    private lateinit var binding: FragmentItemDetailBinding
    private val viewModel: ProfileDetailsViewModel by viewModels()


    lateinit var itemDetailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        itemDetailTextView = binding.itemDetail
        observeState()
        observeProfileDetails()

        arguments?.getString(ARG_ITEM_ID)?.let {
            viewModel.start(it)
        }

        return rootView
    }

    private fun observeProfileDetails() {
        viewModel.profileDetails.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .onEach { profileDetails ->
                profileDetails?.let { bindDetails(it) }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeState() {
        viewModel.mState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: ItemDetailsFragmentState) {
        when (state) {
            is ItemDetailsFragmentState.Error -> requireActivity().showToast(state.error)
            ItemDetailsFragmentState.Init -> Unit
            is ItemDetailsFragmentState.IsLoading -> handleIsLoading(state.isLoading)
            is ItemDetailsFragmentState.ShowToast -> requireActivity().showToast(state.message)
        }
    }

    private fun handleIsLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar?.visibility = View.VISIBLE
        } else {
            binding.progressBar?.visibility = View.GONE
        }
    }

    private fun bindDetails(data: ProfileDetails) {
        binding.name.text = """${data.firstName}  ${data.lastName}"""
        binding.itemDetail.text = """Name : ${data.lastName}
                |Firstname : ${data.firstName}
                |Gender : ${data.gender}
                |Phone : ${data.phone}
                |Email : ${data.email}""".trimMargin()
        Glide.with(binding.root)
            .load(data.picture)
            .transform(CircleCrop())
            .into(binding.image)
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

}