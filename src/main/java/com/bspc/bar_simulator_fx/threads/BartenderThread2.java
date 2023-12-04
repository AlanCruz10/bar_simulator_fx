package com.bspc.bar_simulator_fx.threads;

import com.bspc.bar_simulator_fx.models.Bartender2;
import com.bspc.bar_simulator_fx.monitors.MonitorBar;
import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Getter @Setter @Slf4j
public class BartenderThread2 extends Observable implements Runnable{

    private MonitorBar monitorBar;

    private Random random = new Random();
    public BartenderThread2(MonitorBar monitorBar) {
        this.monitorBar = monitorBar;
    }

    @Override
    @SneakyThrows
    public void run() {
        log.info(Thread.currentThread().getName() + " THREAD BARTENDER");
//        Bartender2 bartender2 = monitorBar.getBar().getBartender();
        while (true){
            Bartender2 bartender = monitorBar.startAttendClientByBartender();
            Platform.runLater(()->{
                this.setChanged();
                this.notifyObservers(bartender);
            });
            TimeUnit.SECONDS.sleep(5);
            Bartender2 bartender1 = monitorBar.endAttendClientByBartender();
            Platform.runLater(()->{
                this.setChanged();
                this.notifyObservers(bartender1);
            });
        }
    }
}