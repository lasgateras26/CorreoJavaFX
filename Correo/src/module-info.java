module GestorCorreo {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires java.mail;
    requires javafx.web;
    requires commons.email;
    opens alberto.views to javafx.fxml;
    exports us.alberto;
    exports us.alberto.logic;
    exports us.alberto.models;
    exports us.alberto.views;
}