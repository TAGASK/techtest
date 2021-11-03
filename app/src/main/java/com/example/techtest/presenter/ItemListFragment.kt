package com.example.techtest.presenter

import android.os.Bundle
import android.os.Parcelable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techtest.R
import com.example.techtest.databinding.FragmentItemListBinding
import com.example.techtest.utils.Resource
import com.example.techtest.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

const val constRecyclerState = "ItemListFragment.recycler.layout"


/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
@AndroidEntryPoint
class ItemListFragment : Fragment(), ProfileAdapter.ProfileItemListener {


    private var binding: FragmentItemListBinding by autoCleared()
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var adapter: ProfileAdapter

    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggeredProfileViewModel
     */
    private val unhandledKeyEventListenerCompat =
        ViewCompat.OnUnhandledKeyEventListenerCompat { v, event ->
            if (event.keyCode == KeyEvent.KEYCODE_Z && event.isCtrlPressed) {
                Toast.makeText(
                    v.context,
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
                ).show()
                true
            } else if (event.keyCode == KeyEvent.KEYCODE_F && event.isCtrlPressed) {
                Toast.makeText(
                    v.context,
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
                ).show()
                true
            }
            false
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat)

        val recyclerView: RecyclerView = binding.itemList
        setupRecyclerView(recyclerView, this)
        setUpObservers()
        viewModel.start()
    }

    private fun setUpObservers() {
        viewModel.profile.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { data ->
                        binding.progressBar?.visibility = View.GONE
                        if (data.isEmpty()) {
                            binding.itemList.clearOnScrollListeners()
                            Toast.makeText(
                                requireContext(), R.string.end_of_list, Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (adapter.itemCount > 0) {
                                adapter.add(ArrayList(data))
                            } else {
                                adapter.setItems(ArrayList(data))
                            }
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    binding.progressBar?.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING ->
                    binding.progressBar?.visibility = View.VISIBLE
            }
        })
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onClickListener: ProfileAdapter.ProfileItemListener
    ) {

        adapter = ProfileAdapter(onClickListener)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMore()
                }
            }
        })
    }

    /**
     * This is a method for Fragment.
     * You can do the same in onCreate or onRestoreInstanceState
     */
    override fun onViewStateRestored(@Nullable savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val savedRecyclerLayoutState =
                savedInstanceState.getParcelable<Parcelable>(constRecyclerState)
            binding.itemList?.layoutManager?.onRestoreInstanceState(savedRecyclerLayoutState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            constRecyclerState,
            binding.itemList?.layoutManager?.onSaveInstanceState()
        )
    }

    fun clickedProfile(id: String) {
        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = view?.findViewById(R.id.item_detail_nav_container)
        val bundle = Bundle()
        bundle.putString(
            ItemDetailFragment.ARG_ITEM_ID,
            id
        )
        if (itemDetailFragmentContainer != null) {
            itemDetailFragmentContainer.findNavController()
                .navigate(R.id.fragment_item_detail, bundle)
        } else {
            view?.findNavController()?.navigate(R.id.show_item_detail, bundle)
        }
    }


    override fun onClickedProfile(id: String) {
        clickedProfile(id)
    }
}