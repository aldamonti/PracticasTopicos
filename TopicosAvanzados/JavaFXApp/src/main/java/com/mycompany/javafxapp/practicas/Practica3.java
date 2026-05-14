package com.mycompany.javafxapp.practicas;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Practica3 extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label lblPelicula = new Label("Escribe el título de una película:");
        Label lblPeliculas = new Label("Películas:");

        TextField tfPelicula = new TextField();
        tfPelicula.setPromptText("Título de la película...");

        ObservableList<String> lista = FXCollections.observableArrayList();
        ComboBox<String> cbPeliculas = new ComboBox<>(lista);
        cbPeliculas.setPrefWidth(200);

        Button btnAgregar = new Button("Agregar");
        btnAgregar.setPrefWidth(150);
        btnAgregar.setPrefHeight(35);
        btnAgregar.setStyle(
            "-fx-background-color: #2563EB;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;"
        );

        btnAgregar.setOnAction(e -> {
            String pelicula = tfPelicula.getText().trim();

            if (pelicula.isEmpty()) return;

            for (String p : lista) {
                if (p.equalsIgnoreCase(pelicula)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("⚠️ Película repetida.");
                    alert.showAndWait();
                    return;
                }
            }

            lista.add(pelicula);
            tfPelicula.clear();
        });

        // Enter también agrega
        tfPelicula.setOnAction(e -> btnAgregar.fire());

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.add(lblPelicula, 0, 0);
        grid.add(lblPeliculas, 1, 0);
        grid.add(tfPelicula, 0, 1);
        grid.add(cbPeliculas, 1, 1);

        VBox root = new VBox(20, grid, btnAgregar);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 450, 200);
        primaryStage.setTitle("Práctica 3 - Películas");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}