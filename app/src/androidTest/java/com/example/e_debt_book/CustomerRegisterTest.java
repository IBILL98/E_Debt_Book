package com.example.e_debt_book;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class CustomerRegisterTest {

    @Rule
    public ActivityTestRule<CustomerRegister> customerRegisterActivityTestRule = new ActivityTestRule<CustomerRegister>(CustomerRegister.class);

    @Test
    public void testCustomerNameInputScenario() {
        //input the name
        Espresso.onView(withId(R.id.customerRegisterName)).perform(typeText("Johnny"));
        //close keyboard
        Espresso.closeSoftKeyboard();
        //click sign up
        Espresso.onView(withId(R.id.customerRegisterSignUpButtom)).perform(click());
        //have the error
        Espresso.onView(withId(R.id.customerRegisterProgressBar)).check(matches(isDisplayed()));
    }
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}