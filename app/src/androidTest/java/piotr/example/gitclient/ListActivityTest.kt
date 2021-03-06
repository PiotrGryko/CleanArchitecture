package piotr.example.gitclient

import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import piotr.example.data.api.GitApi
import piotr.example.data.db.ProjectsDatabase
import piotr.example.data.repository.ProjectsRepositoryImpl
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.entity.ProjectOwner
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.domain.model.value.toProjectListItem
import piotr.example.domain.repository.ProjectsRepository
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCase
import piotr.example.domain.usecase.GetProjectsListUseCase
import piotr.example.domain.usecase.GetProjectsListUseCaseImpl
import piotr.example.gitclient.di.AppModule
import piotr.example.gitclient.list.ListActivity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@UninstallModules(AppModule::class)
@HiltAndroidTest
class ListActivityTest {


    companion object {
        val project = Project(
            1235L,
            "name",
            "fullName",
            true,
            Date(),
            ProjectOwner(132L, "", "", "")
        )
    }

    @Module
    @InstallIn(SingletonComponent::class)
    class TestModule {

        @Singleton
        @Provides
        fun provideGetProjectsListsUseCase(): GetProjectsListUseCase {
            val useCase = mockk<GetProjectsListUseCase>()
            coEvery { useCase.invoke(any(), any(), 1) }.coAnswers { listOf(project.toProjectListItem()) }
            coEvery { useCase.invoke(any(), any(), 2) }.coAnswers { emptyList() }
            return useCase
        }

        @Singleton
        @Provides
        fun provideGetProjectFromDatabaseUseCase(): GetProjectFromDatabaseUseCase = mockk<GetProjectFromDatabaseUseCase>().also {
            coEvery { it.invoke(any()) }.coAnswers { null }
        }

    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("piotr.example.gitclient", appContext.packageName)
    }

    @Test
    fun testListActivityPlaceholder() {
        launchActivity<ListActivity>()
        onView(withId(R.id.placeholder))
            .check(ViewAssertions.matches(withText(R.string.list_placeholder)))
            .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }


    @Test
    fun testSearchResults() {
        launchActivity<ListActivity>()
            onView(withId(R.id.searchView)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))
            onView(withId(R.id.search_button)).perform(click())
            onView(withId(R.id.search_src_text))
                .perform(typeText("text"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(R.id.recyclerView)).check(ViewAssertions.matches(hasChildCount(1)))
        onView(withId(R.id.labelName)).check(ViewAssertions.matches(withText(project.name)))

    }
}