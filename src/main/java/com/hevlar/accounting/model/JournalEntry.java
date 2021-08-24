package com.hevlar.accounting.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents a journal entry, ie transaction record
 */
public class JournalEntry {

    private final AtomicLong journalId;
    private LocalDate txDate;
    private String item;
    private Recurrence recurrence;
    private String[] tags;
    private Currency currency;
    private BigDecimal amount;
    private Account debit;
    private Account credit;
    private LocalDate postDate;
    private LocalDate debitStatementDate;
    private LocalDate creditStatementDate;
    private AtomicBoolean locked;

    public JournalEntry(AtomicLong journalId, LocalDate txDate, String item, Recurrence recurrence, String[] tags, String currency, Double amount, Account debit, Account credit, LocalDate postDate, LocalDate debitStatementDate, LocalDate creditStatementDate) {
        this.journalId = journalId;
        this.txDate = txDate;
        this.item = item;
        this.recurrence = recurrence;
        this.tags = tags;
        this.currency = Currency.getInstance(currency);
        this.amount = new BigDecimal(amount);
        this.debit = debit;
        this.credit = credit;
        this.postDate = postDate;
        this.debitStatementDate = debitStatementDate;
        this.creditStatementDate = creditStatementDate;
        this.locked = new AtomicBoolean(false);
    }

    public AtomicLong getJournalId() {
        return journalId;
    }

    public LocalDate getTxDate() {
        return txDate;
    }

    public String getItem() {
        return item;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public String[] getTags() {
        return tags;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Account getDebit() {
        return debit;
    }

    public Account getCredit() {
        return credit;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public LocalDate getDebitStatementDate() {
        return debitStatementDate;
    }

    public LocalDate getCreditStatementDate() {
        return creditStatementDate;
    }

    public Boolean isLocked(){
        return locked.get();
    }

    public void lock(){
        locked.set(true);
    }

    public void setTxDate(LocalDate txDate) {
        this.txDate = txDate;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDebit(Account debit) {
        this.debit = debit;
    }

    public void setCredit(Account credit) {
        this.credit = credit;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public void setDebitStatementDate(LocalDate debitStatementDate) {
        this.debitStatementDate = debitStatementDate;
    }

    public void setCreditStatementDate(LocalDate creditStatementDate) {
        this.creditStatementDate = creditStatementDate;
    }

}
