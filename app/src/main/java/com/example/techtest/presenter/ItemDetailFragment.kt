package com.example.techtest.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.techtest.data.entities.ProfileDetails
import com.example.techtest.databinding.FragmentItemDetailBinding
import com.example.techtest.utils.Resource
import com.example.techtest.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
@AndroidEntryPoint
class ItemDetailFragment : Fragment() {


    private var binding: FragmentItemDetailBinding by autoCleared()
    private val viewModel: ProfileDetailsViewModel by viewModels()


    lateinit var itemDetailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        itemDetailTextView = binding.itemDetail
        updateContent()
        arguments?.getString(ARG_ITEM_ID)?.let {
            viewModel.start(it)
        }

        return rootView
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

    private fun updateContent() {
        viewModel.profileDetails.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { data ->
                        bindDetails(data)
                        binding.progressBar?.visibility = View.GONE
                        binding.itemDetailContainer.visibility = View.VISIBLE
                    }
                }

                Resource.Status.ERROR -> {
                    binding.progressBar?.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT)
                        .show()
                }

                Resource.Status.LOADING -> {
                    binding.progressBar?.visibility = View.VISIBLE
                    binding.itemDetailContainer.visibility = View.GONE
                }
            }
        })
    }


    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

}