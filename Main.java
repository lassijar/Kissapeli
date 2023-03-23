package com.example.kissapeli;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;



/**
 * Pääluokka. Luodaan tässä uusi olio Setuppi luokasta, jolla saadaan ns. alkuruutu näkymä.
 * @author Lassi Järvelä
 */
public class Main extends Application {
    @Override
    public void start(Stage paaStage) throws IOException {

        Setuppi testi = new Setuppi();
        paaStage = testi.getPaaStage();
        paaStage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}