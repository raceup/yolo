/*
 * Copyright 2016-2017 RaceUp ED
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.raceup.yolo.ui.component.driver;

import it.raceup.yolo.ui.component.ProgressBar;

import javax.swing.*;
import java.awt.*;


/**
 * Panel with info about brakes (total % and rear-front balance)
 */
public class ThrottleBrakePanel extends JPanel {

    private final ProgressBar throttleBar =
            new ProgressBar("Throttle", Color.GREEN);

    private final ProgressBar brakeBar =
            new ProgressBar("Brake", Color.RED);

    public ThrottleBrakePanel() {
        setup();
    }

    public void setBrake(double value) {
        brakeBar.setValue(value);
    }

    /**
     * Sets value of indicator and label and updates gui
     *
     * @param value throttle value
     */
    public void setThrottle(double value) {
        throttleBar.setValue(value);
    }

    /**
     * Setups gui and components
     */
    private void setup() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // vertical
        add(throttleBar);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(brakeBar);
    }
}
