package com.boxesapp.desktopapp.model.dao;

import com.boxesapp.desktopapp.model.Account;
import com.boxesapp.desktopapp.model.User;

import java.util.List;

public interface AccountDao extends Dao<Account> {
    public List<Account> findByCredentialTitle();

    public List<Account> findByOwner(User owner);
}
