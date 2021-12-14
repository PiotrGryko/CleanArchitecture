package piotr.example.domain.repository

import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.value.ProjectsSortType


interface ProjectsRepository {

    suspend fun getProjectsFromApi(query:String, sort:ProjectsSortType, page:Int = 1) : List<Project>
    suspend fun saveProjectsToDatabase(projects: List<Project>)
    suspend fun getProjectFromDatabase(id:Long) : Project?

}