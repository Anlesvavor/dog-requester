package com.example.linearandconstraintlayouts

import android.view.View
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import com.example.linearandconstraintlayouts.R.id.button_detail_request_dog
import com.example.linearandconstraintlayouts.adapter.BreedListRecyclerViewAdapter
import com.example.linearandconstraintlayouts.adapter.DogListRecyclerViewAdapter
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class BreedRecyclerViewRendersTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun recyclerViewIsRendered() {
        onView(
            withId(R.id.breed_list_recycler_view)
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun clickOnABreedDetails() {
        sleep(5, TimeUnit.SECONDS)
        onView(
            withId(R.id.breed_list_recycler_view)
        ). perform(
            RecyclerViewActions.actionOnItemAtPosition<BreedListRecyclerViewAdapter.ItemViewHolder>(
                0,
                click()
            )
        )

        onView(
            withId(button_detail_request_dog)
        ). check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun requestADogAndThenViewItsDetails() {
        sleep(5, TimeUnit.SECONDS)
        onView(
            withId(R.id.breed_list_recycler_view)
        ). perform(
            RecyclerViewActions.actionOnItemAtPosition<BreedListRecyclerViewAdapter.ItemViewHolder>(
                0,
                click()
            )
        )

        onView(
            withText("Request dog")
        ). inRoot(
            isDialog()
        ) .perform(
            click()
        )

        onView(
            withId(R.id.dogFragment)
        ). perform(
            click()
        )

        sleep(2, TimeUnit.SECONDS)
        onView(
            withId(R.id.recycler_view_dog_list)
        ). perform(
            RecyclerViewActions.actionOnItemAtPosition<DogListRecyclerViewAdapter.ItemViewHolder>(
                0,
                clickChildViewWithId(R.id.button_dog_details)
            )
        )
    }

    @Test
    fun requestADogAndThenDismissAnother() {
        onView(
            withId(R.id.breed_list_recycler_view)
        ). perform(
            RecyclerViewActions.actionOnItemAtPosition<BreedListRecyclerViewAdapter.ItemViewHolder>(
                0,
                click()
            )
        )

        onView(
            withText("Request dog")
        ). inRoot(
            isDialog()
        ). perform(
            click()
        )

        onView(
            withId(R.id.dogFragment)
        ). perform(
            click()
        )

        sleep(2, TimeUnit.SECONDS)
        onView(
            withId(R.id.recycler_view_dog_list)
        ). perform(
            RecyclerViewActions.actionOnItemAtPosition<DogListRecyclerViewAdapter.ItemViewHolder>(
                0,
                clickChildViewWithId(R.id.button_dog_dismiss)
            )
        )
    }



    private fun clickChildViewWithId(id: Int): ViewAction {
        return object: ViewAction {
            override fun getConstraints() = null

            override fun getDescription() = "Click on a child view with specified id."

            override fun perform(uiController: UiController?, view: View) = click().perform(uiController, view.findViewById(id))

        }
    }
}