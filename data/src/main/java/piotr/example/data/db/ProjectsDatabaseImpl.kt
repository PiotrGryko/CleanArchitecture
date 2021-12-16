package piotr.example.data.db

import piotr.example.domain.model.entity.Project

class ProjectsDatabaseImpl(val db : ProjectsRoomDatabase) : ProjectsDatabase {
    override suspend fun getProjectFromDatabase(id: Long): Project? {
        return db.bookDao().getSavedBookById(id)?.toProject()
    }

    override suspend fun saveProjectsToDatabase(projects: List<Project>) {
        db.bookDao().saveBooks(projects.map { it.toProjectEntity() })
    }
}