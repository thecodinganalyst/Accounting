package com.hevlar.accounting.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardAccountTest {

    CreditCardAccount creditCard;

    @BeforeEach
    void setUp() {
        creditCard = new CreditCardAccount("Credit Card A", LocalDate.now(), "SGD", "0.0", "Bank A", 1, 13, false);
    }

    @Test
    void setStatementDay_within_boundaries() {
        assertFalse(creditCard.setStatementDay(0));
        assertTrue(creditCard.setStatementDay(2));
        assertFalse(creditCard.setStatementDay(32));
        creditCard.lock();
        assertTrue(creditCard.setStatementDay(2));
    }

    @Test
    void setDueDay_within_boundaries() {
        assertFalse(creditCard.setDueDay(0));
        assertTrue(creditCard.setDueDay(2));
        assertFalse(creditCard.setDueDay(32));
        creditCard.lock();
        assertTrue(creditCard.setDueDay(2));
    }
}