package it.raceup.yolo.models.car;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Parser;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Tyre extends Observable implements Observer {
    private double[] temperatures;
    private Long time;

    public Tyre() {
        time = 0L;
        temperatures = new double[4];
    }

    private Tyre(double[] temperatures, long time) {
        this.temperatures = temperatures;
        this.time = time;
    }


    private void update(Raw[] raw) {
        setDriverData(raw);
    }

    private void setTyreTime(Long time) {
        this.time = time;
    }

    private void setDriverData(Raw[] raw) {
        setTyreTime(0L);
        for (int i = 0; i < raw.length; i++) {
            switch (raw[i].getType()) {
                case WHEEL_TEMPERATURE: {
                    temperatures[0] = raw[i++].getRaw();
                    temperatures[1] = raw[i++].getRaw();
                    temperatures[2] = raw[i++].getRaw();
                    temperatures[3] = raw[i++].getRaw();
                }
            }

        }

    }

    public double[] getTyreData(Type type) {
        switch (type) {
            case WHEEL_TEMPERATURE:
                return temperatures;
        }
        return new double[]{0};
    }

    private void update(ArrayList<CanMessage> data) {
        for (CanMessage message : data) {
            Raw[] packets = new Parser(message).getParsedData();
            if (packets.length > 0) {
                update(packets);
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            FromKvaserMessage message = new FromKvaserMessage(o);
            ArrayList<CanMessage> messages = message.getAsCanMessages();
            if (messages != null) {
                update(messages);
                triggerObservers();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new YoloException("cannot update Imu car", e, ExceptionType.DRIVER).print();
        }

    }

    private void triggerObservers() {
        setChanged();
        notifyObservers(new Tyre(temperatures, time));
    }
}
