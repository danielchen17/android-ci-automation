package com.fungate.automation

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import io.mockk.every
import io.mockk.mockkObject
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {

    private val context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val activityTestRule = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
        override fun getActivityIntent(): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    @Before
    fun setup() {
        mockkObject(MainActivity)
        every { MainActivity.temp }.returns("XYZ")

        val server = MockWebServer()
        server.enqueue(MockResponse().apply {
            setResponseCode(200)
            addHeader("Content-Type", "application/json;charset=utf-8")
            addHeader("Cache-Control", "no-cache")
            setBody("{}")
        })
        val serverUrl = server.url("")

    }

    @Test
    fun useAppContext() {



        onView(withId(R.id.textView)).perform(replaceText(MainActivity.temp)).check(matches(withText("XYZ")))


        onView(withId(R.id.textView)).perform(replaceText("fdsafdf"))

        onView(withId(R.id.textView)).perform(replaceText("fdsafd121"))

        onView(withId(R.id.textView)).perform(replaceText("fds333afd121"))

        onView(withId(R.id.textView)).check(matches(CoreMatchers.allOf(isDisplayed(), isClickable()))).perform(click())

    }
}
