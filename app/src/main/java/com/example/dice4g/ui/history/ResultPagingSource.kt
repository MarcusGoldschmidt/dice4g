package com.example.dice4g.ui.history

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.dice4g.infra.dao.ResultDao
import com.example.dice4g.infra.models.Profile
import com.example.dice4g.infra.models.Result

class ResultPagingSource(
    private val profile: Profile,
    private val dao: ResultDao
) : PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 0

        return try {
            val entities = dao.getPagedList(profile.id, params.loadSize, page * params.loadSize)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}