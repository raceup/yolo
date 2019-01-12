package it.raceup.yolo.ui.component.driver;

class ThrottleBrake{

    private double ThrottleValue;
    private double BrakeValue;
    public ThrottleBrake(double ThrottleValue, double BrakeValue){
        this.ThrottleValue = ThrottleValue;
        this.BrakeValue = BrakeValue;
    }
    public double getThrottleValue(){
        return ThrottleValue;
    }
    public double getBrakeValue(){
        return BrakeValue;
    }
}