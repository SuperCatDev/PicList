package com.supercat.piclist.ui.full_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.supercat.piclist.R
import com.supercat.piclist.presntation.full_screen.FullScreenViewModel
import com.supercat.piclist.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FullScreenFragment : BaseFragment() {

    private val viewModel: FullScreenViewModel by viewModel {
        parametersOf(
            arguments?.getString(DOWNLOAD_URL_ID) ?: ""
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.full_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            onBackPressAction()
        }


        with(view.findViewById<TouchImageView>(R.id.picture)) {
            isZoomEnabled = true
            Glide.with(view)
                .load(arguments?.getString(DOWNLOAD_URL_ID))
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(this)
        }
    }

    override fun onBackPressAction() {
        viewModel.onBackPressAction()
    }

    companion object {
        private const val DOWNLOAD_URL_ID = "DOWNLOAD_URL_ID"

        fun newInstance(downloadUrl: String) = FullScreenFragment().apply {
            arguments = Bundle().apply {
                putString(DOWNLOAD_URL_ID, downloadUrl)
            }
        }
    }
}