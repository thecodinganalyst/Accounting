package com.hevlar.accounting.model;

public abstract class Account {

    private String name;
    private AccountGroup accountGroup;
    private Boolean lock;

    public Account(String name, AccountGroup accountGroup) {
        this.name = name;
        this.accountGroup = accountGroup;
        this.lock = Boolean.FALSE;
    }

    public String getName() {
        return name;
    }

    public Boolean setName(String name) {
        if(this.lock) return Boolean.FALSE;
        this.name = name;
        return Boolean.TRUE;
    }

    public AccountGroup getAccountGroup() {
        return accountGroup;
    }

    public void lock(){
        this.lock = true;
    }

    public Boolean isLocked(){
        return this.lock;
    }
}
