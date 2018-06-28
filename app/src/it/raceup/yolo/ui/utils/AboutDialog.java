/*
 *  Copyright 2016-2018 Race Up Electric Division
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package it.raceup.yolo.ui.utils;

import javax.swing.*;

/**
 * A simple about dialog with given content and title
 */
public class AboutDialog extends JDialog {
    public AboutDialog(JFrame parent, String content, String title) {
        super(parent, title, true);
        add(new JLabel(content));

        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20,
                20));  // border
        pack();  // set size based on content
        setLocationRelativeTo(null);  // center in screen

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // destroy
    }
}
