package com.sparkfusion.balina.test.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.databinding.FragmentGalleryBinding
import com.sparkfusion.balina.test.ui.exception.ViewBindingIsNullException
import com.sparkfusion.balina.test.ui.utils.shortSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            throw ViewBindingIsNullException()
        }

    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipeRefreshLayout = binding.swipeRefreshLayout
        val adapter = GridAdapter(
            handlePressAndHold = {
                viewModel.handleIntent(GalleryLoadImagesIntent.DeleteImage(it))
            }
        )

        initRecyclerView(adapter)
        handleLoadingState(swipeRefreshLayout, adapter)
        handleImageDeleting(adapter)
    }

    private fun initRecyclerView(adapter: GridAdapter) {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = adapter

        viewModel.imagesLiveData.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }
    }

    private fun handleImageDeleting(adapter: GridAdapter) {
        viewModel.deleteImageLiveData.observe(viewLifecycleOwner) {
            when (it) {
                DeleteImageState.Empty -> {}
                DeleteImageState.Success -> {
                    binding.root.shortSnackbar(R.string.image_deleted_successfully)
                    adapter.refresh()
                }

                DeleteImageState.Failure -> {
                    binding.root.shortSnackbar(R.string.failed_to_delete_image)
                }
            }
        }
    }

    private fun handleLoadingState(
        swipeRefreshLayout: SwipeRefreshLayout,
        adapter: GridAdapter
    ) {
        swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadStates ->
                when (loadStates.refresh) {
                    is LoadState.Loading -> swipeRefreshLayout.isRefreshing = true
                    is LoadState.NotLoading -> swipeRefreshLayout.isRefreshing = false
                    is LoadState.Error -> swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
