package piotr.example.gitclient.list

import androidx.lifecycle.*
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import piotr.example.domain.Constants
import piotr.example.domain.usecase.GetProjectsListUseCase
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCase
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.gitclient.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ListActivityViewModel @Inject constructor(private val getProjectsListUseCase: GetProjectsListUseCase) :
    BaseViewModel() {

    data class SearchParams(val query:String, val sortType: ProjectsSortType)
    val searchParams = MutableLiveData<SearchParams>()
    val projects: LiveData<PagingData<ProjectListItem>> = searchParams.switchMap {
        Pager(config = PagingConfig(pageSize = Constants.API_ELEMENTS_PER_PAGE, prefetchDistance = 2),
            pagingSourceFactory = {
                ProjectsPagingSource(getProjectsListUseCase, it.query,it.sortType)
            }).liveData.cachedIn(viewModelScope)
    }

    init {
        searchParams.postValue(SearchParams("project",ProjectsSortType.STARS))
    }
}