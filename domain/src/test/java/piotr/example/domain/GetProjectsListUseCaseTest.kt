package piotr.example.domain

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.entity.ProjectOwner
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.domain.model.value.toProjectListItem
import piotr.example.domain.repository.ProjectsRepository
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCaseImpl
import piotr.example.domain.usecase.GetProjectsListUseCaseImpl
import java.util.*

class GetProjectsListUseCaseTest {

    @MockK
    lateinit var projectsRepository: ProjectsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testGetProjectsListUseCase()
    {
        val project = Project(
            1235L,
            "name",
            "fullName",
            true,
            Date(),
            ProjectOwner(132L, "", "", "")
        )

        val useCase = GetProjectsListUseCaseImpl(projectsRepository)
        val query = "q"
        val type = ProjectsSortType.STARS
        val page = 1

        coEvery { projectsRepository.getProjectsFromApi(query,type,page) }.coAnswers { listOf(project) }
        coEvery { projectsRepository.saveProjectsToDatabase(any()) }.coAnswers {  }

        runTest {
            val result = useCase.invoke(query,type,page)
            coVerify(exactly = 1) { projectsRepository.getProjectsFromApi(query,type,page) }
            coVerify(exactly = 1) { projectsRepository.saveProjectsToDatabase(listOf(project)) }
            Assert.assertEquals(result, listOf(project.toProjectListItem()))
        }
    }
}