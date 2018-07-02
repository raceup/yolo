package it.raceup.yolo;

import it.raceup.yolo.control.Hal;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.Car;
import it.raceup.yolo.utils.Debugger;

public class App extends Debugger {
    public static void main(String[] args) {
        Car model = new Car();
        Hal controller = new Hal(model);
        // Main view = new Main();

        try {
            controller.startConnection();
        } catch (YoloException e) {
            System.err.println(e.toString());
        }
    }
}
