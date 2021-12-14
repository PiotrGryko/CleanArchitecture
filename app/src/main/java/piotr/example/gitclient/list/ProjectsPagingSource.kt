package piotr.example.gitclient.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import piotr.example.domain.usecase.GetProjectsListUseCase
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCase
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.domain.model.value.ProjectsSortType
import retrofit2.HttpException

class ProjectsPagingSource(val getProjectsListUseCase: GetProjectsListUseCase) : PagingSource<Int, ProjectListItem>() {

    override fun getRefreshKey(state: PagingState<Int, ProjectListItem>) = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectListItem> {
        val key = params.key ?: 1
        return try {
            val result = getProjectsListUseCase.invoke("test",ProjectsSortType.STARS,key)
            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = key + 1
            )
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}