package piotr.example.data

import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import piotr.example.data.api.GitApi
import piotr.example.data.api.ProjectsResponse
import piotr.example.data.db.ProjectsDatabase
import piotr.example.data.repository.ProjectsRepositoryImpl
import piotr.example.domain.Constants
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.entity.ProjectOwner
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.domain.repository.ProjectsRepository
import java.util.*

class ProjectsRepositoryTests {

    @MockK
    lateinit var api: GitApi

    @MockK
    lateinit var db: ProjectsDatabase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @Test
    @ExperimentalCoroutinesApi
    fun testGetProjectsFromApi() {
        val apiResponse = ProjectsResponse(emptyList())
        coEvery { api.getProjects(any(), any(), any(), any()) }.coAnswers { apiResponse }

        val repository = ProjectsRepositoryImpl(api, db)
        val query = "q"
        val type = ProjectsSortType.STARS
        val page = 1

        runTest {
            val result = repository.getProjectsFromApi(query, type, page)
            coVerify(exactly = 1) { api.getProjects(query, type.name, Constants.API_ELEMENTS_PER_PAGE, page) }
            assertEquals(result, apiResponse.items)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testGetProjectFromDatabase() {
        val projectId = 1235L
        val project = Project(
            projectId,
            "name",
            "fullName",
            true,
            Date(),
            ProjectOwner(132L, "", "", "")
        )
        coEvery { db.getProjectFromDatabase(projectId) }.coAnswers { project }
        val repository = ProjectsRepositoryImpl(api, db)

        runTest {
            val result = repository.getProjectFromDatabase(projectId)
            coVerify(exactly = 1) { db.getProjectFromDatabase(projectId) }
            assertEquals(result, project)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testSaveProjectsToDatabase() {
        val projectId = 1235L
        val project = Project(
            projectId,
            "name",
            "fullName",
            true,
            Date(),
            ProjectOwner(132L, "", "", "")
        )
        coEvery { db.saveProjectsToDatabase(listOf(project)) }.coAnswers {  }
        val repository = ProjectsRepositoryImpl(api, db)

        runTest {
            repository.saveProjectsToDatabase(listOf(project))
            coVerify(exactly = 1) { db.saveProjectsToDatabase(listOf(project)) }
        }
    }
}