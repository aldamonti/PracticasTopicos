package com.mycompany.javafxapp.practicas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cronometro extends Application {

    private Thread hiloContador;
    private volatile boolean corriendo = false;
    private int segundos = 0;
    private Label lblTiempo;

    @Override
    public void start(Stage primaryStage) {

        lblTiempo = new Label("00:00:00");
        lblTiempo.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");

        Button btnIniciar = new Button("▶ Iniciar");
        Button btnPausar  = new Button("⏸ Pausar");
        Button btnReset   = new Button("↺ Reset");

        for (Button b : new Button[]{btnIniciar, btnPausar, btnReset}) {
            b.setPrefWidth(120);
            b.setPrefHeight(40);
            b.setStyle(
                "-fx-background-color: #2563EB;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;"
            );
        }

        // ── Hilo que incrementa el tiempo ──
        btnIniciar.setOnAction(e -> {
            if (!corriendo) {
                corriendo = true;
                hiloContador = new Thread(() -> {
                    while (corriendo) {
                        try {
                            Thread.sleep(1000);
                            segundos++;
                            // Actualizar UI desde el hilo de JavaFX
                            Platform.runLater(() -> lblTiempo.setText(formatearTiempo(segundos)));
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                hiloContador.setDaemon(true); // se cierra con la ventana
                hiloContador.start();
            }
        });

        // ── Hilo que pausa ──
        btnPausar.setOnAction(e -> {
            corriendo = false;
            if (hiloContador != null) {
                hiloContador.interrupt();
            }
        });

        // ── Reset ──
        btnReset.setOnAction(e -> {
            corriendo = false;
            if (hiloContador != null) hiloContador.interrupt();
            segundos = 0;
            lblTiempo.setText("00:00:00");
        });

        HBox hBotones = new HBox(15, btnIniciar, btnPausar, btnReset);
        hBotones.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, lblTiempo, hBotones);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F1F5F9;");

        Scene scene = new Scene(root, 380, 200);
        primaryStage.setTitle("Cronómetro");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private String formatearTiempo(int totalSegundos) {
        int hrs  = totalSegundos / 3600;
        int mins = (totalSegundos % 3600) / 60;
        int segs = totalSegundos % 60;
        return String.format("%02d:%02d:%02d", hrs, mins, segs);
    }

    public static void main(String[] args) {
        launch(args);
    }
}