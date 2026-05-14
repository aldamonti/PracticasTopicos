package com.mycompany.javafxapp.practicas;

import java.sql.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Practica7 extends Application {

    private Connection conn;
    private TableView<Respuesta> tabla;
    private ObservableList<Respuesta> datos;

    // ── Modelo de datos ──
    public static class Respuesta {
        private int id;
        private String sisOper, progra, diseno, admon;
        private int horas;

        public Respuesta(int id, String sisOper, String progra, String diseno, String admon, int horas) {
            this.id = id; this.sisOper = sisOper; this.progra = progra;
            this.diseno = diseno; this.admon = admon; this.horas = horas;
        }

        public int getId() { return id; }
        public String getSisOper() { return sisOper; }
        public String getProgra() { return progra; }
        public String getDiseno() { return diseno; }
        public String getAdmon() { return admon; }
        public int getHoras() { return horas; }
    }

    @Override
    public void start(Stage primaryStage) {

        // ── Controles ──
        ComboBox<String> cbSO = new ComboBox<>();
        cbSO.getItems().addAll("Windows", "Linux", "Mac");
        cbSO.setValue("Windows");
        cbSO.setPrefWidth(100);

        CheckBox chProgra = new CheckBox("Progra");
        CheckBox chDiseno = new CheckBox("Diseño");
        CheckBox chAdmon  = new CheckBox("Admon");

        Spinner<Integer> spHoras = new Spinner<>(0, 24, 0);
        spHoras.setPrefWidth(80);
        spHoras.setEditable(true);

        // ── Botones ──
        Button btnAgregar    = new Button("+");
        Button btnEliminar   = new Button("-");
        Button btnGuardar    = new Button("Guardar");
        Button btnActualizar = new Button("Actualizar");

        for (Button b : new Button[]{btnAgregar, btnEliminar, btnGuardar, btnActualizar}) {
            b.setPrefHeight(30);
            b.setStyle("-fx-background-color: #2563EB; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        }

        // ── Tabla ──
        tabla = new TableView<>();
        datos = FXCollections.observableArrayList();
        tabla.setItems(datos);
        tabla.setPrefHeight(280);

        TableColumn<Respuesta, Integer> colId     = new TableColumn<>("id");
        TableColumn<Respuesta, String>  colSO     = new TableColumn<>("Sist. Op.");
        TableColumn<Respuesta, String>  colProgra = new TableColumn<>("Progra.");
        TableColumn<Respuesta, String>  colDiseno = new TableColumn<>("Diseño");
        TableColumn<Respuesta, String>  colAdmon  = new TableColumn<>("Admon.");
        TableColumn<Respuesta, Integer> colHoras  = new TableColumn<>("Horas");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSO.setCellValueFactory(new PropertyValueFactory<>("sisOper"));
        colProgra.setCellValueFactory(new PropertyValueFactory<>("progra"));
        colDiseno.setCellValueFactory(new PropertyValueFactory<>("diseno"));
        colAdmon.setCellValueFactory(new PropertyValueFactory<>("admon"));
        colHoras.setCellValueFactory(new PropertyValueFactory<>("horas"));

        tabla.getColumns().addAll(colId, colSO, colProgra, colDiseno, colAdmon, colHoras);

        // Al seleccionar fila, llena los controles
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cbSO.setValue(newVal.getSisOper());
                chProgra.setSelected(newVal.getProgra().equals("S"));
                chDiseno.setSelected(newVal.getDiseno().equals("S"));
                chAdmon.setSelected(newVal.getAdmon().equals("S"));
                spHoras.getValueFactory().setValue(newVal.getHoras());
            }
        });

        // ── Acciones ──
        btnActualizar.setOnAction(e -> cargarDatos());

        btnAgregar.setOnAction(e -> {
            String sSO   = cbSO.getValue();
            String sPrg  = chProgra.isSelected() ? "S" : "N";
            String sGrf  = chDiseno.isSelected()  ? "S" : "N";
            String sAdm  = chAdmon.isSelected()   ? "S" : "N";
            int    iHrs  = spHoras.getValue();

            try {
                connect();
                String sql = String.format(
                    "INSERT INTO respuestas (sSisOper,cProgra,cDiseno,cAdmon,iHoras) VALUES ('%s','%s','%s','%s',%d)",
                    sSO, sPrg, sGrf, sAdm, iHrs);
                conn.createStatement().execute(sql);
                disconnect();
                cargarDatos();
                showAlert("✅ Registro agregado.");
            } catch (SQLException ex) { ex.printStackTrace(); }
        });

        btnEliminar.setOnAction(e -> {
            Respuesta sel = tabla.getSelectionModel().getSelectedItem();
            if (sel == null) return;
            try {
                connect();
                conn.createStatement().execute("DELETE FROM respuestas WHERE id = " + sel.getId());
                disconnect();
                cargarDatos();
                showAlert("✅ Registro eliminado.");
            } catch (SQLException ex) { ex.printStackTrace(); }
        });

        btnGuardar.setOnAction(e -> {
            Respuesta sel = tabla.getSelectionModel().getSelectedItem();
            if (sel == null) return;
            try {
                connect();
                String sql = String.format(
                    "UPDATE respuestas SET sSisOper='%s',cProgra='%s',cDiseno='%s',cAdmon='%s',iHoras=%d WHERE id=%d",
                    cbSO.getValue(),
                    chProgra.isSelected() ? "S" : "N",
                    chDiseno.isSelected()  ? "S" : "N",
                    chAdmon.isSelected()   ? "S" : "N",
                    spHoras.getValue(), sel.getId());
                conn.createStatement().execute(sql);
                disconnect();
                cargarDatos();
                showAlert("✅ Registro actualizado.");
            } catch (SQLException ex) { ex.printStackTrace(); }
        });

        // ── Layout ──
        HBox hBotones = new HBox(8, btnAgregar, btnEliminar, btnGuardar, btnActualizar);
        hBotones.setAlignment(Pos.CENTER_LEFT);

        HBox hControles = new HBox(8, cbSO, chProgra, chDiseno, chAdmon, spHoras);
        hControles.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox(10, hBotones, hControles, tabla);
        root.setPadding(new Insets(15));

        cargarDatos();

        Scene scene = new Scene(root, 520, 400);
        primaryStage.setTitle("Práctica 7 - CRUD Encuesta");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void cargarDatos() {
        datos.clear();
        try {
            connect();
            ResultSet rs = conn.createStatement().executeQuery(
                "SELECT id,sSisOper,cProgra,cDiseno,cAdmon,iHoras FROM respuestas");
            while (rs.next()) {
                datos.add(new Respuesta(
                    rs.getInt("id"),
                    rs.getString("sSisOper"),
                    rs.getString("cProgra"),
                    rs.getString("cDiseno"),
                    rs.getString("cAdmon"),
                    rs.getInt("iHoras")
                ));
            }
            disconnect();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void connect() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException ex) { ex.printStackTrace(); }
        conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/encuesta?zeroDateTimeBehavior=CONVERT_TO_NULL"
            + "&user=encuesta_user&password=encuesta_pass");
    }

    private void disconnect() throws SQLException { conn.close(); }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}