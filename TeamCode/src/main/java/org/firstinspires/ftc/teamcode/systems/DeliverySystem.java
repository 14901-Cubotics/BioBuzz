package org.firstinspires.ftc.teamcode.systems;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DeliverySystem {
    private DcMotorEx shootMotor;
    private CRServo feeder;
    private double maxFeedSpeed = 1.0;
    private Telemetry telemetry;
    private double feedTime = 300.0;
    private ElapsedTime elapsedTime = new ElapsedTime();
    final private int maxFlySpeed = 2500;
    //when using the same speed (ex 1900), the bigger balls are propelled farther into the sky whereas the smaller balls are propelled farther horizontally.
    //the geometry of the ramp for the flywheel needs a different design; fit both small and large balls and not bend.
    private int currentFlySpeed = maxFlySpeed;
    public void init(HardwareMap hMap, Telemetry telemetry){
        shootMotor = hMap.get(DcMotorEx.class, "flywheel");
        shootMotor.setDirection(DcMotor.Direction.FORWARD);
        shootMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        feeder = hMap.get(CRServo.class, "feedservo");
        this.telemetry = telemetry;
        telemetry.addData("Delivery System Initialized","☄✅");
    }

    //flywheel speed

    public void setFlywheelSpeed(double flySpeed){
        shootMotor.setPower(flySpeed);
    }
    public void lowerFlywheelVelocity(){
        if (currentFlySpeed < 100) {
            currentFlySpeed = 0;
        } else {
            currentFlySpeed -= 100;
        }
        shootMotor.setVelocity(currentFlySpeed);
    }

    public void raiseFlywheelVelocity(){
        if (currentFlySpeed < maxFlySpeed) {
            currentFlySpeed += 100;
        } else {
            currentFlySpeed = maxFlySpeed;
        }
        shootMotor.setVelocity(currentFlySpeed);
    }

    public double getFlywheelVelocity(){
        double velocity = shootMotor.getVelocity();
        return velocity;

    }
    //feed balls when button pressed
    public void feedServo(){
        if (feeder.getPower() < 1.0) {
            telemetry.addData("Feeder", "Activated");
            feeder.setPower(maxFeedSpeed);
            elapsedTime.reset();
            while (elapsedTime.milliseconds() < feedTime) {
                telemetry.addData("Feeder:", "⏩ Feeding");
            }
            feeder.setPower(0);
        }
    }



}
