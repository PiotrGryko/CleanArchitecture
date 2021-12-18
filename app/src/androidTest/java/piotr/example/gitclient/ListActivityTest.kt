package piotr.example.gitclient

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import piotr.example.gitclient.list.ListActivity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ListActivityTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
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
}