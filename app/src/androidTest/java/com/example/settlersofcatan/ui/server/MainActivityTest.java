package com.example.settlersofcatan.ui.server;

import androidx.test.annotation.UiThreadTest;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.FlakyTest;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.ui.color.ChooseColorActivity;
import com.example.settlersofcatan.ui.game.GameActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.repeatedlyUntil;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
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
    public void testClickCreateServerStartsGameActivity() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.createServerButton2)).perform(click());
        Thread.sleep(1000);
        intended(hasComponent(ChooseColorActivity.class.getName()));
        onView(ViewMatchers.withId(R.id.view_green)).perform(click());
        Thread.sleep(1000);
        intended(hasComponent(GameActivity.class.getName()));
    }

    @Test
    public void testClickConnectToServerStartsWaitForHostActivity() throws InterruptedException {
        onView(withId(R.id.view_pager)).perform(repeatedlyUntil(swipeLeft(), hasDescendant(withId(R.id.editTextServerIP)), 5));
        onView(withId(R.id.editTextServerIP)).perform(repeatedlyUntil(typeText("localhost"), hasFocus(), 5));
        onView(withId(R.id.editTextUsername)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.joinServerButton)).perform(click());
        Thread.sleep(500);

        intended(hasComponent(WaitForHostActivity.class.getName()));
        GameClient.getInstance().disconnect();
    }

    @Test
    public void testClickConnectToServerFailsOnInvalidServerIp() throws InterruptedException {
        onView(withId(R.id.view_pager)).perform(repeatedlyUntil(swipeLeft(), hasDescendant(withId(R.id.editTextServerIP)), 5));
        onView(withId(R.id.editTextServerIP)).perform(repeatedlyUntil(typeText("123.123.123.123"), hasFocus(), 5));
        onView(withId(R.id.editTextUsername)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.joinServerButton)).perform(click());
        Thread.sleep(6000);

        onView(withId(android.R.id.message)).check(matches(isDisplayed()));
    }
}
