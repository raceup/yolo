package it.raceup.yolo.ui.component.tyres;

import it.raceup.yolo.ui.component.table.TablePanel;

public class TyreInfo extends TablePanel {
    private static final String[] LABELS =
            new String[]{
                    "Potentiometer",
            };

    public TyreInfo(String tag) {
        super(LABELS, tag);
    }
}
