package com.hevlar.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

/**
 * Repository for Account
 */
public interface AccountDataRepository extends JpaRepository<AccountData, String> {
    AccountData findByName(String name);
    Streamable<AccountData> findAllByBankNotNull();
    Streamable<AccountData> findByAccountGroup(String accountGroup);
}
