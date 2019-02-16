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

package it.raceup.yolo.ui.component;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.utils.Misc.limitValue;

public class ProgressBar extends JPanel {
    private final JProgressBar progress = new JProgressBar();

    public ProgressBar(String title, Color color) {
        super();

        setup(title, color);
    }

    private void setup(String title, Color color) {
        // creates progress bar
        progress.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        progress.setStringPainted(true);
        progress.setFont(progress.getFont().deriveFont(24f));
        progress.setForeground(color);
        setupLayout(title);
    }

    private void setupLayout(String title) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // vertical

        // creates label
        add(new JLabel(title));

        // adds progress bar
        add(progress);
    }

    //todo fix this
    public void setValue(double value) {
        value = limitValue(value, 0, 100);  // limit 0 - 100%
        value = 40;
        progress.setValue((int) value);
        progress.setString("prova");
        progress.updateUI();
        //System.out.println((int) value);

    }
}
