package piotr.example.domain

import junit.framework.Assert
import org.junit.Test

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.entity.ProjectOwner
import piotr.example.domain.repository.ProjectsRepository
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCaseImpl
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GetProjectFromDatabaseUseCaseTest {

    @MockK
    lateinit var projectsRepository: ProjectsRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @Test
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

        val useCase = GetProjectFromDatabaseUseCaseImpl(projectsRepository)

        coEvery { projectsRepository.getProjectFromDatabase(projectId) }.coAnswers { project }
        runTest {
            val result = useCase.invoke(projectId)
            coVerify(exactly = 1) { projectsRepository.getProjectFromDatabase(projectId) }
            Assert.assertEquals(result, project)
        }
    }
}