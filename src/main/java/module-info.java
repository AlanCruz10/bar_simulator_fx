module com.bspc.bar_simulator_fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires lombok;
    requires org.slf4j;

    opens com.bspc.bar_simulator_fx to javafx.fxml;
    exports com.bspc.bar_simulator_fx;
    exports com.bspc.bar_simulator_fx.controllers;
    opens com.bspc.bar_simulator_fx.controllers to javafx.fxml;
}