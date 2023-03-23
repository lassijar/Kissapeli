package com.example.kissapeli;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongToDoubleFunction;



/**
 * Peliluokka, jossa kaikki peliin liittyvät objektit ja toiminnallisuudet.
 * Laajennetaan ImageView, että saadaan kuvat toimimaan.
 * @author Lassi Järvelä
 */
public class PeliSetuppi extends ImageView {
    private AnchorPane peliPane;
    private Scene peliScene;
    private Stage peliStage;
    private static final int korkeus = 600;
    private static final int leveys = 800;

    //Määritellään valmiiksi kissa kuvasta Image ja Imageview
    private Image kissaKuva = new Image("file:Kissa1.2.png");
    private ImageView kissaView = new ImageView(kissaKuva);


    //Kissan liikkumiseen käytettäviä muuttujia
    private double X = 0;
    private double liikkuminen = 10;


    //Ryhmä, johon hyvät ja huonot kuvat sijoitetaan niiden luonnin jälkeen.
    private Group kuvaRyhma = new Group();




    public PeliSetuppi(){

        peliPane = new AnchorPane();
        peliScene = new Scene(peliPane,leveys,korkeus);
        peliStage = new Stage();
        peliStage.setScene(peliScene);

        teeTausta();
        kissa();
        kissaView.requestFocus();
        peli();

    }

    /**
     * Palauttaa peliruudun stagen
     * @return Stage peliStage
     */
    public Stage getPeliStage(){
        return peliStage;
    }

