package com.bspc.bar_simulator_fx.controllers;

import com.bspc.bar_simulator_fx.models.Bartender2;
import com.bspc.bar_simulator_fx.threads.BartenderThread2;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

@Getter
@Setter
public class BartenderController implements Observer {

    private AnchorPane pane;

    public BartenderController(AnchorPane pane) {
        this.pane = pane;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BartenderThread2){
            Bartender2 bartender2 = (Bartender2) arg;
            if(!bartender2.isStatus()){
                Node node = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), bartender2.getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Bartender"));
                Node node2 = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), bartender2.getClient().getChair().getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Chair"));
                Rectangle rectangle1 = (Rectangle) node;
                Rectangle rectangle = (Rectangle) node2;
                rectangle1.setLayoutX(rectangle.getLayoutX());
                rectangle1.setLayoutY(rectangle.getLayoutY() - 20);
                rectangle1.setId(bartender2.getFigure().getId());
                rectangle1.setFill(Paint.valueOf("#ff3838"));
            }else {
                Node node = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), bartender2.getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Bartender"));
                Rectangle rectangle1 = (Rectangle) node;
                rectangle1.setLayoutX(261);
                rectangle1.setLayoutY(27);
                rectangle1.setId(bartender2.getFigure().getId());
                rectangle1.setFill(Paint.valueOf("#f295ff"));
            }
        }
    }
}
