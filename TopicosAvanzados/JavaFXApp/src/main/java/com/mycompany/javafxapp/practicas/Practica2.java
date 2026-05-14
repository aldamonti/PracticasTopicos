package com.mycompany.javafxapp.practicas;

import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Practica2 extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label lblNumInf = new Label("Núm. Inferior:");
        Label lblNumSup = new Label("Núm. Superior:");
        Label lblNumGenerado = new Label("Núm. Generado:");

        Spinner<Integer> spNumInf = new Spinner<>(0, 1000, 0);
        Spinner<Integer> spNumSup = new Spinner<>(0, 1000, 100);
        spNumInf.setEditable(true);
        spNumSup.setEditable(true);

        // Solo permitir números en spNumInf
        spNumInf.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("-?\\d*")) {
                spNumInf.getEditor().setText(oldVal);
            }
        });

        // Solo permitir números en spNumSup
        spNumSup.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("-?\\d*")) {
                spNumSup.getEditor().setText(oldVal);
            }
        });

        TextField tfNumGenerado = new TextField("0");
        tfNumGenerado.setEditable(false);
        tfNumGenerado.setStyle("-fx-alignment: center-right;");

        // Validación al cambiar spNumInf
        spNumInf.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal > spNumSup.getValue()) {
                spNumInf.getValueFactory().setValue(oldVal);
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText(null);
                a.setContentText("El valor mínimo no puede ser mayor que el máximo.");
                a.showAndWait();
            }
        });

        // Validación al cambiar spNumSup
        spNumSup.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal < spNumInf.getValue()) {
                spNumSup.getValueFactory().setValue(oldVal);
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText(null);
                a.setContentText("El valor máximo no puede ser menor que el mínimo.");
                a.showAndWait();
            }
        });

        Button btnGenerar = new Button("Generar");
        btnGenerar.setPrefWidth(200);
        btnGenerar.setPrefHeight(35);
        btnGenerar.setStyle(
            "-fx-background-color: #2563EB;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;"
        );

        btnGenerar.setOnAction(e -> {
            int min = spNumInf.getValue();
            int max = spNumSup.getValue();
            int randomNum = new Random().nextInt((max - min) + 1) + min;
            tfNumGenerado.setText(String.valueOf(randomNum));
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.add(lblNumInf, 0, 0);
        grid.add(spNumInf, 1, 0);
        grid.add(lblNumSup, 0, 1);
        grid.add(spNumSup, 1, 1);
        grid.add(lblNumGenerado, 0, 2);
        grid.add(tfNumGenerado, 1, 2);

        VBox root = new VBox(20, grid, btnGenerar);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 320, 250);
        primaryStage.setTitle("Práctica 2 - Número Aleatorio");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}