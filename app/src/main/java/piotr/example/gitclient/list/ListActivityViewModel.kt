package piotr.example.gitclient.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import piotr.example.domain.Constants
import piotr.example.domain.usecase.GetProjectsListUseCase
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCase
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.gitclient.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ListActivityViewModel @Inject constructor(private val getProjectsListUseCase: GetProjectsListUseCase) :
    BaseViewModel() {
    val projects: LiveData<PagingData<ProjectListItem>> =
        Pager(config = PagingConfig(pageSize = Constants.API_ELEMENTS_PER_PAGE, prefetchDistance = 2),
            pagingSourceFactory = {
                ProjectsPagingSource(getProjectsListUseCase)
            }).liveData.cachedIn(viewModelScope)
}