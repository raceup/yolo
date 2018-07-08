package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.models.data.CanMessage;

import static core.Canlib.canBITRATE_1M;

public class Test {
    public static void main(String[] args) {
        RemoteKvaser kvaser = new RemoteKvaser(RemoteKvaser.HTTP_SCHEME,
                "localhost", 1729);

        boolean isOk = kvaser.openConnection();
        System.out.println(isOk);

        isOk = kvaser.setupCan(0, 8, 4, canBITRATE_1M);
        System.out.println(isOk);

        isOk = kvaser.onBus();
        System.out.println(isOk);

        CanMessage[] messages = kvaser.readCan();
        System.out.println(messages.length);

        isOk = kvaser.offBus();
        System.out.println(isOk);

        isOk = kvaser.closeCan();
        System.out.println(isOk);

        isOk = kvaser.closeConnection();
        System.out.println(isOk);
    }
}
