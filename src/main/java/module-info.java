module com.boxesapp.desktopapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.boxesapp.desktopapp to javafx.fxml;
    exports com.boxesapp.desktopapp.view;
    exports com.boxesapp.desktopapp.controller;
    exports com.boxesapp.desktopapp.utils;

    opens com.boxesapp.desktopapp.view to javafx.fxml;
}