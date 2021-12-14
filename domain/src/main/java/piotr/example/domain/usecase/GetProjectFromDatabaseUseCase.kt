package piotr.example.domain.usecase

import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.value.ProjectListItem

interface GetProjectFromDatabaseUseCase {
    suspend fun invoke(projectId:Long) : Project?
}