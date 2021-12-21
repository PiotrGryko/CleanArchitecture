package piotr.example.gitclient.list

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import piotr.example.domain.Constants
import piotr.example.domain.usecase.GetProjectsListUseCase
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCase
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.domain.model.value.ProjectsSortType
import retrofit2.HttpException

class ProjectsPagingSource(
    private val getProjectsListUseCase: GetProjectsListUseCase,
    private val query: String,
    private val sortType: ProjectsSortType
) : PagingSource<Int, ProjectListItem>() {

    override fun getRefreshKey(state: PagingState<Int, ProjectListItem>) = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectListItem> {
        val key = params.key ?: 1

        return try {
            val result = getProjectsListUseCase.invoke(query, sortType, key)
            val nextKey = (key+1).takeIf { result.isNotEmpty() }
            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}