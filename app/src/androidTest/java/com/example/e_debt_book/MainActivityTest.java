package com.example.e_debt_book;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;

import junit.framework.TestCase;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest extends TestCase {


    String mahoMarket = "Mahasin Elderviş";
    String mahoMarketEmail = "mahassin@gmail.com";
    String mahoMarketPhone = "0236614789";
    String mahoMarketPassword = "mahomaho";
    String mahoMarketIban = "5555 8888 9999 6666";
    String mahoMarketAddress = "kavacık mahallesi";

    //Check if MainActivity is in view
    @Test
    public void test1_isMainActivityInView() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainChoicewindow)).check(matches(isDisplayed()));
    }

    @Test
    public void test_marketLogin () {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainChoicewindow)).check(matches(isDisplayed()));
        //check if main market button is visible
        onView(withId(R.id.mainMarketButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mainMarketButton)).perform(click());
        onView(withId(R.id.marketLoginEmail)).perform(typeText("abo@amo.com"));
        onView(withId(R.id.marketLoginPassword)).perform(typeText("kkkllooo"));
        onView(withId(R.id.marketLoginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.marketLoginButton)).perform(click());
        //onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
        //ActivityScenario<NumberVerification> activityScenario1 = ActivityScenario.launch(NumberVerification.class);
        //onView(withId(R.id.verificationLaterButton)).check(matches(isDisplayed()));
        //onView(withId(R.id.verificationLaterButton)).perform(click());
        ActivityScenario<MarketMain1> marketMain1ActivityScenario = ActivityScenario.launch(MarketMain1.class);
        onView(withId(R.id.addNewDebtButton2)).check(matches(isDisplayed()));
    }

    @Test
    public void test_marketRegister () {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainCustomertButton)).perform(click());
        onView(withId(R.id.customerBackButton)).perform(click());
        onView(withId(R.id.mainChoicewindow)).check(matches(isDisplayed()));
        onView(withId(R.id.mainMarketButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mainMarketButton)).perform(click());
        onView(withId(R.id.marketSignUpButton)).check(matches(isDisplayed()));
        onView(withId(R.id.marketSignUpButton)).perform(click());
        onView(withId(R.id.marketRegisterSignUpButtom)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.marketRegisterName)).perform(replaceText(mahoMarket));
        Espresso.onView(withId(R.id.marketRegisterEmail)).perform(typeText(mahoMarketEmail));
        Espresso.onView(withId(R.id.marketRegisterPhone)).perform(typeText(mahoMarketPhone));
        Espresso.onView(withId(R.id.marketRegisterPassword)).perform(typeText(mahoMarketPassword));
        Espresso.onView(withId(R.id.marketRegisterIban)).perform(typeText(mahoMarketIban));
        Espresso.onView(withId(R.id.marketRegisterAdress)).perform(replaceText(mahoMarketAddress));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.marketRegisterSignUpButtom)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.marketRegisterSignUpButtom)).perform(click());
        ActivityScenario<NumberVerification> numberVerificationActivityScenario = ActivityScenario.launch(NumberVerification.class);
        Espresso.onView(withId(R.id.verificationButtom)).check(matches(isDisplayed()));
    }

    @Test
    public void test_customerRegister() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        Espresso.onView(withId(R.id.mainCustomertButton)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.mainCustomertButton)).perform(click());
        onView(withId(R.id.customerBackButton)).perform(click());
        Espresso.onView(withId(R.id.mainCustomertButton)).perform(click());
        Espresso.onView(withId(R.id.customertSignUpButton)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.customertSignUpButton)).perform(click());
        Espresso.onView(withId(R.id.customerRegisterName)).perform(typeText("Mahasin"));
        Espresso.onView(withId(R.id.customerRegisterLastName)).perform(typeText("Eldervis"));
        Espresso.onView(withId(R.id.customerRegisterPassword)).perform(typeText("mahasin"));
        Espresso.onView(withId(R.id.customerRegisterPhone)).perform(typeText("0236514789"));
        Espresso.onView(withId(R.id.customerRegisterEmail)).perform(typeText("mahasin@gmail.com"));
        Espresso.onView(withId(R.id.customerRegisterSignUpButtom)).perform(click());
        //Espresso.onView(withId(R.id.nav_customer_home)).check(matches(isDisplayed()));

    }

    @Test
    public void test_customerLogin () {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainChoicewindow)).check(matches(isDisplayed()));
        //check if main market button is visible
        onView(withId(R.id.mainCustomertButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mainCustomertButton)).perform(click());
        onView(withId(R.id.customerLogin));
        onView(withId(R.id.customerLoginEmail)).perform(typeText("hello@gmail.com"));
        onView(withId(R.id.customerLoginPassword)).perform(typeText(""));
    }




    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }
}