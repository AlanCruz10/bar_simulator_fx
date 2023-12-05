package com.bspc.bar_simulator_fx.models;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter @Setter
public class Bar2 {

    private Receptionist2 receptionist;

    private Bartender2 bartender;

    private int capacityBar;

    private int bartenders;

    private int receptionists;

    private int chairsOccupied;

    private boolean attend;

    private List<Bartender2> bartenderList = new ArrayList<>();

    private List<Chair2> chairs = new ArrayList<>();

    private List<Client2> clientsListSeated;

    private List<Receptionist2> receptionistList = new ArrayList<>();

    private List<Client2> clientsListWait = new ArrayList<>();

    private int[] positionsX;

    private int[] positionsY;

    private int[] positionXBartender;

    private int[] positionYBartender;

    private int[] positionXReceptionist;

    private int[] positionYReceptionist;

    private AnchorPane pane;

    public Bar2(int capacityBar, int bartenders, int receptionists, AnchorPane pane) {
        this.capacityBar = capacityBar;
        this.bartenders = bartenders;
        this.receptionists =  receptionists;
        this.clientsListSeated = new ArrayList<>();
        this.chairsOccupied = 0;
        this.positionsX = addPositionX();
        this.positionsY = addPositionY();
        this.positionXBartender = addPositionXBartender();
        this.positionYBartender = addPositionYBartender();
        this.positionXReceptionist = addPositionXReceptionist();
        this.positionYReceptionist = addPositionYReceptionist();
        this.pane = pane;
        for (int i = 0; i < getBartenders(); i++) {
            Bartender2 bartender = new Bartender2();
            bartender.setId(i);
            bartender.setFigure(createFigureRectangle(getPositionXBartender()[i], getPositionYBartender()[i], 16, 35, "#f295ff", String.format("bartender%d",i)));
            bartender.setStatus(true);
            bartender.setClient(null);
            getBartenderList().add(bartender);
        }
        for (int i = 0; i < getReceptionists(); i++) {
            Receptionist2 receptionist = new Receptionist2();
            receptionist.setFigure(createFigureRectangle(getPositionXReceptionist()[i], getPositionYReceptionist()[i], 16, 35, "#44eb39", String.format("receptionist%d", i)));
            receptionist.setId(i);
            receptionist.setStatus(true);
            getReceptionistList().add(receptionist);
        }
        for (int i = 0; i < getCapacityBar(); i++) {
            Chair2 chair = new Chair2();
            chair.setId(i);
            chair.setStatus(true);
            chair.setFigure(createFigureRectangle(getPositionsX()[i],getPositionsY()[i], 16, 17, "#079073", String.format("chair%d", i)));
            getChairs().add(chair);
        }
        this.receptionist = getReceptionistList().get(0);
        this.bartender = getBartenderList().get(0);
        this.attend = false;
    }


    public int[] addPositionX(){
        return new int[]{40, 64, 88, 112, 137, 159, 186, 211, 236, 261, 287, 311, 335, 359, 384, 406, 433, 458, 483, 508};
    }

    public int[] addPositionY(){
        return  new int[]{112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112};
    }

    public int[] addPositionXBartender(){
            return new int[]{261, 287};
    }

    public int[] addPositionYBartender(){
        return new int[]{27, 27};
    }

    public int[] addPositionXReceptionist(){
        return new int[]{367};
    }

    public int[] addPositionYReceptionist(){
        return new int[]{232};
    }

    public Rectangle createFigureRectangle(int px, int py, int w, int h, String color, String id){
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(w);
        rectangle.setHeight(h);
        rectangle.setLayoutX(px);
        rectangle.setLayoutY(py);
        rectangle.setFill(Paint.valueOf(color));
        rectangle.setId(id);
        return rectangle;
    }

    public Circle createFigureCircle(int px, int py, int radius, String color, String id){
        Circle circle = new Circle();
        circle.setId(id);
        circle.setLayoutX(px);
        circle.setLayoutY(py);
        circle.setRadius(radius);
        circle.setFill(Paint.valueOf(color));
        return circle;
    }

    public void printBar(){
        for (Chair2 c : getChairs()) {
            getPane().getChildren().add(c.getFigure());
        }
        for (Receptionist2 r : getReceptionistList()){
            getPane().getChildren().add(r.getFigure());
        }
        for (Bartender2 b : getBartenderList()){
            getPane().getChildren().add(b.getFigure());
        }
    }

    public String getColorRandom() {
        Random random = new Random();
        String[] colors = new String[]{"#ecbe13", "#66ffff", "#7ab317", "#a013ff", "#57beea", "#2d1b33"};
        return colors[random.nextInt(colors.length)];
    }

}