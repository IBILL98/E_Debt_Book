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

public class MarketRegisterTest extends TestCase {


    String mahoMarket = "Mahasin Elderviş";
    String mahoMarketEmail = "mahaassssssin@gmail.com";
    String mahoMarketPhone = "0237715779";
    String mahoMarketPassword = "mahomaho";
    String mahoMarketIban = "5555888899996666";
    String mahoMarketAddress = "kavacık mahallesi";

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
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.marketRegisterIban)).perform(typeText(mahoMarketIban));
        Espresso.onView(withId(R.id.marketRegisterAdress)).perform(replaceText(mahoMarketAddress));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.marketRegisterSignUpButtom)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.marketRegisterSignUpButtom)).perform(click());
        Espresso.onView(withId(R.id.verificationLaterButton)).check(matches(isDisplayed())); //problem with number verification class
        Espresso.onView(withId(R.id.verificationLaterButton)).perform(click());
        onView(withId(R.id.market_home_constraint_layout)).check(matches(isDisplayed()));
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }
}