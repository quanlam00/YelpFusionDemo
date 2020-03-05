package com.quan.lam.yelpfusiondemo

import android.os.Build
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*

/**
 * Custom assertion class to check Recycler view item count
 */
class RecyclerViewItemCountAssertion : ViewAssertion {
    private val matcher: Matcher<Int>
    constructor(expectedCount: Int) {
        this.matcher = Matchers.`is`(expectedCount)
    }

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, matcher)
    }

}
/**
 * Movie Review List Fragment UI Automation test
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testOnFragmentLifeCycle() {
        val scenario = launchActivity<MainActivity>()
        scenario.moveToState(Lifecycle.State.CREATED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scenario.moveToState(Lifecycle.State.STARTED)
        }
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun testListViewLoaded() {
        val scenario = launchActivity<MainActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.editText)).perform(typeText("pizza"))
        onView(withId(R.id.locationText)).perform(typeText("new york"))
        onView(withId(R.id.searchButton)).perform(click())
        //Give enough time for result to be returned successfully
        Thread.sleep(7000)
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(20))
    }

    @Test
    fun testListViewLoadMore() {
        val scenario = launchActivity<MainActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.editText)).perform(typeText("pizza"))
        onView(withId(R.id.locationText)).perform(typeText("new york"))
        onView(withId(R.id.searchButton)).perform(click())
        //Give enough time for result to be returned successfully
        Thread.sleep(7000)
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(20))

        onView(withId(R.id.recyclerView))
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
            .perform(ViewActions.swipeUp())
        //Give enough time for result to be returned successfully
        Thread.sleep(7000)
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(40))
    }

    @Test
    fun testListViewClicked() {
        val scenario = launchActivity<MainActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.editText)).perform(typeText("pizza"))
        onView(withId(R.id.locationText)).perform(typeText("new york"))
        onView(withId(R.id.searchButton)).perform(click())
        //Give enough time for result to be returned successfully
        Thread.sleep(7000)
        onView(withId(R.id.recyclerView)).
            perform(click())
        //Give enough time for result to be returned successfully
        Thread.sleep(5000)
        onView(withId(R.id.nameText)).check(ViewAssertions.matches(withText("Lucali")))
    }
}