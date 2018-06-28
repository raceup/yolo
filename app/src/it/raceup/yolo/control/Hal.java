package it.raceup.yolo.control;

import it.raceup.yolo.models.Car;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

/**
 * Parses inputs and updates model
 */
public class Hal {
    private Car car = new Car();

    public void update(Raw data) {
        car.update(data);
    }

    public double get(Type type, int motor) {
        return car.get(type, motor);
    }
}
