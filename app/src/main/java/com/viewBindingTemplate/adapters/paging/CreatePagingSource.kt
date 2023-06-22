package com.viewBindingTemplate.adapters.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.viewBindingTemplate.dto.models.response.BaseResponse
import retrofit2.Response

/**
Implementation of the PagingSource interface for creating a paging data source.

@param [requestHandler] The PaginationHandler used for sending requests and retrieving paged data.
@param [T] The type of data being paged.

Usage:

    you can create variable of paging like this

 */

//val pager by lazy {
//    Pager(
//        config = PagingConfig (
//            pageSize = 5, enablePlaceholders = true, initialLoadSize = 10
//        )
//    ) {
//        CreatePagingSource(requestHandler = {
//            apiRequest.notificationHistory(it, 5)
//        })
//    }.flow
//}

class CreatePagingSource<T : Any>(private val requestHandler: PaginationHandler<List<T>>) :
    PagingSource<Int, T>() {


    /**
    Loads the requested page of data asynchronously and returns the result.

    @param [params] The LoadParams object containing information about the requested page.
    @return The [LoadResult] object containing the loaded data and page information.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = requestHandler.sendRequest(page)
            val list = response.body()?.data.orEmpty()

            val total = response.body()?.totalPages ?: 0
            val newCount = list.size
            val itemsBefore = page * params.loadSize
            val itemsAfter = total - (itemsBefore + newCount)

            LoadResult.Page(
                data = list,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (list.isEmpty()) null else page + 1,
                itemsAfter = itemsAfter
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }

    /**

    Retrieves the refresh key for the current state of the paging data.

    @param [state] The PagingState object representing the current state of the paging data.
    @return The refresh key, which is the next page to be loaded.
     */
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
    Sends a request to retrieve a specific page of paged data.

    @params [page] The page number to retrieve.
    @return The response containing the paged data.
     */
    fun interface PaginationHandler<T> {
        suspend fun sendRequest(page: Int): Response<BaseResponse<T>>
    }

}