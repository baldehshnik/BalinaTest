package com.sparkfusion.balina.test.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sparkfusion.balina.test.data.datasource.api.ImagesApiService
import com.sparkfusion.balina.test.data.entity.image.GetImageDataEntity
import com.sparkfusion.balina.test.utils.exception.common.UnexpectedException

class ImagePagingSource(
    private val apiService: ImagesApiService
) : PagingSource<Int, GetImageDataEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetImageDataEntity> {
        val page = params.key ?: 0
        return try {
            val response = apiService.readImages(page)
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

    override fun getRefreshKey(state: PagingState<Int, GetImageDataEntity>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}
