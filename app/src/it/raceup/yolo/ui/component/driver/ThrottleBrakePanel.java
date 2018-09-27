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

import com.raceup.ed.vsm.gui.components.BrakesPanel;
import com.raceup.ed.vsm.gui.components.ThrottleBrakeIndicator;

import javax.swing.*;

/**
 * Panel with info about brakes (total % and rear-front balance)
 */
public class ThrottleBrakePanel extends JPanel {
    private final ThrottleBrakeIndicator throttleBrakeIndicator;  // ring indicator
    private final BrakesPanel brakesPanel = new BrakesPanel();

    public ThrottleBrakePanel() {
        // super("Throttle and brakes");

        throttleBrakeIndicator = new ThrottleBrakeIndicator();  // % indicator

        setup();
    }

    /*
     * Values
     */

    /**
     * Sets value of indicator and label and updates gui
     *
     * @param value         new value to set
     * @param brakePosition position of brake to set value of
     */
    public void setBrakeValue(double value, int brakePosition) {
        brakesPanel.setValue(value, brakePosition);
    }

    /**
     * Sets value of indicator and label and updates gui
     *
     * @param value throttle value
     */
    public void setThrottleValue(double value) {
        throttleBrakeIndicator.setValue(value);
    }

    /**
     * Setups gui and components
     */
    private void setup() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));  // add components vertically

        JPanel labelsPanel = new JPanel();
        labelsPanel.add(brakesPanel);

        mainPanel.add(throttleBrakeIndicator);
        mainPanel.add(labelsPanel);

        add(mainPanel);
    }
}
