package piotr.example.gitclient.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import piotr.example.data.api.GitApi
import piotr.example.data.db.ProjectsRoomDatabase
import piotr.example.data.repository.ProjectsRepositoryImpl
import piotr.example.domain.repository.ProjectsRepository
import piotr.example.domain.usecase.GetProjectsListUseCaseImpl
import piotr.example.domain.usecase.GetProjectsListUseCase
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCase
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideProjectsDb(application: Application) : ProjectsRoomDatabase {
        return ProjectsRoomDatabase.create(application)
    }

    @Singleton
    @Provides
    fun provideProjectsRepository(gitApi: GitApi, projectsDatabase: ProjectsRoomDatabase) : piotr.example.domain.repository.ProjectsRepository {
        return ProjectsRepositoryImpl(gitApi,projectsDatabase)
    }


    @Provides
    @Singleton
    fun provideGetProjectsFirstPageUseCase(projectsRepository: ProjectsRepository) : GetProjectsListUseCase{
        return GetProjectsListUseCaseImpl(projectsRepository)
    }

    @Provides
    @Singleton
    fun provideGetProjectsNextPageUseCase(projectsRepository: ProjectsRepository) : GetProjectFromDatabaseUseCase{
        return GetProjectFromDatabaseUseCaseImpl(projectsRepository)
    }
}