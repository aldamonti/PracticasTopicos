package com.mycompany.javafxapp.practicas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Practica6 extends Application {

    private Connection conn;

    @Override
    public void start(Stage primaryStage) {

        // ── Sistema Operativo ──
        Label lblSO = new Label("Elige un sistema operativo");
        lblSO.setStyle("-fx-font-weight: bold;");

        ToggleGroup tgSO = new ToggleGroup();
        RadioButton rbWin = new RadioButton("Windows");
        RadioButton rbLnx = new RadioButton("Linux");
        RadioButton rbMac = new RadioButton("Mac");
        rbWin.setToggleGroup(tgSO);
        rbLnx.setToggleGroup(tgSO);
        rbMac.setToggleGroup(tgSO);
        rbWin.setSelected(true);

        // ── Uso de la PC ──
        Label lblUso = new Label("Uso de la PC");
        lblUso.setStyle("-fx-font-weight: bold;");

        CheckBox chPrg = new CheckBox("Programación");
        CheckBox chGrf = new CheckBox("Diseño Gráfico");
        CheckBox chAdm = new CheckBox("Administración");

        // ── Horas ──
        Label lblHoras = new Label("Horas que dedicas en la PC");
        lblHoras.setStyle("-fx-font-weight: bold;");

        Slider sdHrs = new Slider(0, 24, 8);
        sdHrs.setShowTickLabels(true);
        sdHrs.setShowTickMarks(true);
        sdHrs.setMajorTickUnit(6);
        sdHrs.setPrefWidth(200);

        Label lbHrs = new Label("8");
        sdHrs.valueProperty().addListener((obs, oldVal, newVal) ->
            lbHrs.setText(String.valueOf(newVal.intValue())));

        // ── Botón Guardar ──
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setPrefWidth(150);
        btnGuardar.setPrefHeight(35);
        btnGuardar.setStyle(
            "-fx-background-color: #2563EB;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;"
        );

        btnGuardar.setOnAction(e -> {
            String sSO = rbWin.isSelected() ? "Windows" : rbLnx.isSelected() ? "Linux" : "Mac";
            String sPrg = chPrg.isSelected() ? "S" : "N";
            String sGrf = chGrf.isSelected() ? "S" : "N";
            String sAdm = chAdm.isSelected() ? "S" : "N";
            int iHrs = (int) sdHrs.getValue();

            boolean archGuardado = guardarArchivo(sSO, sPrg, sGrf, sAdm, iHrs);
            boolean bdGuardado = guardarBD(sSO, sPrg, sGrf, sAdm, iHrs);

            if (archGuardado && bdGuardado) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("✅ Registro guardado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("❌ Error al guardar el registro.");
                alert.showAndWait();
            }
        });

        VBox root = new VBox(8,
            lblSO, rbWin, rbLnx, rbMac,
            new Separator(),
            lblUso, chPrg, chGrf, chAdm,
            new Separator(),
            lblHoras, sdHrs, lbHrs,
            btnGuardar
        );
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER_LEFT);

        Scene scene = new Scene(root, 280, 450);
        primaryStage.setTitle("Práctica 6 - Encuesta");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private boolean guardarArchivo(String sSO, String sPrg, String sGrf, String sAdm, int iHrs) {
        try {
            FileWriter fw = new FileWriter("encuesta.txt", true);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(String.format("%s,%s,%s,%s,%d\n", sSO, sPrg, sGrf, sAdm, iHrs));
            out.close();
            fw.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean guardarBD(String sSO, String sPrg, String sGrf, String sAdm, int iHrs) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/encuesta?zeroDateTimeBehavior=CONVERT_TO_NULL"
                + "&user=encuesta_user&password=encuesta_pass");

            String sql = String.format(
                "INSERT INTO respuestas (sSisOper,cProgra,cDiseno,cAdmon,iHoras) VALUES ('%s','%s','%s','%s',%d)",
                sSO, sPrg, sGrf, sAdm, iHrs);

            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}