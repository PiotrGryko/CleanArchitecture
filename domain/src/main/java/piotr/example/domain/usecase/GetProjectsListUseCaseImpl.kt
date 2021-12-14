package piotr.example.domain.usecase

import piotr.example.domain.repository.ProjectsRepository
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.domain.model.value.toProjectListItem

class GetProjectsListUseCaseImpl(val projectsRepository: ProjectsRepository) : GetProjectsListUseCase {
    override suspend fun invoke(
        query: String,
        sortType: ProjectsSortType,
        page: Int
    ): List<ProjectListItem> =
        projectsRepository.getProjectsFromApi(query, sortType, page).also {
            projectsRepository.saveProjectsToDatabase(it)
        }.map { it.toProjectListItem() }

}