package com.boxesapp.desktopapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class BoxesAppController {

    @FXML
    public Button btnValidate;
    @FXML
    public TextField fieldPseudo;
    @FXML
    public PasswordField fieldPassword;



    @FXML
    public void signUp(){
        System.out.println("BoxesAppController - cliqued on somthing haha");
    }
}
