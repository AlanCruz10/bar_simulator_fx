package com.bspc.bar_simulator_fx.threads;

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
import java.util.concurrent.TimeUnit;

@Getter @Setter @Slf4j
public class ClientThread2 extends Observable implements Runnable{

    private MonitorBar monitorBar;

    public ClientThread2(MonitorBar monitorBar){
        this.monitorBar = monitorBar;
    }

    @Override
    @SneakyThrows
    public void run() {
        log.info(Thread.currentThread().getName() + " THREAD CLIENT");
        while (true){
            Receptionist2 receptionist2 = monitorBar.startAttendClient();
            Platform.runLater(()->{
                this.setChanged();
                this.notifyObservers(receptionist2);
            });
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(5) + 1);
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
            monitorBar.startDrink();
            TimeUnit.SECONDS.sleep(random.nextInt(5) + 1);
            monitorBar.endDrink();
            Client2 client21 = monitorBar.exitClient();
            Platform.runLater(()->{
                this.setChanged();
                this.notifyObservers(client21);
            });
        }
    }

}