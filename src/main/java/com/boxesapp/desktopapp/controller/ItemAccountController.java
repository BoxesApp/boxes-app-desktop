package com.boxesapp.desktopapp.controller;

import com.boxesapp.desktopapp.model.Account;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public abstract class ItemAccountController {
    private Account account;
    public abstract void onDelete();
    public abstract void onEdit();

    public ItemAccountController(Account account) {
        this.account = account;
    }

    public void initialize() {
        btnEdit.setOnAction(e -> {
            onEdit();
        });

        btnDelete.setOnAction(e -> {
            onDelete();
        });
    }

    @FXML
    public Text txtAccountName;

    @FXML
    public Text txtCreatedDate;

    @FXML
    public Text txtLastModifiedDate;

    @FXML
    public Button btnEdit;

    @FXML
    public Button btnDelete;


    @FXML
    public void onEditClicked(){
        System.out.println("edit clicked");
    }

    @FXML
    public void onDeleteClicked(Event event){
        System.out.println("delete clicked");
    }
}
