package piotr.example.gitclient

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.InstrumentationRegistry.getTargetContext
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
import piotr.example.domain.Constants
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.entity.ProjectOwner
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.domain.model.value.toProjectListItem
import piotr.example.domain.repository.ProjectsRepository
import piotr.example.domain.usecase.GetProjectFromDatabaseUseCase
import piotr.example.domain.usecase.GetProjectsListUseCase
import piotr.example.domain.usecase.GetProjectsListUseCaseImpl
import piotr.example.gitclient.details.DetailsActivity
import piotr.example.gitclient.di.AppModule
import piotr.example.gitclient.list.ListActivity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.jvm.internal.impl.load.java.Constant


@UninstallModules(AppModule::class)
@HiltAndroidTest
class DetailsActivityTest {

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
        fun provideGetProjectsListsUseCase(): GetProjectsListUseCase = mockk<GetProjectsListUseCase>()

        @Singleton
        @Provides
        fun provideGetProjectFromDatabaseUseCase(): GetProjectFromDatabaseUseCase = mockk<GetProjectFromDatabaseUseCase>().also {
            coEvery { it.invoke(any()) }.coAnswers { project }
        }

    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun init() {
        hiltRule.inject()
    }


    @Test
    fun testProjectInfoIsDisplayed() {
        val intent = Intent(getTargetContext(), DetailsActivity::class.java)
        intent.putExtra(Constants.KEY_PROJECT_ID, project.id)
        launchActivity<DetailsActivity>(intent)
        onView(withId(R.id.ownerName)).check(ViewAssertions.matches(withText(project.owner.login)))
        onView(withId(R.id.projectName)).check(ViewAssertions.matches(withText(project.name)))
        onView(withId(R.id.creationDate)).check(ViewAssertions.matches(withText(project.createdAt.toString())))
        onView(withId(R.id.isPrivate)).check(ViewAssertions.matches(withText(project.private.toString())))

    }
}