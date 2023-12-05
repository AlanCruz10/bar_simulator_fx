package com.bspc.bar_simulator_fx.monitors;

import com.bspc.bar_simulator_fx.models.*;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Random;

@Getter @Setter @Slf4j
public class MonitorBar {

    private Bar2 bar;

    private int id;

    private int positionX;

    private int positionY;

    public MonitorBar(Bar2 bar){
        this.bar = bar;
        this.id = 1;
        this.positionX = 400;
        this.positionY = 250;
    }

    public synchronized Client2 generateClient() {
        Client2 client = new Client2();
        client.setChair(null);
        client.setBartender(null);
        client.setStatus(false);
        client.setDrink(false);
        client.setLeave(false);
        int idClient = getId();
        client.setId(idClient);
        // color base of client "#ebff1e"
        client.setFigure(getBar().createFigureCircle(getPositionX(), getPositionY(), 8, getBar().getColorRandom(), String.valueOf(idClient)));
        getBar().getClientsListWait().add(client);
        idClient++;
        setId(idClient);
        log.info("CLIENT "+ client.getId() +" ARRIVED");
        Receptionist2 receptionist = getBar().getReceptionist();
        if(getBar().getClientsListWait().size() == 1 || receptionist.isStatus())
            receptionist.setStatus(false);
        this.notifyAll();
        return client;
    }

    @SneakyThrows
    public synchronized Receptionist2 startAttendClient() {
        List<Client2> clientsListWait = getBar().getClientsListWait();
        List<Client2> clientsListSeated = getBar().getClientsListSeated();
        boolean chartAvailable = getBar().getChairs().stream().noneMatch(Chair2::isStatus);
        Receptionist2 receptionist = getBar().getReceptionist();
        while (clientsListWait.isEmpty()
                || clientsListSeated.size() == getBar().getCapacityBar()
                || chartAvailable
                || receptionist.isStatus()){
            this.wait();
        }
        receptionist.setStatus(false);
        log.info("RECEPTIONIST "+ receptionist.getId() +" ATTENDING CLIENT " + clientsListWait.get(0).getId());
        this.notifyAll();
        return receptionist;
    }

    public synchronized Receptionist2 endAttendClient(){
        Receptionist2 receptionist = getBar().getReceptionist();
        List<Client2> clientsListWait = getBar().getClientsListWait();
        receptionist.setStatus(true);
        Client2 client = clientsListWait.get(0);
        getBar().setClientsListWait(clientsListWait);
        log.info("RECEPTIONIST "+ receptionist.getId() +" ATTENDED CLIENT " + client.getId());
        this.notifyAll();
        return receptionist;
    }

    @SneakyThrows
    public synchronized Client2 enterClient() {
        List<Client2> clientsListSeated = getBar().getClientsListSeated();
        List<Client2> clientsListWait = getBar().getClientsListWait();
        Receptionist2 receptionist = getBar().getReceptionist();
        List<Chair2> chairsList = getBar().getChairs();
        boolean chartAvailable = chairsList.stream().noneMatch(Chair2::isStatus);
        List<Chair2> chairsListAvailable = chairsList.stream().filter(Chair2::isStatus).toList();
        while(chartAvailable
                || clientsListSeated.size() == getBar().getCapacityBar()
                || clientsListWait.isEmpty()
                || !receptionist.isStatus()
                || chairsListAvailable.isEmpty()){
            this.wait();
        }
        Client2 client = clientsListWait.get(0);
        clientsListWait.remove(client);
        Random random = new Random();
        Chair2 chair = chairsListAvailable.get(random.nextInt(chairsListAvailable.size()));
        chairsList.forEach(c -> {
            if(c.getId() == chair.getId())
                c.setStatus(false);
        });
        getBar().setChairs(chairsList);
        client.setChair(chair);
        client.setStatus(true);
        clientsListSeated.add(client);
        getBar().setClientsListSeated(clientsListSeated);
        log.info("CLIENT "+ client.getId() +" ENTERED");
        this.notifyAll();
        return client;
    }

    @SneakyThrows
    public synchronized Bartender2 startAttendClientByBartender(){

        List<Client2> clientsListSeated = getBar().getClientsListSeated();
        boolean chartAvailable = getBar().getChairs().stream().allMatch(Chair2::isStatus);
        Bartender2 bartender = getBar().getBartender();
        while (clientsListSeated.isEmpty() || chartAvailable || !bartender.isStatus()){
            this.wait();
        }
        Client2 client2 = clientsListSeated.get(0);
        bartender.setStatus(false);
        bartender.setClient(client2);
        client2.setBartender(bartender);

        log.info("BARTENDER "+ bartender.getId() +" ATTENDING CLIENT " + clientsListSeated.get(0).getId());
        this.notifyAll();
        return bartender;
    }

    public synchronized Bartender2 endAttendClientByBartender(){

        Bartender2 bartender = getBar().getBartender();
        List<Client2> clientsListWait = getBar().getClientsListSeated();
        bartender.setStatus(true);
        bartender.setClient(null);
        Client2 client = clientsListWait.get(0);
        client.setBartender(null);
        client.setDrink(true);
        getBar().setClientsListSeated(clientsListWait);
        log.info("BARTENDER "+ bartender.getId() +" ATTENDED CLIENT " + client.getId());
        this.notifyAll();
        return bartender;
    }

    @SneakyThrows
    public synchronized void startDrink(){
        List<Client2> clientsListSeated = getBar().getClientsListSeated();
        Client2 client = clientsListSeated.get(0);
        List<Chair2> chairs = getBar().getChairs();
        while (clientsListSeated.size() == 0 || chairs.stream().allMatch(Chair2::isStatus) || !client.isDrink()){
            this.wait();
        }
        log.info("CLIENT "+ client.getId() +" DRINKING");
        this.notifyAll();
    }

    @SneakyThrows
    public synchronized void endDrink(){
        List<Client2> clientsListSeated = getBar().getClientsListSeated();
        Client2 client = clientsListSeated.get(0);
        client.setDrink(false);
        client.setLeave(true);
        getBar().setClientsListSeated(clientsListSeated);
        log.info("CLIENT "+ client.getId() +" DRANK");
        this.notifyAll();
    }

    @SneakyThrows
    public synchronized Client2 exitClient(){
        List<Client2> clientsListSeated = getBar().getClientsListSeated();
        Client2 client = clientsListSeated.get(0);
        List<Chair2> chairs = getBar().getChairs();
        while (clientsListSeated.size() == 0 || chairs.stream().allMatch(Chair2::isStatus) || !client.isLeave()) {
            this.wait();
        }
        clientsListSeated.remove(client);
        Chair2 chair = client.getChair();
        chairs.forEach(c -> {
            if(c.getId() == chair.getId())
                c.setStatus(true);
        });
        getBar().setChairs(chairs);
        client.setStatus(false);
        client.setLeave(false);
        getBar().setClientsListSeated(clientsListSeated);
        log.info("CLIENT "+ client.getId() +" EXITED");
        client.setId(0);
        this.notifyAll();
        return client;
    }

}
