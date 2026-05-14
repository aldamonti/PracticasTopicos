module com.mycompany.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens com.mycompany.javafxapp to javafx.fxml;
    opens com.mycompany.javafxapp.practicas to javafx.fxml;
    exports com.mycompany.javafxapp;
    exports com.mycompany.javafxapp.practicas;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.media;
}