package com.hevlar.accounting.model;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Abstract class to represent an Account.
 * Accounts can be edited until an irreversible lock action is performed, after which, no editing is allowed, as
 * doing so may cause discrepancies to the accounts. Editing is allowed before locking so to allow room for typo mistakes
 * during creation.
 */
public abstract class Account {

    private String name;
    private final AccountGroup accountGroup;
    private final AtomicBoolean lock;

    /**
     * Default constructor to create an Account, with lock = false
     * @param name name of the account
     * @param accountGroup account group
     */
    public Account(String name, AccountGroup accountGroup, Boolean lock) {
        this.name = name;
        this.accountGroup = accountGroup;
        this.lock = new AtomicBoolean(lock);
    }

    /**
     * Gets the name of the account
     * @return name of the account
     */
    public String getName() {
        return name;
    }

    /**
     * Edit the name of the account, only when lock = false
     * @param name new name of the account
     * @return true if success, false if account is locked
     */
    public Boolean setName(String name) {
        if(isLocked()) return Boolean.FALSE;
        this.name = name;
        return Boolean.TRUE;
    }

    /**
     * Gets the account group which this account belongs to
     * @return AccountGroup of the account
     */
    public AccountGroup getAccountGroup() {
        return accountGroup;
    }

    /**
     * Locks the account from editing. Irreversible change, once locked, no editing of this account is possible
     */
    public void lock(){
        this.lock.set(true);
    }

    /**
     * Check if this account is locked
     * @return true if account is locked, false otherwise
     */
    public Boolean isLocked(){
        return this.lock.get();
    }

    public AccountType getAccountType(){
        return this.accountGroup.accountType;
    }
}
