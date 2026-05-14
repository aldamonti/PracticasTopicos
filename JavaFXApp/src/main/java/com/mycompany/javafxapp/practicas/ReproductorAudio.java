package com.mycompany.javafxapp.practicas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

public class ReproductorAudio extends Application {

    private MediaPlayer mediaPlayer;
    private Label lblCancion;
    private Slider sliderTiempo;
    private Slider sliderVolumen;
    private boolean cambiandoSlider = false;

    @Override
    public void start(Stage primaryStage) {

        // ── Label canción ──
        lblCancion = new Label("Sin archivo seleccionado");
        lblCancion.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        // ── Slider tiempo ──
        sliderTiempo = new Slider(0, 100, 0);
        sliderTiempo.setPrefWidth(350);
        sliderTiempo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (cambiandoSlider && mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(newVal.doubleValue()));
            }
        });
        sliderTiempo.setOnMousePressed(e -> cambiandoSlider = true);
sliderTiempo.setOnMouseReleased(e -> {
    cambiandoSlider = false;
    if (mediaPlayer != null) {
        double total = mediaPlayer.getTotalDuration().toSeconds();
        mediaPlayer.seek(Duration.seconds(sliderTiempo.getValue() / 100 * total));
    }
});

        // ── Slider volumen ──
        Label lblVol = new Label("🔊");
        sliderVolumen = new Slider(0, 1, 0.8);
        sliderVolumen.setPrefWidth(100);
        sliderVolumen.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (mediaPlayer != null)
                mediaPlayer.setVolume(newVal.doubleValue());
        });

        // ── Botones ──
        Button btnAbrir   = new Button("📂 Abrir");
        Button btnPlay    = new Button("▶ Play");
        Button btnPausar  = new Button("⏸ Pausar");
        Button btnDetener = new Button("⏹ Detener");

        for (Button b : new Button[]{btnAbrir, btnPlay, btnPausar, btnDetener}) {
            b.setPrefHeight(35);
            b.setPrefWidth(100);
            b.setStyle(
                "-fx-background-color: #2563EB;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;"
            );
        }

        // ── Acciones ──
        btnAbrir.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar audio");
            fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Audio", "*.mp3", "*.wav", "*.aac"));
            File file = fc.showOpenDialog(primaryStage);
            if (file != null) {
                if (mediaPlayer != null) mediaPlayer.dispose();
                Media media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(sliderVolumen.getValue());
                lblCancion.setText(file.getName());

                // Actualizar slider de tiempo
                mediaPlayer.currentTimeProperty().addListener((obs, oldVal, newVal) -> {
                    if (!cambiandoSlider) {
                        double total = mediaPlayer.getTotalDuration().toSeconds();
                        sliderTiempo.setValue(newVal.toSeconds() / total * 100);
                    }
                });
            }
        });

        btnPlay.setOnAction(e -> {
            if (mediaPlayer != null) mediaPlayer.play();
        });

        btnPausar.setOnAction(e -> {
            if (mediaPlayer != null) mediaPlayer.pause();
        });

        btnDetener.setOnAction(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                sliderTiempo.setValue(0);
            }
        });

        // ── Layout ──
        HBox hBotones = new HBox(10, btnAbrir, btnPlay, btnPausar, btnDetener);
        hBotones.setAlignment(Pos.CENTER);

        HBox hVolumen = new HBox(8, lblVol, sliderVolumen);
        hVolumen.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(15, lblCancion, sliderTiempo, hVolumen, hBotones);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F1F5F9;");

        Scene scene = new Scene(root, 420, 220);
        primaryStage.setTitle("Reproductor de Audio");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}