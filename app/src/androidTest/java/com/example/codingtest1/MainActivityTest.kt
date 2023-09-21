package com.example.codingtest1

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import okhttp3.internal.wait
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAddContactButton() {

        onView(withId(R.id.btnAddContact)).perform(click())

        onView(withId(R.id.etName)).perform(typeText("John"), closeSoftKeyboard())
        onView(withId(R.id.etNumber)).perform(typeText("123456789"), closeSoftKeyboard())
        onView(withId(R.id.etDescription)).perform(typeText("Friend"), closeSoftKeyboard())
        onView(withId(android.R.id.button1)).perform(click())

    }

}

