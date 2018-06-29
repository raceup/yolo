package it.raceup.yolo;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.ui.window.Main;
import it.raceup.yolo.utils.Debugger;

public class App extends Debugger {
    public static void main(String[] args) {
        Main main = new Main();
        main.update(new Raw(42.1, 0, Type.ACTUAL_VELOCITY));
    }
}
