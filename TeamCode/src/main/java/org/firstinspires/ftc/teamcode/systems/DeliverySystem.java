package org.firstinspires.ftc.teamcode.systems;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DeliverySystem {
    private DcMotor shootMotor;
    private CRServo feeder;
    private double feedSpeed = 0;
    private double maxFeedSpeed = 1.0;
    private Telemetry telemetry;
    public void init(HardwareMap hMap, Telemetry telemetry){
        shootMotor = hMap.get(DcMotor.class, "flywheel");
        shootMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        feeder = hMap.get(CRServo.class, "feedservo");
        this.telemetry = telemetry;
        telemetry.addData("Delivery System Initialized","☄✅");
    }
    //flywheel speed
    public void setFlywheelSpeed(double flySpeed){
        shootMotor.setPower(flySpeed);
    }

    //feed balls when button pressed
    public void feedServo(){
        feeder.setPower(maxFeedSpeed);
    }



}
