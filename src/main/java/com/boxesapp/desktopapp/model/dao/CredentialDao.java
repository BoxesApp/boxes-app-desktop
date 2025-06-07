package com.boxesapp.desktopapp.model.dao;

import com.boxesapp.desktopapp.model.Account;
import com.boxesapp.desktopapp.model.Credential;

import java.util.List;

public interface CredentialDao extends Dao<Credential> {

    public List<Credential> getByAccount(Account account);

    /**
     * Delete all the credentials related to and account.
     * @param account
     */
    public void delete(Account account);
}
