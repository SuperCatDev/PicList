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
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.supercat.piclist.R
import com.supercat.piclist.domain.model.PictureItem

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PictureItem>() {
    override fun areContentsTheSame(oldItem: PictureItem, newItem: PictureItem): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: PictureItem, newItem: PictureItem): Boolean {
        return oldItem.id == newItem.id
    }
}

class PicturesListAdapter(private val itemSize: Int) :
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

        return ViewHolder(itemView)
    }

    override fun onViewRecycled(holder: ViewHolder) {
    //    holder.unbind()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pictureItem: PictureItem?) {
            with(itemView.findViewById<ImageView>(R.id.imageView)) {
                if (pictureItem == null) {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_baseline_image_24
                        )
                    )
                } else {
                    // setImageDrawable(ContextCompat.getDrawable(itemView.context, R.color.black))
                    Glide.with(itemView)
                        .load(pictureItem.url)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .format(DecodeFormat.PREFER_RGB_565)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(this)
                }
            }

        }

        fun unbind() {
            with(itemView.findViewById<ImageView>(R.id.imageView)) {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_baseline_image_24
                    )
                )
            }
        }
    }
}