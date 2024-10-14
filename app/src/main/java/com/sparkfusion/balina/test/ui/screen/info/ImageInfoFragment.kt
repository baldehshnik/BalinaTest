package com.sparkfusion.balina.test.ui.screen.info

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.databinding.FragmentImageInfoBinding
import com.sparkfusion.balina.test.domain.model.comment.GetCommentModel
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.ui.exception.ViewBindingIsNullException
import com.sparkfusion.balina.test.ui.utils.SecondsToDateConverter
import com.sparkfusion.balina.test.ui.utils.shortSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageInfoFragment : Fragment() {

    private val secondsToDateConverter = SecondsToDateConverter()

    private var _binding: FragmentImageInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            throw ViewBindingIsNullException()
        }

    private val viewModel: ImageInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ImageInfoFragmentArgs by navArgs()
        val imageModel = args.imageModel

        initUI(imageModel)

        val commentsAdapter = CommentsAdapter(
            onPressAndHoldHandler = ::showDeleteConfirmationDialog
        )
        initCommentsList(commentsAdapter)

        handleCommentsSaveState(commentsAdapter)
        handleCommentDeleteState(commentsAdapter)

        binding.buttonSend.setOnClickListener {
            viewModel.handleIntent(ImageInfoIntent.SaveComment(binding.editTextMessage.text.toString()))
        }

        viewModel.handleIntent(ImageInfoIntent.LoadComments(imageModel.id))
    }

    private fun initUI(imageModel: GetImageModel) {
        binding.titleTextView.text = secondsToDateConverter.convert(imageModel.date)
        Glide.with(this)
            .load(imageModel.url)
            .into(binding.imageView)
    }

    private fun initCommentsList(commentsAdapter: CommentsAdapter) {
        binding.recyclerViewComments.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewComments.adapter = commentsAdapter
        viewModel.comments.observe(viewLifecycleOwner) { pagingData ->
            commentsAdapter.submitData(lifecycle, pagingData)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleCommentsSaveState(commentsAdapter: CommentsAdapter) {
        viewModel.commentSaveState.observe(viewLifecycleOwner) {
            when (it) {
                CommentSaveState.Empty -> {}
                CommentSaveState.ImageNotFound -> {
                    binding.root.shortSnackbar(R.string.image_not_found)
                }
                CommentSaveState.Failure -> {
                    binding.root.shortSnackbar(R.string.error)
                }
                CommentSaveState.Success -> {
                    binding.root.shortSnackbar(R.string.comment_was_added)
                    binding.editTextMessage.setText("")
                    commentsAdapter.refresh()
                }
            }
        }
    }

    private fun handleCommentDeleteState(commentsAdapter: CommentsAdapter) {
        viewModel.deleteCommentState.observe(viewLifecycleOwner) {
            when (it) {
                DeleteCommentState.Empty -> {}
                DeleteCommentState.ImageNotFound -> {
                    binding.root.shortSnackbar(R.string.image_not_found)
                }
                DeleteCommentState.Failure -> {
                    binding.root.shortSnackbar(R.string.error)
                }
                DeleteCommentState.Success -> {
                    binding.root.shortSnackbar(R.string.comment_was_deleted)
                    commentsAdapter.refresh()
                }
            }
        }
    }

    private fun showDeleteConfirmationDialog(comment: GetCommentModel) {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.confirm_deletion))
            .setMessage(resources.getString(R.string.are_you_sure_you_want_delete_this_comment))
            .setPositiveButton(resources.getString(R.string.delete)) { dialog, _ ->
                viewModel.handleIntent(ImageInfoIntent.DeleteComment(comment))
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
