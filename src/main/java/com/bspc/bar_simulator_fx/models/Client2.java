package com.bspc.bar_simulator_fx.models;

import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Client2 {

    private long id;

    private boolean status;

    private boolean leave;

    private boolean drink;

    private Chair2 chair;

    private Bartender2 bartender;

    private Circle figure;

}