    /**
     * Tekee taustan
     */
    private void teeTausta(){

        Image taustaKuva = new Image("file:tausta.jpg",800,600,false,true);
        BackgroundImage tausta = new BackgroundImage(taustaKuva, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        peliPane.setBackground(new Background(tausta));

    }




    /**
     * Tekee napin, jolla saadaan peli uudestaan käyntiin
     */
    private void teeUudestaanNappi(){

        Nappulat uudestaanNappi = new Nappulat("Uudestaan?",20);
        uudestaanNappi.setLayoutX(100);
        uudestaanNappi.setLayoutY(230);

        uudestaanNappi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                peliStage.close();
                PeliSetuppi peli2 = new PeliSetuppi();
                Stage peliStage = peli2.getPeliStage();
                peliStage.show();
            }
        });

        peliPane.getChildren().add(uudestaanNappi);

    }



    /**
     * Tekee napin, jolla pääsee takaisin alkuruutuun
     */
    private void teeTakaisinNappi(){

        Nappulat takaisinNappi = new Nappulat("Takaisin",20);
        takaisinNappi.setLayoutX(100);
        takaisinNappi.setLayoutY(400);

        takaisinNappi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                peliStage.close();
                Setuppi setuppi2 = new Setuppi();
                Stage stage2 = setuppi2.getPaaStage();
                stage2.show();
            }
        });

        peliPane.getChildren().add(takaisinNappi);

    }




    /**
     * Teksti, joka näkyy, jos peli on voitettu
     */
    private void voititTeksti(){

        Text teksti = new Text();
        teksti.setText("Voitit pelin!");
        teksti.setFont(Font.font("Copperplate", FontWeight.EXTRA_BOLD, 60));
        teksti.setLayoutX(280);
        teksti.setLayoutY(100);
        peliPane.getChildren().add(teksti);

    }

    /**
     * Teksti, joka näkyy, jos peli on hävitty
     */
    private void havisitTeksti(){

        Text teksti = new Text();
        teksti.setText("Hävisit pelin :(");
        teksti.setFont(Font.font("Copperplate", FontWeight.EXTRA_BOLD, 60));
        teksti.setLayoutX(280);
        teksti.setLayoutY(100);
        peliPane.getChildren().add(teksti);

    }





    /**
     * Metodi, jolla hoidetaan ennätyksen lukeminen ja kirjoittaminen tiedostoon.
     * @param aika
     */
    private void onkoEnnatys(long aika) {

        boolean olikoEnkka = false;
        long nykyinenEnnätys = 0;
        //Muunnetaan tulevat miillisekunnit sekunneiksi.
        long sekuntit = aika / 1000;

        //Tiedostonkäsittely
        try {
            File file = new File("ajat.txt");
            Scanner scanner = new Scanner(file);

            //Jos tiedosto on tyhjä, lisätään tämähetkinen aika sinne suoraan
            if(!scanner.hasNext()){

                //Oli ennätys -> myöhemmin näytetään teksti
                olikoEnkka = true;
                FileWriter writer = new FileWriter(file);
                writer.write(Long.toString(sekuntit));
                writer.close();
            }

            //Luetaan edellinen ennätys
            nykyinenEnnätys = scanner.nextLong();
            scanner.close();

            //Jos saatu aika on nopeampi, kuin edellinen ennätys, kirjoitetaan uusi aika tiedostoon.
            if (sekuntit < nykyinenEnnätys) {
                FileWriter writer = new FileWriter(file);
                writer.write(Long.toString(sekuntit));
                writer.close();
                olikoEnkka = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Näytetään mennyt aika
        Text ajat = new Text("Sinulla kesti pelin läpäisyssä:"+" "+ sekuntit+" "+"sekunttia!");
        ajat.setLayoutX(300);
        ajat.setLayoutY(200);
        ajat.setFont(Font.font("Copperplate", FontWeight.EXTRA_BOLD, 20));

        //Jos oli ennätys näytetään teksti, muuten näytetään vanha ennätys.
        if(olikoEnkka){
            Text enkkaTeksti = new Text("!!UUSI ENNÄTYS!!");
            enkkaTeksti.setLayoutX(300);
            enkkaTeksti.setLayoutY(180);
            enkkaTeksti.setFont(Font.font("Copperplate", FontWeight.EXTRA_BOLD, 30));
            peliPane.getChildren().add(enkkaTeksti);
        } else {
            Text edellinenParas = new Text("Tämän hetkinen paras:"+" "+nykyinenEnnätys+" "+"sekunttia");
            edellinenParas.setLayoutX(300);
            edellinenParas.setLayoutY(250);
            edellinenParas.setFont(Font.font("Copperplate", FontWeight.EXTRA_BOLD, 20));
            peliPane.getChildren().add(edellinenParas);
        }

        peliPane.getChildren().add(ajat);
    }





    /**
     * Luo olion lihakuvasta, josta saa pisteitä
     * Koordinaatti saadaan Peli luokasta
     * @param koordinaatti
     * @return ImageView lihaView
     */

    private ImageView hyvaKuva(int koordinaatti){

        Image kuva = new Image("file:liha1.2.png");
        ImageView lihaView = new ImageView(kuva);
        lihaView.setFitWidth(100);
        lihaView.setFitHeight(100);
        lihaView.setLayoutX(koordinaatti);
        lihaView.setLayoutY(10);
        lihaView.setId("hyvä");

        return lihaView;

    }



    /**
     * Luo olion lihakuvasta, josta saa miinus pisteitä
     * Koordinaatti saadaan Peli luokasta
     * @param koordinaatti
     * @return ImageView painoView
     */
    private ImageView huonoKuva(int koordinaatti){

        Image kuva = new Image("file:paino1.2.png");
        ImageView painoView = new ImageView(kuva);
        painoView.setFitWidth(100);
        painoView.setFitHeight(100);
        painoView.setLayoutX(koordinaatti);
        painoView.setLayoutY(10);
        painoView.setId("huono");

        return painoView;

    }


    //Luodaan timeline valmiiksi
    private Timeline aikajana = null;




    /**
     * Peli luokka, joka hoitaa pelimekaniikat
     */
    private void peli(){

        //Näytetään pelaajalle pisteet
        Text pisteteksti = new Text("Pisteet:");
        pisteteksti.setLayoutX(30);
        pisteteksti.setLayoutY(30);
        pisteteksti.setFont(Font.font("Copperplate", FontWeight.EXTRA_BOLD, 20));
        peliPane.getChildren().add(pisteteksti);

        //Luodaan pistelaskun muuttujat
        AtomicInteger pisteet = new AtomicInteger();
        pisteet.set(10);
        AtomicInteger maxpisteet = new AtomicInteger();
        AtomicInteger minpisteet = new AtomicInteger();
        maxpisteet.set(20);
        minpisteet.set(5);


        //Tehdään koordinaatti randomisti.
        AtomicInteger randomKoordinaatti = new AtomicInteger();
        AtomicInteger randomKumpi = new AtomicInteger();
        Random rand = new Random();


        //Luodaan muuttujat ajan laskua varten.
        AtomicLong endTime = new AtomicLong();
        AtomicLong startTime = new AtomicLong();
        startTime.set(System.currentTimeMillis());


        peliPane.getChildren().add(kuvaRyhma);

        //Jos aikajanaa ei ole määritelty
        if(aikajana == null){

            //Määritellään aikajana
            aikajana = new Timeline(new KeyFrame(Duration.millis(2000), event -> {

                //Koordinaatin randomisointi
                randomKoordinaatti.set(rand.nextInt(600));
                //Randomisoidaan myös se, kumpi objekti luodaan
                randomKumpi.set(rand.nextInt(2));

                //Jos 0, tehdään hyvä kuva
                if (randomKumpi.get() == 0) {
                    //luodaan uusi hyväkuva olio
                    ImageView hyvakuva = hyvaKuva(randomKoordinaatti.get());
                    //Lisätään se ryhmään
                    kuvaRyhma.getChildren().add(hyvakuva);
                    //Tehdään TranslateTransitio, jolla saadaan objektit putoamaan sulavasti
                    TranslateTransition animaatio = new TranslateTransition(Duration.seconds(3), hyvakuva);
                    animaatio.setByY(600);
                    animaatio.setCycleCount(1);
                    animaatio.setAutoReverse(false);

                    //Jos animaatio menee loppuun asti(poistuu näytöltä), poistetaan se ryhmästä
                    animaatio.setOnFinished(eventti -> kuvaRyhma.getChildren().remove(hyvakuva));

                    animaatio.play();


                    //Jos 1, niin tehdään huono kuva
                } else if (randomKumpi.get() == 1) {
                    ImageView huonokuva = huonoKuva(randomKoordinaatti.get());
                    kuvaRyhma.getChildren().add(huonokuva);
                    TranslateTransition animaatio2 = new TranslateTransition(Duration.seconds(3), huonokuva);
                    animaatio2.setByY(600);
                    animaatio2.setCycleCount(1);
                    animaatio2.setAutoReverse(false);

                    animaatio2.setOnFinished(eventti -> kuvaRyhma.getChildren().remove(huonokuva));

                    animaatio2.play();


                }


                //Käydään koko ajan for loopilla kuvaryhmän objekteja läpi
                for (Node kuva : new ArrayList<>(kuvaRyhma.getChildren())) {

                    //Muuttuja, jolla päästään käsiksi tämän hetkiseen objektiin
                    ImageView tama = (ImageView) kuva;

                    //Jos kuvan reunat osuu ristiin kissa kuvan reunojen kanssa.
                    if (kuva.getBoundsInParent().intersects(kissaView.getBoundsInParent())) {

                        //Jos kuvan id on "hyvä" (kyseessä hyvä kuva)
                        if (tama.getId().equals("hyvä")) {
                            //Poistetaan
                            kuvaRyhma.getChildren().remove(kuva);
                            //Nostetaan pisteitä
                            pisteet.getAndIncrement();
                            //Päivitetään pisteteksti
                            pisteteksti.setText(String.valueOf(pisteet.get()));
                            //Tehdään kissasta isompi ja hitaampi
                            kissaView.setFitWidth(kissaView.getFitWidth() + 10);
                            kissaView.setFitHeight(kissaView.getFitHeight() + 10);
                            liikkuminen = liikkuminen - 0.75;


                            //Jos kuvan id on "huono" (kyseessä huono kuva)
                        } else if (tama.getId().equals("huono")) {
                            //Poistetaan
                            kuvaRyhma.getChildren().remove(kuva);
                            //Vähennetään pisteitä
                            pisteet.getAndDecrement();
                            //Päivitetään pisteteksti
                            pisteteksti.setText(String.valueOf(pisteet.get()));
                            //Tehdään kissasta pienempi ja nopeampi
                            kissaView.setFitWidth(kissaView.getFitWidth() - 10);
                            kissaView.setFitHeight(kissaView.getFitHeight() - 10);
                            liikkuminen = liikkuminen + 0.75;

                        }
                    }

                    //Jos pisteet on maksimipisteet
                    if(pisteet.get() == maxpisteet.get()){
                        //lopetetaan peli
                        aikajana.stop();
                        //pysäytetään ajanotto
                        endTime.set(System.currentTimeMillis());
                        long lopullinenAika = endTime.get()-startTime.get();

                        //Kutsutaan metodeja
                        voititTeksti();
                        teeTakaisinNappi();
                        teeUudestaanNappi();
                        onkoEnnatys(lopullinenAika);
                        return;
                    }

                    //Jos pisteet on minimipisteet
                    if(pisteet.get() == minpisteet.get()){
                        //lopetetaan peli
                        aikajana.stop();
                        //pysäytetään ajanotto
                        endTime.set(System.currentTimeMillis());
                        long lopullinenAika = endTime.get()-startTime.get();

                        //Kutsutaan metodeja
                        havisitTeksti();
                        teeTakaisinNappi();
                        teeUudestaanNappi();
                        return;
                    }
                }

            }));

            //Pyöritetään animaatiota loputtomiin
            aikajana.setCycleCount(Animation.INDEFINITE);
            aikajana.play();

        }






    }







    /**
     * Metodi, joka luo alareunaan kissan kuvan, jota pelaaja liikuttaa
     */
    private void kissa(){
        kissaView.setFitWidth(200);
        kissaView.setFitHeight(200);
        kissaView.setLayoutX(270);
        kissaView.setLayoutY(450);
        peliPane.getChildren().add(kissaView);
        peliPane.requestFocus();

        //Kun painetaan nappia
        kissaView.setOnKeyPressed(event ->{
            KeyCode nappain = event.getCode();

            //Jos nappi on vasen, pienennetään X:ää liikkumisen verran ja jos nappi on oikea, pistetään x:n arvoksi liikkuminen.
            switch (nappain) {
                case LEFT:
                    X = -liikkuminen;
                    break;
                case RIGHT:
                    X = liikkuminen;
                    break;
                default:
                    break;
            }
        });

        //Jos napista päästetään irti, pistetään kissa pysähtymään
        kissaView.setOnKeyReleased(event ->{
            KeyCode nappain = event.getCode();
            switch (nappain) {
                case LEFT:
                case RIGHT:
                    X = 0;
                    break;
                default:
                    break;
            }
        });

        //Käsittelijä kissan animaatiolle
        javafx.animation.AnimationTimer animaatio = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                kissaView.setLayoutX(kissaView.getLayoutX() + X);
            }
        };
        animaatio.start();
    }
}



