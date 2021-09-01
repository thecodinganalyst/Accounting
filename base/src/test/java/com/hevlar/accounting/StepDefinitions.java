package com.hevlar.accounting;

import com.hevlar.accounting.model.Account;
import com.hevlar.accounting.model.AccountGroup;
import com.hevlar.accounting.model.IncomeStatementAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    private final Account account = new IncomeStatementAccount("Food", AccountGroup.EXPENSES, false);
    private String actualAnswer;

    @Given("account is locked")
    public void account_is_locked() {
        // Write code here that turns the phrase above into concrete actions
        account.lock();
    }

    @When("I try to edit the locked account's attributes")
    public void i_try_to_edit_the_locked_account_s_attributes() {
        // Write code here that turns the phrase above into concrete actions
        actualAnswer = account.setName("Grocery") ? "pass" : "fail";
    }

    @Then("I should {string}")
    public void i_should(String expectedAnswer) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Given("account is not locked")
    public void accountIsNotLocked() {
        // Do nothing as account is not locked by default
    }
}
