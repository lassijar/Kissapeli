package com.example.kissapeli;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;




/**
 * Luokka, joka nopeuttaa uusien nappuloiden luomista
 * @author Lassi Järvelä
 */
public class Nappulat extends Button {

    private final String starttiTyyli = "-fx-background-color: gainsboro; -fx-border-color:black";

    public Nappulat(String teksti, int koko){

        setText(teksti);
        setPrefWidth(180);
        setPrefHeight(130);
        setStyle(starttiTyyli);
        setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, koko));

    }


}
