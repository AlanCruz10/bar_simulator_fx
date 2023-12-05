package com.bspc.bar_simulator_fx.threads;

import com.bspc.bar_simulator_fx.controllers.BartenderController;
import com.bspc.bar_simulator_fx.controllers.ClientController;
import com.bspc.bar_simulator_fx.models.Bar2;
import com.bspc.bar_simulator_fx.monitors.MonitorBar;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter @Slf4j
public class BarThread2 implements Runnable{

    private Bar2 bar;

    private ClientController clientController;

    private BartenderController bartenderController;

    public BarThread2(Bar2 bar, ClientController clientController, BartenderController bartenderController) {
        this.bar = bar;
        this.clientController = clientController;
        this.bartenderController = bartenderController;
    }

    @SneakyThrows
    @Override
    public void run() {

        log.info(Thread.currentThread().getName() + " THREAD BAR");

        MonitorBar monitorBar = new MonitorBar(getBar());

        BartenderThread2 bartenderThread = new BartenderThread2(monitorBar);
        bartenderThread.addObserver(getBartenderController());

        ClientGenerateThread2 clientGenerateThread = new ClientGenerateThread2(monitorBar, bartenderThread);
        clientGenerateThread.addObserver(getClientController());
        Thread generateClient = new Thread(clientGenerateThread);

        generateClient.start();

        generateClient.join();

    }
}