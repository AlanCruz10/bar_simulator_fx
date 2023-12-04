package com.bspc.bar_simulator_fx.models;

import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Bartender2 {

    private long id;

    private boolean status;

    private Client2 client;

    private Rectangle figure;

}