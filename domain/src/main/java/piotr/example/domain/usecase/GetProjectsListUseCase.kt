package piotr.example.domain.usecase

import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.domain.model.value.ProjectsSortType

interface GetProjectsListUseCase {
    suspend fun invoke(query:String, sortType: ProjectsSortType, page:Int) : List<ProjectListItem>
}