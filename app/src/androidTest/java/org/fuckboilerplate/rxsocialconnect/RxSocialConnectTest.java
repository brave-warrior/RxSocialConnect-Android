package org.fuckboilerplate.rxsocialconnect;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;

/**
 * Run the test uninstalling the app previously and removing permissions for the app from every facebook user profile.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class RxSocialConnectTest {

    @Test public void _1_Connect_With_Facebook() {
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(true)));

        onView(withId(R.id.bt_facebook)).perform(click());

        waitTime();
        rotateDevice();

        onWebView()
                .withElement(findElement(Locator.NAME, "email"))
                .perform(DriverAtoms.webKeys(Credentials.EMAIL))
                .withElement(findElement(Locator.NAME, "pass"))
                .perform(DriverAtoms.webKeys(Credentials.PASSWORD))
                .withElement(findElement(Locator.NAME, "login"))
                .perform(webClick())
                .withElement(findElement(Locator.NAME, "__CONFIRM__"))
                .perform(webClick());

        waitTime();
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(false)));
    }

    @Test public void _2_Connected_Facebook() {
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(true)));
        onView(withId(R.id.bt_facebook)).perform(click());
        waitTime();
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(false)));
    }

    @Test public void _3_Connect_With_Google() {
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(true)));

        onView(withId(R.id.bt_google)).perform(click());

        waitTime();
        rotateDevice();

        onWebView()
                .withElement(findElement(Locator.NAME, "Email"))
                .perform(DriverAtoms.webKeys(Credentials.EMAIL))
                .withElement(findElement(Locator.NAME, "signIn"))
                .perform(webClick());
        waitTime();

        onWebView()
                .withElement(findElement(Locator.NAME, "Passwd"))
                .perform(DriverAtoms.webKeys(Credentials.PASSWORD))
                .withElement(findElement(Locator.ID, "signIn"))
                .perform(webClick());

        waitTime();
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(false)));
    }

    @Test public void _4_Connected_Google() {
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(true)));
        onView(withId(R.id.bt_google)).perform(click());
        waitTime();
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(false)));
    }

    @Test public void _5_Connect_With_LinkedIn() {
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(true)));

        onView(withId(R.id.bt_linkedin)).perform(click());

        waitTime();
        rotateDevice();

        onWebView()
                .withElement(findElement(Locator.NAME, "session_key"))
                .perform(DriverAtoms.webKeys(Credentials.EMAIL))
                .withElement(findElement(Locator.NAME, "session_password"))
                .perform(DriverAtoms.webKeys(Credentials.PASSWORD))
                .withElement(findElement(Locator.NAME, "authorize"))
                .perform(webClick());

        waitTime();
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(false)));
    }

    @Test public void _6_Connected_LinkedIn() {
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(true)));
        onView(withId(R.id.bt_linkedin)).perform(click());
        waitTime();
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(false)));
    }

    @Test public void _7_Disconnect_All() {
        onView(withId(R.id.bt_all_disconnect)).perform(click());
        waitTime();
    }

    @Test public void _8_LinkedIn_Has_Been_Disconnected() {
        onView(withId(R.id.tv_token)).check(matches(shouldBeEmpty(true)));
        onView(withId(R.id.bt_linkedin)).perform(click());
        onView(withId(R.id.webview)).check(matches(isDisplayed()));
    }

/*    @Test public void Connect_With_Twitter() {
        //Can not be performed due to security restrictions.
        //Exception: Caused by: java.lang.RuntimeException: Fatal exception checking document state: Evaluation: status: 13 value: {message=Refused to evaluate a string as JavaScript because 'unsafe-eval' is not an allowed source of script in the following Content Security Policy directive: "script-src https://abs.twimg.com https://abs-0.twimg.com https://twitter.com https://mobile.twitter.com".
        //} hasMessage: true message: Refused to evaluate a string as JavaScript because 'unsafe-eval' is not an allowed source of script in the following Content Security Policy directive: "script-src https://abs.twimg.com https://abs-0.twimg.com https://twitter.com https://mobile.twitter.com".
    }*/

    private Matcher<View> shouldBeEmpty(boolean shouldBeEmpty) {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override public void describeTo(Description description) {
                description.appendText("no empty text: ");
            }

            @Override public boolean matchesSafely(TextView textView) {
                String text = textView.getText().toString();
                if (shouldBeEmpty) return text.isEmpty();
                else return !text.isEmpty();
            }
        };
    }

    private void waitTime() {
        try {Thread.sleep(3500);}
        catch (InterruptedException e) { e.printStackTrace();}
    }

    private void rotateDevice() {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        try {
            uiDevice.setOrientationLeft();
            waitTime();
            uiDevice.setOrientationNatural();
            waitTime();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}