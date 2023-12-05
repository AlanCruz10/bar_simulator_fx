package com.bspc.bar_simulator_fx.controllers;

import com.bspc.bar_simulator_fx.models.Bartender2;
import com.bspc.bar_simulator_fx.models.Client2;
import com.bspc.bar_simulator_fx.models.Receptionist2;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;


@Getter
@Setter
@Slf4j
public class ClientController implements Observer {

    private AnchorPane pane;

    public ClientController(AnchorPane pane) {
        this.pane = pane;
    }

    @Override
    @SneakyThrows
    public void update(Observable o, Object arg) {
        String className = o.getClass().describeConstable().stream().findFirst().orElseThrow(() -> new RuntimeException("Not Found Class")).displayName();
        String instruction = arg.getClass().describeConstable().stream().findFirst().orElseThrow(() -> new RuntimeException("Not Found Instruction")).displayName();
        switch (className){
            case "ClientGenerateThread2" -> {
                switch (instruction){
                    case "Client2"->{
                        Client2 client = (Client2) arg;
                        if(client.getId() > 0 && client.isStatus() && client.getChair() != null){
                            Node node = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), client.getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Client"));
                            Node node2 = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), client.getChair().getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Client"));
                            Circle circle = (Circle) node;
                            Rectangle rectangle = (Rectangle) node2;
                            circle.setLayoutX(rectangle.getLayoutX() + 8);
                            circle.setLayoutY(rectangle.getLayoutY() + 8);
                            circle.setId(client.getFigure().getId());
                            rectangle.setId(client.getChair().getFigure().getId());
                            rectangle.setFill(Paint.valueOf("#ff3838"));
                        }else if(client.getId() > 0) {
                            pane.getChildren().add(client.getFigure());
                        }else {
                            Node node = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), client.getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Client"));
                            Node node2 = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), client.getChair().getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Client"));
                            Circle circle = (Circle) node;
                            Rectangle rectangle = (Rectangle) node2;
                            circle.setLayoutX(109);
                            circle.setLayoutY(252);
                            circle.setId(client.getFigure().getId());
                            rectangle.setId(client.getChair().getFigure().getId());
                            rectangle.setFill(Paint.valueOf("#079073"));
                        }
                    }
                    case "Receptionist2"->{
                        Receptionist2 receptionist2 = (Receptionist2) arg;
                        Node node = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), receptionist2.getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Receptionist"));
                        Rectangle rectangle = (Rectangle) node;
                        if(receptionist2.isStatus()){
                            rectangle.setFill(Paint.valueOf("#44eb39"));
                        }else {
                            rectangle.setFill(Paint.valueOf("#ff3838"));
                        }
                    }
                    case "Bartender2"->{
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
            case "ClientThread2"->{
                switch (instruction){
                    case "Client2"->{
                        Client2 client = (Client2) arg;
                        Node node = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), client.getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Client"));
                        Node node2 = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), client.getChair().getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Client"));
                        Circle circle = (Circle) node;
                        Rectangle rectangle = (Rectangle) node2;
                        if(client.getId() > 0 && client.isStatus()){
                            circle.setLayoutX(rectangle.getLayoutX() + 8);
                            circle.setLayoutY(rectangle.getLayoutY() + 8);
                            circle.setId(client.getFigure().getId());
                            rectangle.setId(client.getChair().getFigure().getId());
                            rectangle.setFill(Paint.valueOf("#ff3838"));
                        }else if(client.getId() > 0) {
                            pane.getChildren().add(client.getFigure());
                        }else {
                            circle.setLayoutX(109);
                            circle.setLayoutY(252);
                            circle.setId(client.getFigure().getId());
                            rectangle.setId(client.getChair().getFigure().getId());
                            rectangle.setFill(Paint.valueOf("#079073"));
                        }
                    }
                    case "Receptionist2"->{
                        Receptionist2 receptionist2 = (Receptionist2) arg;
                        Node node = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), receptionist2.getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Receptionist"));
                        Rectangle rectangle = (Rectangle) node;
                        if(receptionist2.isStatus()){
                            rectangle.setFill(Paint.valueOf("#44eb39"));
                        }else {
                            rectangle.setFill(Paint.valueOf("#ff3838"));
                        }
                    }
                }
            }
            case "ReceptionistThread"->{
                switch (instruction){
                    case "Receptionist2"->{
                        Receptionist2 receptionist2 = (Receptionist2) arg;
                        Node node = pane.getChildren().stream().filter(r -> Objects.equals(r.getId(), receptionist2.getFigure().getId())).findAny().orElseThrow(() -> new RuntimeException("Not Found Receptionist"));
                        Rectangle rectangle = (Rectangle) node;
                        if(receptionist2.isStatus()){
                            rectangle.setFill(Paint.valueOf("#44eb39"));
                        }else {
                            rectangle.setFill(Paint.valueOf("#ff3838"));
                        }
                    }
                    default -> {
//                        log.info("Default");
                    }
                }
            }
        }

    }
}
