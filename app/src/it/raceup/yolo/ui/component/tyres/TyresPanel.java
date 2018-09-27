package it.raceup.yolo.ui.component.tyres;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.models.car.Motors.DEFAULT_MOTORS;
import static it.raceup.yolo.models.data.Raw.isBoolean;

public class TyresPanel extends JPanel implements Observer {
    private final TyreInfo[] tyrePanels = new TyreInfo[DEFAULT_MOTORS.length];

    public TyresPanel() {
        for (int i = 0; i < DEFAULT_MOTORS.length; i++) {
            tyrePanels[i] = new TyreInfo(DEFAULT_MOTORS[i]);
        }

        setup();
    }

    private void setup() {
        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
        up.add(tyrePanels[0]);
        up.add(Box.createRigidArea(new Dimension(10, 0)));
        up.add(tyrePanels[1]);

        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));
        down.add(tyrePanels[2]);
        down.add(Box.createRigidArea(new Dimension(10, 0)));
        down.add(tyrePanels[3]);

        add(up);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(down);
    }


    public void update(Raw data) {
        update(data.getMotor(), data.getType(), data.getRaw());
    }

    public void update(int motor, Type type, Double data) {
        tyrePanels[motor].update(type.toString(), data, isBoolean(type));
    }

    private void update(it.raceup.yolo.models.car.Motor[] motors) {
        for (int i = 0; i < motors.length; i++) {
            for (Type type : Raw.ALL) {
                double value = motors[i].get(type);  // fetch from model
                update(i, type, value);  // updateWith view
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            update((it.raceup.yolo.models.car.Motor[]) o);
        } catch (Exception e) {
            new YoloException("cannot update tyres", e, ExceptionType.VIEW)
                    .print();
        }
    }
}
