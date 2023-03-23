package com.example.kissapeli;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Setuppi luokka. Tässä luokassa luodaan kaikki objektit ja niiden toiminnallisuudet, jotka kuuluvat alkunäyttöön.
 * @author Lassi Järvelä
 */
public class Setuppi {

    //AnchorPane paras vaihtoehto peliin -> päästään helposti käsiksi koordinaatteihin.
    private AnchorPane paaPane;
    private Scene paaScene;
    private Stage paaStage;

    //Ikkunan dimensiot.
    private static final int korkeus = 600;
    private static final int leveys = 800;

    //Onko ohjeruutu päällä vai ei
    private boolean paalla = false;



    public Setuppi(){

        //Luo uudet oliot
        paaPane = new AnchorPane();
        paaScene = new Scene(paaPane,leveys,korkeus);
        paaStage = new Stage();
        paaStage.setScene(paaScene);

        //Lisätään kaikki, mitä halutaan näyttää näytöllä
        teeAlkuNappi();
        teeOhjeNappi();
        teePoistuNappi();
        teeTausta();
        teeTitle();

    }


    /**
     * Palauttaa alkuruudun stagen
     * @return Stage paaStage
     */
    public Stage getPaaStage(){
        return paaStage;
    }


    /**
     * Tekee napin, jolla peli aloitetaan.
     */
    private void teeAlkuNappi(){
        //Luodaan uusi olio luokan Nappulat avulla.
        Nappulat alotusNappi = new Nappulat("Aloita!",30);
        alotusNappi.setLayoutX(50);
        alotusNappi.setLayoutY(150);

        //Tehdään toiminnallisuus
        alotusNappi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Kun nappulaa painetaan alkunäytön stage suljetaan
                paaStage.close();

                //Luodaan uusi peliSetuppi ja laitetaan se näkymään
                PeliSetuppi peli = new PeliSetuppi();
                Stage peliStage = peli.getPeliStage();
                peliStage.show();
            }
        });

        paaPane.getChildren().add(alotusNappi);

    }


    /**
     * Tehdään nappi, joka näyttää ja piilottaa ohjetekstin
     */
    private void teeOhjeNappi(){

        //Käytetään Nappulat luokkaa
        Nappulat ohjeNappi = new Nappulat("Ohjeet",30);
        ohjeNappi.setLayoutX(50);
        ohjeNappi.setLayoutY(290);
        paaPane.getChildren().add(ohjeNappi);


        //Toiminnallisuus
        ohjeNappi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Jos ei ole päällä, tehdään ja pistetäännäkymään
                if(paalla == false){
                    Text ohjeAlue = new Text("Pelin ideana on väistellä ylhäältä putoavia painoja ja\nyrittää saada kiinni lihapaloja.\nKissa liikkuu oikeasta ja vasemmasta nuolinäppäimestä.\nPelin voittaa, jos kissa saavuttaa maksimikoon ja häviää,\njos kissa saavuttaa minimikoon.\nPisteet lähtevät 10:stä. Pelin voittaa 20:llä ja häviää 5:llä.");
                    ohjeAlue.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 16));
                    ohjeAlue.setLayoutX(250);
                    ohjeAlue.setLayoutY(320);
                    paaPane.getChildren().add(ohjeAlue);
                    paalla = true;
                } else{
                    //Jos on päällä poistetaan indeksillä viimeisin objekti
                    paaPane.getChildren().remove(4);
                    paalla=false;
                }

            }

        });

    }

    /**
     * Tekee napin, jolla poistutaan pelistä
     */
    private void teePoistuNappi(){
        //Luodaan uusi olio luokan Nappulat avulla.
        Nappulat poistuNappi = new Nappulat("Poistu",30);
        poistuNappi.setLayoutX(50);
        poistuNappi.setLayoutY(430);

        //Tehdään toiminnallisuus
        poistuNappi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Kun nappulaa painetaan alkunäytön stage suljetaan
                paaStage.close();

            }
        });

        paaPane.getChildren().add(poistuNappi);

    }


    /**
     *  Tekee taustan
     */
    private void teeTausta(){
        Image taustaKuva = new Image("file:tausta.jpg",800,600,false,true);
        BackgroundImage tausta = new BackgroundImage(taustaKuva, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        paaPane.setBackground(new Background(tausta));
    }


    /**
     *  Tekee titlen
     */
    private void teeTitle(){
        Text title = new Text();
        title.setText("Kissapeli");
        title.setFont(Font.font("Copperplate",FontWeight.EXTRA_BOLD, 60));
        title.setLayoutX(280);
        title.setLayoutY(100);


        paaPane.getChildren().add(title);


    }

}
