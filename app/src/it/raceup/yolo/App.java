package it.raceup.yolo;

import it.raceup.yolo.control.Hal;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.Car;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.ui.window.Main;
import it.raceup.yolo.utils.Debugger;

import java.util.Timer;
import java.util.TimerTask;

public class App extends Debugger {
    public static void main(String[] args) {
        Car model = new Car();
        Hal controller = new Hal(model);
        Main view = new Main();

        try {
            controller.startConnection();
        } catch (YoloException e) {
            System.err.println(e.toString());
        }  // start logging Kvaser data

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Raw value = controller.getMostRecentValue();
                view.update(value);
            }
        }, 0, 100);
    }
}
