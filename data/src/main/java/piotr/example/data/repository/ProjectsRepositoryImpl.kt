package piotr.example.data.repository

import piotr.example.data.api.GitApi
import piotr.example.data.api.toProject
import piotr.example.data.db.ProjectsDatabase
import piotr.example.domain.Constants
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.domain.repository.ProjectsRepository

class ProjectsRepositoryImpl(
    private val api: GitApi,
    private val db: ProjectsDatabase
) : ProjectsRepository {

    override suspend fun getProjectsFromApi(query: String, sort: ProjectsSortType, page: Int): List<Project> {
        return api.getProjects(query, sort.apiName, Constants.API_ELEMENTS_PER_PAGE, page).items.map { it.toProject() }
    }

    override suspend fun getProjectFromDatabase(id: Long): Project? {
        return db.getProjectFromDatabase(id)
    }

    override suspend fun saveProjectsToDatabase(projects: List<Project>) {
        db.saveProjectsToDatabase(projects)
    }
}