package com.supercat.piclist.ui.pictures.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.Request
import com.supercat.piclist.R
import com.supercat.piclist.domain.model.PictureItem
import com.supercat.piclist.presntation.pictures.PicturesListViewModel

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PictureItem>() {
    override fun areContentsTheSame(oldItem: PictureItem, newItem: PictureItem): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: PictureItem, newItem: PictureItem): Boolean {
        return oldItem.id == newItem.id
    }
}

class PicturesListAdapter(private val itemSize: Int, private val viewModel: PicturesListViewModel) :
    PagingDataAdapter<PictureItem, PicturesListAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false)

        with(itemView.findViewById<ViewGroup>(R.id.container)) {
            layoutParams.height = itemSize
            layoutParams.width = itemSize
            requestLayout()
        }

        return ViewHolder(itemView, viewModel)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.unbind()
    }

    class ViewHolder(itemView: View, private val viewModel: PicturesListViewModel) :
        RecyclerView.ViewHolder(itemView) {
        private var request: Request? = null
        private lateinit var item: PictureItem
        private val onClickListener = View.OnClickListener {
            viewModel.navigateToPicture(item)
        }

        fun bind(pictureItem: PictureItem?) {
            with(itemView.findViewById<ImageView>(R.id.imageView)) {
                if (pictureItem.isPlaceHolder()) {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_baseline_image_24
                        )
                    )
                } else {
                    item = pictureItem!!
                    itemView.setOnClickListener(onClickListener)
                    viewModel.contentShowed()

                    Glide.with(itemView.context)
                        .load(pictureItem.url)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        //.format(DecodeFormat.PREFER_RGB_565)
                        .transition(DrawableTransitionOptions.withCrossFade(100))
                        .into(this)
                        .also {
                            request = it.request
                        }
                }
            }
        }

        fun unbind() {
            itemView.setOnClickListener(null)
            request?.pause()
        }
    }

    companion object {
        fun PictureItem?.isPlaceHolder(): Boolean = this == null || url.isEmpty()
    }
}