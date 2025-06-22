package com.boxesapp.desktopapp.controller;

import com.boxesapp.desktopapp.model.Account;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public abstract class ItemAccountController {

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

    private Account account;

    @FXML
    public void initialize() {
        btnEdit.setOnAction(e -> {
            onEdit();
        });

        btnDelete.setOnAction(e -> {
            onDelete();
        });
    }


    public abstract void onDelete();
    public abstract void onEdit();
}
