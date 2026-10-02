package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class deliverySystem {
    private DcMotor shootMotor;
    private CRServo feeder;
    private double feedSpeed;
    private Telemetry telemetry;

    public void init(HardwareMap hMap, Telemetry telemetry){
        shootMotor = hMap.get(DcMotor.class,"deliveryMotor");
        feeder = hMap.get(CRServo.class, "feedServo");
        this.telemetry = telemetry;
        telemetry.addData("Delivery System Initialized","✅");
    }
    //set shoot speed
    private void setShootSpeed(double speedShoot){
        shootMotor.setPower(speedShoot);
    }
    //feed balls
    private void feedServo(boolean buttonPress){
        if (buttonPress){
            feeder.setPower(feedSpeed);
        } else{
            feeder.setPower(0);
        }
    }
    
}
