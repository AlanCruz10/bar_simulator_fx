package com.bspc.bar_simulator_fx.threads;

import com.bspc.bar_simulator_fx.models.Bartender2;
import com.bspc.bar_simulator_fx.models.Client2;
import com.bspc.bar_simulator_fx.models.Receptionist2;
import com.bspc.bar_simulator_fx.monitors.MonitorBar;
import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter @Setter @Slf4j
public class ClientGenerateThread2 extends Observable implements Runnable{

    private MonitorBar monitorBar;

    private BartenderThread2 bartenderThread2;

    private ExecutorService executor = Executors.newFixedThreadPool(100);

    private Random random = new Random();

    public ClientGenerateThread2(MonitorBar monitorBar, BartenderThread2 bartenderThread2) {
        this.monitorBar = monitorBar;
        this.bartenderThread2 = bartenderThread2;
    }


    @Override
    @SneakyThrows
    public void run() {
        log.info(Thread.currentThread().getName() + " THREAD GENERATOR CLIENT");
        for (int i = 0; i < 100; i++) {
            Client2 client2 = monitorBar.generateClient();
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    steps();
                }
            });
            Platform.runLater(() -> {
                this.setChanged();
                this.notifyObservers(client2);
            });
            TimeUnit.SECONDS.sleep( 2);
        }
    }

    @SneakyThrows
    private void steps(){
        Receptionist2 receptionist2 = monitorBar.startAttendClient();
        Platform.runLater(()->{
            this.setChanged();
            this.notifyObservers(receptionist2);
        });
        TimeUnit.SECONDS.sleep(1);
        Receptionist2 receptionist21 = monitorBar.endAttendClient();
        Platform.runLater(()->{
            this.setChanged();
            this.notifyObservers(receptionist21);
        });
        Client2 client2 = monitorBar.enterClient();
        Platform.runLater(()->{
            this.setChanged();
            this.notifyObservers(client2);
        });
        Bartender2 bartender = monitorBar.startAttendClientByBartender();
        Platform.runLater(()->{
            this.setChanged();
            this.notifyObservers(bartender);
        });
        TimeUnit.SECONDS.sleep(2);
        Bartender2 bartender1 = monitorBar.endAttendClientByBartender();
        Platform.runLater(()->{
            this.setChanged();
            this.notifyObservers(bartender1);
        });
//        Thread bartender = new Thread(bartenderThread2);
//        bartender.start();
        monitorBar.startDrink();
        TimeUnit.SECONDS.sleep(5);
        monitorBar.endDrink();
        Client2 client21 = monitorBar.exitClient();
        Platform.runLater(()->{
            this.setChanged();
            this.notifyObservers(client21);
        });
    }

}