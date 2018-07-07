package it.raceup.yolo.models.kvaser;

/**
 * Class description
 */
public class Test {
    public Test() {
        //
    }

    /**
     * Method description
     */
    public static void main(String[] args) {
        RemoteKvaser kvaser = new RemoteKvaser("192.168.1.52");
        System.out.println(kvaser.getDeviceStatus());
    }

}
