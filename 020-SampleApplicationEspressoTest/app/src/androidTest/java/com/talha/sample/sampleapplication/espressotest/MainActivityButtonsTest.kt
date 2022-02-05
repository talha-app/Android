package com.talha.sample.sampleapplication.espressotest

import org.junit.Assert.*
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityButtonsTest {
    @Rule
    @JvmField
    val mainActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun ensureTestReversed() {
        val text = "abc"
        val expectedText ="cba"

        //write editText
        onView(withId(R.id.mainActivityEditTextMessage)).perform(typeText(text), closeSoftKeyboard())

        //press reverse button
        onView(withText("Reverse")).perform(click())

        // test text
        onView(withId(R.id.mainActivityEditTextMessage)).check(matches
            (withText(expectedText)))

    }

    @Test
    fun ensureMessageDetailActivityGetMessage(){
        val text = "abc"
        val expectedText ="CBA"

        //write editText
        onView(withId(R.id.mainActivityEditTextMessage)).perform(typeText(text), closeSoftKeyboard())

        //press reverse button
        onView(withText("Reverse")).perform(click())

        //press open other activity button
        onView(withId(R.id.mainActivityButtonOpenMessageDetailActivity)).perform(click())

        onView(withId(R.id.otherActivityEditTextMessage)).check(matches(withText(expectedText)))

    }



}