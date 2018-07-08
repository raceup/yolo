package it.raceup.yolo.models.kvaser;

public class Test {
    public static void main(String[] args) {
        RemoteKvaser kvaser = new RemoteKvaser(RemoteKvaser.HTTP_SCHEME,
                "localhost", 1729);
        kvaser.setup();
    }
}
