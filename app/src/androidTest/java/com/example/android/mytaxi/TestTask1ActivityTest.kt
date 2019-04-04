package br.cericatto.openweathermap

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.android.mytaxi.R
import com.example.android.mytaxi.view.activity.test.TestTask1Activity
import com.example.android.mytaxi.view.dialog.LoadingDialogApiFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestTask1ActivityTest {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    @get:Rule
    var mActivityRule: ActivityTestRule<TestTask1Activity> = ActivityTestRule(TestTask1Activity::class.java)

    //--------------------------------------------------
    // Test
    //--------------------------------------------------

    @Test
    fun done() {
        val idlingResource = DialogFragmentIdlingResource(mActivityRule.activity.supportFragmentManager,
            LoadingDialogApiFragment.TAG)

        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.id_activity_test_task1__text_view)).check(matches(withText(R.string.dialog_ok)))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}