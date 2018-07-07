package it.raceup.yolo.models.kvaser;

public class Test {
    public static void main(String[] args) {
        RemoteKvaser kvaser = new RemoteKvaser("192.168.1.52");
        System.out.println(kvaser.getDeviceStatus());
    }
}
