package piotr.example.data.db

import piotr.example.domain.model.entity.Project

interface ProjectsDatabase {
     suspend fun getProjectFromDatabase(id: Long) : Project?
     suspend fun saveProjectsToDatabase(projects: List<Project>)
}