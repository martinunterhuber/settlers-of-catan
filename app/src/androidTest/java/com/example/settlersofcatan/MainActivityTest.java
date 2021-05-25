package com.example.settlersofcatan;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.settlersofcatan.server_client.WaitForHostActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void testClickCreateServerStartsGameActivity() {
        onView(withId(R.id.createServerButton2)).perform(click());

        intended(hasComponent(GameActivity.class.getName()));
    }

    @Test
    public void testClickConnectToServerStartsWaitForHostActivity() throws InterruptedException {
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        Thread.sleep(300);  // TODO: find a better way to wait for the animation
        onView(withId(R.id.editTextServerIP)).perform(typeText("localhost"), closeSoftKeyboard());
        onView(withId(R.id.editTextUsername)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.joinServerButton)).perform(click());

        intended(hasComponent(WaitForHostActivity.class.getName()));
    }

    @Test
    public void testClickConnectToServerFailsOnInvalidServerIp() throws InterruptedException {
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        Thread.sleep(300);
        onView(withId(R.id.editTextServerIP)).perform(typeText("123.123.123.123"), closeSoftKeyboard());
        onView(withId(R.id.editTextUsername)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.joinServerButton)).perform(click());
        Thread.sleep(6000);

        onView(withId(android.R.id.message)).check(matches(isDisplayed()));
    }
}
