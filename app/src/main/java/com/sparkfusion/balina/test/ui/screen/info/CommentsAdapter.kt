package com.sparkfusion.balina.test.ui.screen.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.domain.model.comment.GetCommentModel
import com.sparkfusion.balina.test.ui.utils.SecondsToDateConverter

class CommentsAdapter(
    private val onPressAndHoldHandler: (model: GetCommentModel) -> Unit
) : PagingDataAdapter<GetCommentModel, CommentsAdapter.CommentViewHolder>(CommentsDiffUtil()) {

    private val secondsToDateConverter = SecondsToDateConverter()

    inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val commentTextView: TextView = view.findViewById(R.id.commentTextView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.commentTextView.text = comment?.text
        holder.dateTextView.text = comment?.date?.let { secondsToDateConverter.convert(it) }

        holder.itemView.setOnLongClickListener {
            if (comment != null) onPressAndHoldHandler(comment)
            true
        }
    }

    class CommentsDiffUtil : DiffUtil.ItemCallback<GetCommentModel>() {

        override fun areItemsTheSame(oldItem: GetCommentModel, newItem: GetCommentModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetCommentModel, newItem: GetCommentModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
