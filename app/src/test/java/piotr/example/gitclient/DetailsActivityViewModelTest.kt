package piotr.example.gitclient

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import piotr.example.domain.Constants
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.entity.ProjectOwner
import piotr.example.domain.repository.ProjectsRepository
import piotr.example.gitclient.details.DetailsActivityViewModel
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DetailsActivityViewModelTest {


    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val liveDataRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var projectsRepository: ProjectsRepository

    val projectId = 1235L
    val project = Project(
        projectId,
        "name",
        "fullName",
        true,
        Date(),
        ProjectOwner(132L, "", null, "")
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testViewModelFields() {
        coEvery { projectsRepository.getProjectFromDatabase(projectId) }.coAnswers { project }

        val savedStateHandle= SavedStateHandle()
        savedStateHandle.set(Constants.KEY_PROJECT_ID,projectId)
        runTest {
            val viewModel = DetailsActivityViewModel(projectsRepository,savedStateHandle)
            viewModel.projectName.observeForever { assertEquals(project.name,it) }
            viewModel.ownerName.observeForever { assertEquals(project.owner.login,it) }
            viewModel.avatar.observeForever { assertEquals(project.owner.avatarUrl,it) }
            viewModel.creationDate.observeForever { assertEquals(project.createdAt.toString(),it) }
            viewModel.private.observeForever { assertEquals(project.private.toString(),it) }
        }
    }


}