package com.example.e_debt_book;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;

import junit.framework.TestCase;

import org.junit.Test;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest extends TestCase {


    String mahoMarket = "Mahasin Elderviş";
    String mahoMarketEmail = "mahaassssin@gmail.com";
    String mahoMarketPhone = "0237714789";
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
        onView(withId(R.id.market_home_constraint_layout)).check(matches(isDisplayed()));
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
        Espresso.onView(withId(R.id.verificationLaterButton)).check(matches(isDisplayed())); //problem with number verification class
        Espresso.onView(withId(R.id.verificationLaterButton)).perform(click());
        onView(withId(R.id.market_home_constraint_layout)).check(matches(isDisplayed()));
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
        Espresso.onView(withId(R.id.customerRegisterPhone)).perform(typeText("0236714789"));
        Espresso.onView(withId(R.id.customerRegisterEmail)).perform(typeText("mahasein@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.customerRegisterSignUpButtom)).perform(click());
        Espresso.onView(withId(R.id.verificationLaterButton)).perform(click()); //problem with number verification class
        onView(withId(R.id.debtsListOfaCustomer)).check(matches(isDisplayed()));

    }

    @Test
    public void test_customerLogin () {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainChoicewindow)).check(matches(isDisplayed()));
        //check if main market button is visible
        onView(withId(R.id.mainCustomertButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mainCustomertButton)).perform(click());
        onView(withId(R.id.customerLoginEmail)).perform(typeText("hellothere@gmail.com"));
        onView(withId(R.id.customerLoginPassword)).perform(typeText("hellothere11"));
        onView(withId(R.id.verificationLaterButton)).perform(click()); //problem with number verification class
        onView(withId(R.id.debtsListOfaCustomer)).check(matches(isDisplayed()));
    }

    @Test
    public void test_addNewDebt() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainChoicewindow)).check(matches(isDisplayed()));
        //check if main market button is visible
        onView(withId(R.id.mainMarketButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mainMarketButton)).perform(click());
        onView(withId(R.id.marketLoginEmail)).perform(typeText("abo@amo.com"));
        onView(withId(R.id.marketLoginPassword)).perform(typeText("kkkllooo"));
        onView(withId(R.id.marketLoginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.marketLoginButton)).perform(click());
        //we have logged in
        onView(withId(R.id.mobile_navigation)).check(matches(isDisplayed()));
        onView(withId(R.id.addNewDebtButton2)).perform(click());
        onView(withId(R.id.selectedCustomerPhone)).perform(typeText("5963265114"));
        closeSoftKeyboard();
        onView(withId(R.id.customerSelectButton)).perform(click());
        onView(withId(R.id.loanAmountInput)).perform(typeText("25"));
        onView(withId(R.id.descriptionInput)).perform(typeText("this is your debt"));
        onView(withId(R.id.dateOfLoanInput)).perform(typeText("15.02.2021"));
        onView(withId(R.id.dueDateInput)).perform(typeText("15.03.2021"));
        onView(withId(R.id.itemNameInput)).perform(typeText("pop kek"));
        onView(withId(R.id.itemPriceInput)).perform(typeText("2"));
        onView(withId(R.id.addProductButton)).perform(click());
        onView(withId(R.id.itemNameInput)).perform(typeText("milk"));
        onView(withId(R.id.itemPriceInput)).perform(typeText("4"));
        onView(withId(R.id.addProductButton)).perform(click());
        onView(withId(R.id.itemNameInput)).perform(typeText("pepsi"));
        onView(withId(R.id.itemPriceInput)).perform(typeText("7"));
        onView(withId(R.id.addProductButton)).perform(click());
        onView(withId(R.id.itemNameInput)).perform(typeText("toilet paper"));
        onView(withId(R.id.itemPriceInput)).perform(typeText("12"));
        onView(withId(R.id.addProductButton)).perform(click());
        onView(withId(R.id.addDebtButton)).perform(click());
        onView(withId(R.id.addNewDebtButton2)).check(matches(isDisplayed())); //check if we are back in market main
    }

    @Test
    public void test_addCustomerFromMarket() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainChoicewindow)).check(matches(isDisplayed()));
        //check if main market button is visible
        onView(withId(R.id.mainMarketButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mainMarketButton)).perform(click());
        onView(withId(R.id.marketLoginEmail)).perform(typeText("abo@amo.com"));
        onView(withId(R.id.marketLoginPassword)).perform(typeText("kkkllooo"));
        onView(withId(R.id.marketLoginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.marketLoginButton)).perform(click());
        onView(withId(R.id.toolbar)).perform(click());
        onView(withId(R.id.nav_add_customer)).perform(click());
        onView(withId(R.id.customerRegisterNamefromMarket)).perform(typeText("Nare"));
        onView(withId(R.id.customerRegisterLastNamefromMarket)).perform(typeText("shelebi"));
        onView(withId(R.id.customerRegisterPhonefromMarket)).perform(typeText("569328741"));
        onView(withId(R.id.customerRegisterEmailfromMarket)).perform(typeText("nareshelebi@gmail.com"));
        onView(withId(R.id.customerRegisterSignUpButtomfromMarket)).perform(click());
    }

    @Test
    public void test_changeEmailMarket() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainChoicewindow)).check(matches(isDisplayed()));
        //check if main market button is visible
        onView(withId(R.id.mainMarketButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mainMarketButton)).perform(click());
        onView(withId(R.id.marketLoginEmail)).perform(typeText("abo@amo.com"));
        onView(withId(R.id.marketLoginPassword)).perform(typeText("kkkllooo"));
        onView(withId(R.id.marketLoginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.marketLoginButton)).perform(click());
        onView(withId(R.id.market_home_constraint_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).perform(click());
        onView(withId(R.id.nav_market_settings)).perform(click());
        onView(withId(R.id.market_settings_change_email)).perform(click());
        //onView(withId(R.id.EditMarketEmailNewEmail)).perform(typeText("abo@ato.com")); //this will change our email
        onView(withId(R.id.EditMarketEmailDoneButton)).perform(click());
    }

    @Test
    public void test_changePasswordMarket() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainChoicewindow)).check(matches(isDisplayed()));
        //check if main market button is visible
        onView(withId(R.id.mainMarketButton)).check(matches(isDisplayed()));
        onView(withId(R.id.mainMarketButton)).perform(click());
        onView(withId(R.id.marketLoginEmail)).perform(typeText("abo@amo.com"));
        onView(withId(R.id.marketLoginPassword)).perform(typeText("kkkllooo"));
        onView(withId(R.id.marketLoginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.marketLoginButton)).perform(click());
        onView(withId(R.id.market_home_constraint_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).perform(click());
        onView(withId(R.id.nav_market_settings)).perform(click());
        onView(withId(R.id.market_settings_change_password)).perform(click());
        //onView(withId(R.id.EditMarketPassNewPassword)).perform(typeText("kkklllooo")); //this will change our password
        //onView(withId(R.id.EditMarketPassNewPasswordConfirmation)).perform(typeText("kkklllooo"));
        onView(withId(R.id.EditMarketPassDoneButton)).perform(click());
    }




    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }
}