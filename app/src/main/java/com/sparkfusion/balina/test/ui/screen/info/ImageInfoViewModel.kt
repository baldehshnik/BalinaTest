package com.sparkfusion.balina.test.ui.screen.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sparkfusion.balina.test.domain.model.comment.CreateCommentModel
import com.sparkfusion.balina.test.domain.model.comment.GetCommentModel
import com.sparkfusion.balina.test.domain.repository.ICommentRepository
import com.sparkfusion.balina.test.domain.usecase.comment.ReadCommentsUseCase
import com.sparkfusion.balina.test.ui.utils.withMainContext
import com.sparkfusion.balina.test.utils.common.CommonViewModel
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageInfoViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val commentRepository: ICommentRepository,
    private val readCommentsUseCase: ReadCommentsUseCase
) : CommonViewModel<ImageInfoIntent>() {

    private var imageId: Int? = null

    private val _comments = MutableLiveData<PagingData<GetCommentModel>>()
    val comments: LiveData<PagingData<GetCommentModel>> get() = _comments

    private val _commentSaveState = MutableLiveData<CommentSaveState>(CommentSaveState.Empty)
    val commentSaveState: LiveData<CommentSaveState> get() = _commentSaveState

    private val _deleteCommentState = MutableLiveData<DeleteCommentState>(DeleteCommentState.Empty)
    val deleteCommentState: LiveData<DeleteCommentState> get() = _deleteCommentState

    override fun handleIntent(intent: ImageInfoIntent) {
        when (intent) {
            is ImageInfoIntent.LoadComments -> {
                imageId = intent.imageId
                loadComments()
            }

            is ImageInfoIntent.SaveComment -> saveComment(intent.message)
            is ImageInfoIntent.DeleteComment -> deleteComment(intent.comment)
        }
    }

    private fun deleteComment(comment: GetCommentModel) {
        viewModelScope.launch(ioDispatcher) {
            if (imageId == null) {
                withMainContext(_deleteCommentState, DeleteCommentState.ImageNotFound)
                return@launch
            }

            commentRepository.deleteComment(imageId!!, comment.id)
                .onSuccess {
                    withMainContext(_deleteCommentState, DeleteCommentState.Success)
                }
                .onFailure {
                    withMainContext(_deleteCommentState, DeleteCommentState.Failure)
                }
        }
    }

    private fun saveComment(message: String) {
        if (message.isEmpty()) return
        viewModelScope.launch(ioDispatcher) {
            if (imageId == null) {
                withMainContext(_commentSaveState, CommentSaveState.ImageNotFound)
                return@launch
            }

            commentRepository.saveComment(imageId!!, CreateCommentModel(message))
                .onSuccess {
                    withMainContext(_commentSaveState, CommentSaveState.Success)
                }
                .onFailure {
                    withMainContext(_commentSaveState, CommentSaveState.Failure)
                }
        }
    }

    private fun loadComments() {
        viewModelScope.launch(ioDispatcher) {
            val flow = readCommentsUseCase.invoke(imageId!!).cachedIn(viewModelScope)
            flow.collect {
                _comments.postValue(it)
            }
        }
    }
}
