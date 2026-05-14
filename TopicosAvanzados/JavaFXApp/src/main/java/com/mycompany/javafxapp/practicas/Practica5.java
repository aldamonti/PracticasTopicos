package com.mycompany.javafxapp.practicas;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Practica5 extends Application {

    @Override
    public void start(Stage primaryStage) {

        // ── ORIGINAL ──
        ToggleGroup tg1 = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Opción 1");
        RadioButton rb2 = new RadioButton("Opción 2");
        RadioButton rb3 = new RadioButton("Opción 3");
        rb1.setToggleGroup(tg1);
        rb2.setToggleGroup(tg1);
        rb3.setToggleGroup(tg1);
        rb1.setSelected(true);

        CheckBox cb1 = new CheckBox("Opción 4");
        CheckBox cb2 = new CheckBox("Opción 5");
        CheckBox cb3 = new CheckBox("Opción 6");

        TextField tf1 = new TextField();
        tf1.setPromptText("Escribe y presiona Enter...");

        ObservableList<String> lista = FXCollections.observableArrayList();
        ComboBox<String> combo1 = new ComboBox<>(lista);
        combo1.setPrefWidth(150);

        Spinner<Integer> spinner1 = new Spinner<>(0, 100, 0);
        spinner1.setEditable(true);

        // ── ESPEJO ──
        ToggleGroup tg2 = new ToggleGroup();
        RadioButton rb11 = new RadioButton("Opción 1");
        RadioButton rb22 = new RadioButton("Opción 2");
        RadioButton rb33 = new RadioButton("Opción 3");
        rb11.setToggleGroup(tg2);
        rb22.setToggleGroup(tg2);
        rb33.setToggleGroup(tg2);
        rb11.setSelected(true);
        rb11.setDisable(true);
        rb22.setDisable(true);
        rb33.setDisable(true);

        CheckBox cb11 = new CheckBox("Opción 4");
        CheckBox cb22 = new CheckBox("Opción 5");
        CheckBox cb33 = new CheckBox("Opción 6");
        cb11.setDisable(true);
        cb22.setDisable(true);
        cb33.setDisable(true);

        TextField tf11 = new TextField();
        tf11.setDisable(true);

        ComboBox<String> combo11 = new ComboBox<>(lista); // mismo modelo
        combo11.setPrefWidth(150);
        combo11.setDisable(true);

        Spinner<Integer> spinner11 = new Spinner<>(0, 100, 0);
        spinner11.setDisable(true);

        // ── SINCRONIZACIÓN ──
        rb1.setOnAction(e -> rb11.setSelected(true));
        rb2.setOnAction(e -> rb22.setSelected(true));
        rb3.setOnAction(e -> rb33.setSelected(true));

        cb1.setOnAction(e -> cb11.setSelected(cb1.isSelected()));
        cb2.setOnAction(e -> cb22.setSelected(cb2.isSelected()));
        cb3.setOnAction(e -> cb33.setSelected(cb3.isSelected()));

        tf1.textProperty().addListener((obs, oldVal, newVal) ->
            tf11.setText(newVal));

        tf1.setOnAction(e -> {
            if (!tf1.getText().isEmpty()) {
                lista.add(tf1.getText());
            }
        });

        spinner1.valueProperty().addListener((obs, oldVal, newVal) ->
            spinner11.getValueFactory().setValue(newVal));

        // ── LAYOUT ORIGINAL ──
        GridPane gridOriginal = new GridPane();
        gridOriginal.setHgap(15);
        gridOriginal.setVgap(10);
        gridOriginal.add(rb1, 0, 0);
        gridOriginal.add(cb1, 1, 0);
        gridOriginal.add(tf1, 2, 0);
        gridOriginal.add(rb2, 0, 1);
        gridOriginal.add(cb2, 1, 1);
        gridOriginal.add(combo1, 2, 1);
        gridOriginal.add(rb3, 0, 2);
        gridOriginal.add(cb3, 1, 2);
        gridOriginal.add(spinner1, 2, 2);

        // ── LAYOUT ESPEJO ──
        GridPane gridEspejo = new GridPane();
        gridEspejo.setHgap(15);
        gridEspejo.setVgap(10);
        gridEspejo.add(rb11, 0, 0);
        gridEspejo.add(cb11, 1, 0);
        gridEspejo.add(tf11, 2, 0);
        gridEspejo.add(rb22, 0, 1);
        gridEspejo.add(cb22, 1, 1);
        gridEspejo.add(combo11, 2, 1);
        gridEspejo.add(rb33, 0, 2);
        gridEspejo.add(cb33, 1, 2);
        gridEspejo.add(spinner11, 2, 2);

        Label lblOriginal = new Label("Original");
        lblOriginal.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
        Label lblEspejo = new Label("Espejo");
        lblEspejo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        VBox root = new VBox(10,
            lblOriginal, gridOriginal,
            new Separator(),
            lblEspejo, gridEspejo
        );
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER_LEFT);

        Scene scene = new Scene(root, 480, 350);
        primaryStage.setTitle("Práctica 5 - Imitador");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}