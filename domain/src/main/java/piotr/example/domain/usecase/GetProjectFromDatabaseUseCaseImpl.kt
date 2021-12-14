package piotr.example.domain.usecase

import piotr.example.domain.model.entity.Project
import piotr.example.domain.repository.ProjectsRepository
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.domain.model.value.toProjectListItem

class GetProjectFromDatabaseUseCaseImpl(val projectsRepository: ProjectsRepository) : GetProjectFromDatabaseUseCase {
    override suspend fun invoke(projectId: Long): Project? {
        return projectsRepository.getProjectFromDatabase(projectId)
    }
}