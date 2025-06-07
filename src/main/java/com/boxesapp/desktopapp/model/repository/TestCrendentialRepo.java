package com.boxesapp.desktopapp.model.repository;

import com.boxesapp.desktopapp.model.Account;
import com.boxesapp.desktopapp.model.Credential;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TestCrendentialRepo {

    public static void main(String[] argvs){

        System.out.println("test credential repo");

        Account account = new AccountRepository().findById(2);

        //new AccountRepository().create(account);

        System.out.println("account " + account);

        Credential itemCrendential = new Credential(
                0,
                account,
                "email",
                "test67@gmail.com",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        //new CredentialRepository().create(itemCrendential);

        System.out.println("-- liste des crendentials en bd:: " + new CredentialRepository().findAll());

        System.out.println("rechercher par compte" + new CredentialRepository().getByAccount(account));
        new CredentialRepository().delete(account);
        System.out.println("supression: " + new CredentialRepository().findAll());
        System.out.println("rechercher par compte" + new CredentialRepository().getByAccount(account));


    }

    public static void testCreateCredential(){

    }
}
