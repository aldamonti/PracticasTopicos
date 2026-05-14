package com.mycompany.javafxapp.practicas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Practica1 extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label titulo = new Label("👋 Saluda a alguien");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titulo.setTextFill(Color.rgb(37, 99, 235));

        Label lbl1 = new Label("Escribe un nombre:");
        lbl1.setFont(Font.font("Segoe UI", 13));

        TextField tfNombre = new TextField();
        tfNombre.setPromptText("Tu nombre aquí...");
        tfNombre.setPrefHeight(35);
        tfNombre.setStyle("-fx-border-radius: 6; -fx-background-radius: 6;");

        Button btnSaludar = new Button("Saludar");
        btnSaludar.setPrefWidth(150);
        btnSaludar.setPrefHeight(35);
        btnSaludar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btnSaludar.setTextFill(Color.WHITE);
        btnSaludar.setStyle(
            "-fx-background-color: #2563EB;" +
            "-fx-background-radius: 6;"
        );

        btnSaludar.setOnAction(e -> {
            String nombre = tfNombre.getText().trim();
            if (nombre.isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setHeaderText(null);
                alerta.setContentText("⚠️ Escribe un nombre primero.");
                alerta.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("¡Hola, " + nombre + "! 👋");
                alert.showAndWait();
            }
        });

        tfNombre.setOnAction(e -> btnSaludar.fire());

        VBox root = new VBox(15, titulo, lbl1, tfNombre, btnSaludar);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER_LEFT);

        Scene scene = new Scene(root, 380, 250);
        primaryStage.setTitle("Práctica 1");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}