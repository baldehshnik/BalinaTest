package com.sparkfusion.balina.test.ui.screen.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.ui.utils.SecondsToDateConverter

class GridAdapter(
    private val handlePressAndHold: (GetImageModel) -> Unit,
    private val onItemClick: (GetImageModel) -> Unit
) : PagingDataAdapter<GetImageModel, GridAdapter.GridViewHolder>(DiffCallback()) {

    private val secondsToDateConverter = SecondsToDateConverter()

    inner class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            Glide.with(holder.itemView.context)
                .load(item.url)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView)

            holder.textView.text = secondsToDateConverter.convert(item.date)
            holder.itemView.setOnLongClickListener {
                handlePressAndHold(item)
                true
            }
            holder.itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GetImageModel>() {
        override fun areItemsTheSame(oldItem: GetImageModel, newItem: GetImageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GetImageModel, newItem: GetImageModel): Boolean {
            return oldItem == newItem
        }
    }
}
