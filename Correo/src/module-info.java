module GestorCorreo {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires java.mail;
    requires javafx.web;
    requires commons.email;

    exports us.alberto;
    exports us.alberto.logic;
    exports us.alberto.models;
    exports us.alberto.views;

    opens us.alberto.views to javafx.fxml;
}