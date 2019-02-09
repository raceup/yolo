package it.raceup.yolo.ui.component.imu;

import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.ui.window.DriverFrame;

import javax.swing.*;
import java.awt.*;

public class ImuPanel extends JPanel {
    private final AccelerationsPanel accelerationsPanel = new
            AccelerationsPanel();
    //private final YawPanel yawPanel = new YawPanel();
    private final JButton driverButton = new JButton("Driver Info");
    private DriverFrame driverFrame = new DriverFrame();

    public ImuPanel() {
        setup();
    }

    private void setup() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        driverButton.addActionListener(e -> openDriverPanel());
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(accelerationsPanel);
        add(Box.createRigidArea(new Dimension(10, 0)));
        //add(yawPanel);
        add(driverButton);


    }
    public void updateAccelerationPanel(Imu imu){
        accelerationsPanel.update(imu);
    }

    /*
    public void updateYawPanel(Imu imu){
        yawPanel.update(imu);
    }
    */
    private void openDriverPanel(){
        if(driverFrame.isVisible()){
            driverFrame.close();
        }
        else {
            driverFrame.open();
        }
    }
}
