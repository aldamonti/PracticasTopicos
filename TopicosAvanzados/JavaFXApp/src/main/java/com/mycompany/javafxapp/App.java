package com.mycompany.javafxapp;

import com.mycompany.javafxapp.practicas.Cronometro;
import com.mycompany.javafxapp.practicas.Practica1;
import com.mycompany.javafxapp.practicas.Practica2;
import com.mycompany.javafxapp.practicas.Practica3;
import com.mycompany.javafxapp.practicas.Practica4;
import com.mycompany.javafxapp.practicas.Practica5;
import com.mycompany.javafxapp.practicas.Practica6;
import com.mycompany.javafxapp.practicas.Practica7;
import com.mycompany.javafxapp.practicas.ReproductorAudio;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label titulo = new Label("Menú de Prácticas");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnPractica1 = new Button("Práctica 1 - Saludo");
        btnPractica1.setPrefWidth(200);
        btnPractica1.setOnAction(e -> {
            try { new Practica1().start(new Stage()); }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        Button btnPractica2 = new Button("Práctica 2 - Número Aleatorio");
        btnPractica2.setPrefWidth(200);
        btnPractica2.setOnAction(e -> {
            try { new Practica2().start(new Stage()); }
            catch (Exception ex) { ex.printStackTrace(); }
        });
        
        Button btnPractica3 = new Button("Práctica 3 - Películas");
        btnPractica3.setPrefWidth(200);
        btnPractica3.setOnAction(e -> {
            try { new Practica3().start(new Stage()); }
            catch (Exception ex) { ex.printStackTrace(); }
});
        Button btnPractica4 = new Button("Práctica 4 - Selector de Archivo");
        btnPractica4.setPrefWidth(200);
        btnPractica4.setOnAction(e -> {
            try { new Practica4().start(new Stage()); }
            catch (Exception ex) { ex.printStackTrace(); }
});
        
        Button btnPractica5 = new Button("Práctica 5 - Imitador");
        btnPractica5.setPrefWidth(200);
        btnPractica5.setOnAction(e -> {
             try { new Practica5().start(new Stage()); }
            catch (Exception ex) { ex.printStackTrace(); }
});
        
        Button btnPractica6 = new Button("Práctica 6 - Encuesta BD");
        btnPractica6.setPrefWidth(200);
        btnPractica6.setOnAction(e -> {
            try { new Practica6().start(new Stage()); }
            catch (Exception ex) { ex.printStackTrace(); }
});
        
        Button btnPractica7 = new Button("Práctica 7 - CRUD Encuesta");
        btnPractica7.setPrefWidth(200);
        btnPractica7.setOnAction(e -> {
            try { new Practica7().start(new Stage()); }
            catch (Exception ex) { ex.printStackTrace(); }
});
        
        Button btnCronometro = new Button("Cronómetro");
        btnCronometro.setPrefWidth(200);
        btnCronometro.setOnAction(e -> {
            try { new Cronometro().start(new Stage()); }
            catch (Exception ex) { ex.printStackTrace(); }
});

        Button btnReproductor = new Button("Reproductor de Audio");
        btnReproductor.setPrefWidth(200);
        btnReproductor.setOnAction(e -> {
            try { new ReproductorAudio().start(new Stage()); }
            catch (Exception ex) { ex.printStackTrace(); }
});

        VBox root = new VBox(15, titulo, btnPractica1, btnPractica2, btnPractica3,
                     btnPractica4, btnPractica5, btnPractica6, btnPractica7,
                     btnCronometro, btnReproductor);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setTitle("Prácticas JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}