package com.mycompany.javafxapp.practicas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class Practica4 extends Application {

    @Override
    public void start(Stage primaryStage) {

        TextField tfRuta = new TextField();
        tfRuta.setEditable(false);
        tfRuta.setPrefWidth(400);
        tfRuta.setPromptText("Selecciona un archivo...");

        Button btnSelect = new Button("...");
        btnSelect.setPrefWidth(40);
        btnSelect.setPrefHeight(35);
        btnSelect.setStyle(
            "-fx-background-color: #2563EB;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;"
        );

        btnSelect.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                tfRuta.setText(file.getAbsolutePath());
            }
        });

        HBox root = new HBox(10, tfRuta, btnSelect);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 530, 150);
        primaryStage.setTitle("Práctica 4 - Selector de Archivo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}