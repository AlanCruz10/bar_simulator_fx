package com.bspc.bar_simulator_fx.controllers;

import com.bspc.bar_simulator_fx.models.*;
import com.bspc.bar_simulator_fx.threads.BarThread2;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;


public class BarController {

    @FXML
    public AnchorPane pane;

    @SneakyThrows
    @FXML
    public void starSimulation(MouseEvent mouseEvent) {
        Bar2 bar = new Bar2(20, 1, 1, pane);

        bar.printBar();

        ClientController clientController = new ClientController(pane);
        BartenderController bartenderController = new BartenderController(pane);

        BarThread2 barThread = new BarThread2(bar, clientController, bartenderController);

        Thread barSimulator = new Thread(barThread);
        barSimulator.start();

    }

    @FXML
    public void endSimulation(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(1);
    }

}