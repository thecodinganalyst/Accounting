package com.hevlar.accounting.model;

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
    private final AtomicBoolean locked;

    /**
     * Default constructor
     * @param journalId unique identifier
     * @param txDate date when transaction is made
     * @param item description of the transaction
     * @param recurrence state whether this is a recurring transaction and how often is the recurrence
     * @param tags the tags used to search for this entry
     * @param currency ISO 4217 code of the currency
     * @param amount amount charged for this entry
     * @param debit debit account
     * @param credit credit account
     * @param postDate date when transaction is posted, usually for banks and credit cards, when transaction is not posted on non-working days
     * @param debitStatementDate for credit cards, to identify which statement should this entry appear for the debit account
     * @param creditStatementDate for credit cards, to identify which statement should this entry appear for the credit account
     */
    public JournalEntry(AtomicLong journalId, LocalDate txDate, String item, Recurrence recurrence, String[] tags, String currency, String amount, Account debit, Account credit, LocalDate postDate, LocalDate debitStatementDate, LocalDate creditStatementDate) {
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

    /**
     * Gets the journal id
     * @return journal id
     */
    public AtomicLong getJournalId() {
        return journalId;
    }

    /**
     * Gets the transaction date
     * @return transaction date
     */
    public LocalDate getTxDate() {
        return txDate;
    }

    /**
     * Gets the description of the journal entry
     * @return description of journal entry
     */
    public String getItem() {
        return item;
    }

    /**
     * Gets the recurrence
     * @return recurrence
     */
    public Recurrence getRecurrence() {
        return recurrence;
    }

    /**
     * Gets the tags associated with this entry
     * @return tags
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * Gets the currency associated with this entry
     * @return currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Gets the amount
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Gets the debit account of this entry
     * @return debit account
     */
    public Account getDebit() {
        return debit;
    }

    /**
     * Gets the credit account of this entry
     * @return credit account
     */
    public Account getCredit() {
        return credit;
    }

    /**
     * Gets the posted date of this entry
     * @return posted date
     */
    public LocalDate getPostDate() {
        return postDate;
    }

    /**
     * Gets the statement date of the debit account for credit card
     * @return statement date of debit account
     */
    public LocalDate getDebitStatementDate() {
        return debitStatementDate;
    }

    /**
     * Gets the statement date of the credit account for the credit card
     * @return statement date of credit account
     */
    public LocalDate getCreditStatementDate() {
        return creditStatementDate;
    }

    /**
     * Check if the entry is locked
     * @return locked status
     */
    public Boolean isLocked(){
        return locked.get();
    }

    /**
     * Locks this entry from being edited
     */
    public void lock(){
        locked.set(true);
    }

    /**
     * Update the transaction date
     * @param txDate transaction date
     * @return true if successful, false otherwise
     */
    public Boolean setTxDate(LocalDate txDate) {
        if(isLocked()) return false;
        this.txDate = txDate;
        return true;
    }

    /**
     * Update the description of the entry
     * @param item description of the entry
     * @return true if successful, false otherwise
     */
    public Boolean setItem(String item) {
        if(isLocked()) return false;
        this.item = item;
        return true;
    }

    /**
     * Update the recurrence
     * @param recurrence recurrence
     * @return true if successful, false otherwise
     */
    public Boolean setRecurrence(Recurrence recurrence) {
        if(isLocked()) return false;
        this.recurrence = recurrence;
        return true;
    }

    /**
     * Update the tags of this entry
     * @param tags tags associated with this entry
     * @return true if successful, false otherwise
     */
    public Boolean setTags(String[] tags) {
        if(isLocked()) return false;
        this.tags = tags;
        return true;
    }

    /**
     * Updates the currency of this entry
     * @param currency currency
     * @return true if successful, false otherwise
     */
    public Boolean setCurrency(Currency currency) {
        if(isLocked()) return false;
        this.currency = currency;
        return true;
    }

    /**
     * Updates the currency of this entry by currency code
     * @param currency currency code
     * @return true if successful, false otherwise
     */
    public Boolean setCurrency(String currency) {
        if(isLocked()) return false;
        this.currency = Currency.getInstance(currency);
        return true;
    }

    /**
     * Updates the amount of this entry
     * @param amount bigdecimal amount
     * @return true if successful, false otherwise
     */
    public Boolean setAmount(BigDecimal amount) {
        if(isLocked()) return false;
        this.amount = amount;
        return true;
    }

    /**
     * Updates the amount of this entry by string value
     * @param amount amount
     * @return true if successful, false otherwise
     */
    public Boolean setAmount(String amount) {
        if(isLocked()) return false;
        this.amount = new BigDecimal(amount);
        return true;
    }

    /**
     * Updates the debit account
     * @param debit debit account
     * @return true if successful, false otherwise
     */
    public Boolean setDebit(Account debit) {
        if(isLocked()) return false;
        this.debit = debit;
        return true;
    }

    /**
     * Updates the credit account
     * @param credit credit account
     * @return true if successful, false otherwise
     */
    public Boolean setCredit(Account credit) {
        if(isLocked()) return false;
        this.credit = credit;
        return true;
    }

    /**
     * Upates the post date of the entry
     * @param postDate posted date of entry
     * @return true if successful, false otherwise
     */
    public Boolean setPostDate(LocalDate postDate) {
        if(isLocked()) return false;
        this.postDate = postDate;
        return true;
    }

    /**
     * Updates the debit statement date of this entry
     * @param debitStatementDate debit statement date
     * @return true if successful, false otherwise
     */
    public Boolean setDebitStatementDate(LocalDate debitStatementDate) {
        if(isLocked()) return false;
        this.debitStatementDate = debitStatementDate;
        return true;
    }

    /**
     * Updates the credit statement date of this entry
     * @param creditStatementDate credit statement date
     * @return true if successful, false otherwise
     */
    public Boolean setCreditStatementDate(LocalDate creditStatementDate) {
        if(isLocked()) return false;
        this.creditStatementDate = creditStatementDate;
        return true;
    }

}
