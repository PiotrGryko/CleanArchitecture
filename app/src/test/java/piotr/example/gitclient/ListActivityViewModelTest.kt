package piotr.example.gitclient

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import piotr.example.domain.Constants
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.entity.ProjectOwner
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.domain.model.value.toProjectListItem
import piotr.example.domain.repository.ProjectsRepository
import piotr.example.domain.usecase.GetProjectsListUseCase
import piotr.example.gitclient.details.DetailsActivityViewModel
import piotr.example.gitclient.list.ListActivityViewModel
import java.util.*

class ListActivityViewModelTest {


    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val liveDataRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getProjectsListUseCase: GetProjectsListUseCase

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
    fun testSearchQuery() {
        coEvery { getProjectsListUseCase.invoke(any(),any(),any()) }.coAnswers { listOf(project.toProjectListItem()) }
        val query = "test"
        runTest {
            val viewModel = ListActivityViewModel(getProjectsListUseCase)
            viewModel.projects.observeForever {
                viewModel.setQuery(query)
                coVerify(exactly = 1) { getProjectsListUseCase.invoke(query,any(),any()) }
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSorting() {
        coEvery { getProjectsListUseCase.invoke(any(),any(),any()) }.coAnswers { listOf(project.toProjectListItem()) }
        val query = "test"
        val sortType = ProjectsSortType.ISSUES
        runTest {
            val viewModel = ListActivityViewModel(getProjectsListUseCase)
            viewModel.projects.observeForever {
                viewModel.setQuery(query)
                coVerify(exactly = 1) { getProjectsListUseCase.invoke(query,ProjectsSortType.STARS,any()) }

                viewModel.setSortType(sortType)
                coVerify(exactly = 1) { getProjectsListUseCase.invoke(query,sortType,any()) }
            }
        }
    }

    @Test
    fun testPlaceholderVisible() {
        val viewModel = ListActivityViewModel(getProjectsListUseCase)
        viewModel.setPlaceholderVisibility(true)
        viewModel.placeholderVisible.observeForever{ assertEquals(true,it) }
    }
    @Test
    fun testPlaceholderHidden() {
        val viewModel = ListActivityViewModel(getProjectsListUseCase)
        viewModel.setPlaceholderVisibility(false)
        viewModel.placeholderVisible.observeForever{ assertEquals(false,it) }
    }

}