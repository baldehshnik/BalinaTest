package com.sparkfusion.balina.test.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sparkfusion.balina.test.data.datasource.api.CommentApiService
import com.sparkfusion.balina.test.data.entity.comment.GetCommentDataEntity
import com.sparkfusion.balina.test.utils.exception.common.UnexpectedException

class CommentsPagingSource(
    private val apiService: CommentApiService,
    private val imageId: Int
) : PagingSource<Int, GetCommentDataEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetCommentDataEntity> {
        val page = params.key ?: 0
        return try {
            val response = apiService.readComments(imageId, page)
            if (response.isSuccessful) {
                val data = response.body()?.data ?: emptyList()
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(UnexpectedException())
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GetCommentDataEntity>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}
