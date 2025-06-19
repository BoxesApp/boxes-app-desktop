package com.boxesapp.desktopapp.controller;

import com.boxesapp.desktopapp.model.Account;
import com.boxesapp.desktopapp.utils.Log;
import com.boxesapp.desktopapp.utils.Logger;
import com.boxesapp.desktopapp.view.BoxesApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

// This is the dashboard template provider.
public class DashboardTemplateController {

    @FXML
    public Tab tabAccount;
    @FXML
    public Tab tabSettings;
    @FXML
    public Tab tabBackup;

    @FXML
    public TabPane tabPane;

    @FXML
    public VBox accountsContainer;

    @FXML
    public ScrollPane scrollPaneAccounts;



    public void initialize() {
        // on charge la table des comptes et on écoute les changements de tabs
        updatePanes(tabAccount);
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldTab, newTab) -> {
                    updatePanes(newTab);
                }
        );
    }

    /**
     * Permet d'authentifier le user
     */
    private boolean auth(){
        System.out.println("..auth middleware");
        return true;
    }

    private void updatePanes(Tab wishedTab){
        if(auth()){
            switch (wishedTab.getId()){
                case "tabAccount":
                    try{

                        loadAccountsPage();
                    }catch(IOException e){
                        Logger.log(new Log(e.getMessage(), Log.LogLevel.exception));
                        System.out.printf(e.getMessage());
                    }
                    break;
                case "tabSettings":
                    loadSettingsPage();
                    break;
                case "tabBackup":
                    loadBackupPage();
                    break;
                default:
                    break;
            }
        }else{
            unLoadAll();
        }
    }

    /**
     * Load all the data related to the account Tab - pane
     */
    private void loadAccountsPage() throws IOException {
        accountsContainer.getChildren().clear();
        System.out.println("loading accounts...");
        // ArrayList<Account> accounts = new ArrayList<>();


        for(int i=0; i< 45; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(BoxesApp.class.getResource("/com/boxesapp/desktopapp/fxml/item-account.fxml"));
            fxmlLoader.setController(new ItemAccountController());
            HBox itemAccountNode =  fxmlLoader.load();
            ItemAccountController controller = fxmlLoader.getController();
            controller.initialize();
            accountsContainer.getChildren().add(itemAccountNode);
        }
        System.out.println(accountsContainer.getChildren().size());
    }

    private  void loadSettingsPage(){
        System.out.println("loading settings...");
    }


    private  void loadBackupPage(){
        System.out.println("loading settings...");
    }

    /*
    unload all panes contents
     */
    private void unLoadAll(){
        System.out.println("unloading contents...");
    }

    @FXML
    public void onAccountTabCliqued(Event event) {
        System.out.println("cliqued on account sections.... ");
    }

    @FXML
    public void onSettingsTabCliqued(Event event) {
        System.out.println("cliqued on tab sections... ");
    }

    @FXML
    public void onBackupTabCliqued(Event event) {
        System.out.println("cliqued on backup sections... ");
    }


    @FXML
    public void onCloseTab(Event event) {
        System.out.println("closed one tab");
    }
}
