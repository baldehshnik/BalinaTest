package com.sparkfusion.balina.test.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.domain.model.image.GetImageModel

class GridAdapter : ListAdapter<GetImageModel, GridAdapter.GridViewHolder>(DiffCallback()) {

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
        // image loading with glide
        holder.textView.text = item.date.toString()
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
