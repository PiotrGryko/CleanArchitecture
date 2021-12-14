package piotr.example.data.repository


import android.util.Log
import piotr.example.data.api.GitApi
import piotr.example.data.api.toProject
import piotr.example.data.db.ProjectsRoomDatabase
import piotr.example.data.db.toProject
import piotr.example.data.db.toProjectEntity
import piotr.example.domain.Constants
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.domain.repository.ProjectsRepository

class ProjectsRepositoryImpl(
    val api: GitApi,
    val db: ProjectsRoomDatabase
) : ProjectsRepository {
    override suspend fun getProjectsFromApi(query:String, sort:ProjectsSortType, page:Int): List<Project> {
        return api.getProjects(query,sort.name, Constants.API_ELEMENTS_PER_PAGE, page).items.map { it.toProject() }
    }

    override suspend fun getProjectFromDatabase(id: Long) : Project? {
       return db.bookDao().getSavedBookById(id)?.toProject()
    }

    override suspend fun saveProjectsToDatabase(projects: List<Project>) {
       db.bookDao().saveBooks(projects.map { it.toProjectEntity() })
    }
}