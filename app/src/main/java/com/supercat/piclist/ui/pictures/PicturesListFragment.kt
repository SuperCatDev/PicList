package com.supercat.piclist.ui.pictures

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supercat.piclist.R
import com.supercat.piclist.ext.getScreenWidth
import com.supercat.piclist.ext.toPx
import com.supercat.piclist.presntation.pictures.PicturesListViewModel
import com.supercat.piclist.ui.BaseFragment
import com.supercat.piclist.ui.pictures.adapter.GridSpacingItemDecoration
import com.supercat.piclist.ui.pictures.adapter.PicturesListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PicturesListFragment : BaseFragment() {

    private val spanCount by lazy(LazyThreadSafetyMode.NONE) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) IMAGE_COLUMNS_COUNT_PORTRAIT
        else IMAGE_COLUMNS_COUNT_LANDSCAPE
    }

    private val itemSize: Int by lazy(LazyThreadSafetyMode.NONE) {
        (requireActivity().getScreenWidth() - SPACE_DP.toPx(requireContext()) * (spanCount + 1)) / spanCount
    }

    private val viewModel: PicturesListViewModel by viewModel {
        parametersOf(
            itemSize
        )
    }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PicturesListAdapter(itemSize, viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.pictures_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view.findViewById<RecyclerView>(R.id.pictures)) {
            layoutManager = GridLayoutManager(
                requireContext(),
                spanCount,
            )
            itemAnimator = null
            adapter = this@PicturesListFragment.adapter
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = spanCount,
                    spacing = SPACE_DP.toPx(requireContext()),
                    includeEdge = true,
                )
            )
        }

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            onBackPressAction()
        }

        lifecycleScope.launch {
            viewModel.placeholder?.let {
                adapter.submitData(it)
            }

            viewModel.picturesPagingFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onBackPressAction() {
        viewModel.onBackPressAction()
    }

    companion object {
        private const val IMAGE_COLUMNS_COUNT_PORTRAIT = 3
        private const val IMAGE_COLUMNS_COUNT_LANDSCAPE = 5
        private const val SPACE_DP = 4

        fun newInstance() = PicturesListFragment()
    }
}