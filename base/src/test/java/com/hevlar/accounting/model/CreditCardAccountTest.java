package com.hevlar.accounting.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardAccountTest {

    CreditCardAccount creditCard;

    @BeforeEach
    void setUp() {
        creditCard = new CreditCardAccount("Credit Card A", LocalDate.now(), "SGD", "0.0", "Bank A", 1, 13, false);
    }

    @Test
    void setBank_correctly(){
        assertTrue(creditCard.setBank("Bank A1"));
        assertEquals(creditCard.getBank(), "Bank A1");
        creditCard.lock();
        assertFalse(creditCard.setBank("Bank B"));
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

    @Test
    void creditCardAccount_cannot_have_blank_bank(){
        assertThrows(NullPointerException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", "100.0", "", 1, 12, false));
        assertThrows(NullPointerException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", "100.0", "   ", 1, 12, false));
        assertThrows(NullPointerException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", "100.0", null, 1, 12, false));
        assertThrows(NullPointerException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", new BigDecimal("100.0"), "", 1, 12, false));
        assertThrows(NullPointerException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", new BigDecimal("100.0"), "   ", 1, 12, false));
        assertThrows(NullPointerException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", new BigDecimal("100.0"), null, 1, 12, false));
    }

    @Test
    void creditCardAccount_statement_day(){
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", "100.0", "Bank", 0, 12, false));
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", "100.0", "Bank", 32, 12, false));
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", new BigDecimal("100.0"), "Bank", 0, 12, false));
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", new BigDecimal("100.0"), "Bank", 32, 12, false));
    }

    @Test
    void creditCardAccount_due_day(){
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", "100.0", "Bank", 1, 0, false));
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", "100.0", "Bank", 12, 32, false));
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", new BigDecimal("100.0"), "Bank", 1, 0, false));
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCardAccount("Credit Card 1", LocalDate.now(), "SGD", new BigDecimal("100.0"), "Bank", 12, 32, false));
    }
}