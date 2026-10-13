package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class deliverySystem {
    private DcMotor shootMotor;
    private CRServo feeder;
    private double feedSpeed;
    private Telemetry telemetry;
    public void init(HardwareMap hMap, Telemetry telemetry){
        shootMotor = hMap.get(DcMotor.class, "Flywheel");
        shootMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        feeder = hMap.get(CRServo.class, "Feed Servo");
        this.telemetry = telemetry;
        telemetry.addData("Delivery System Initialized","☄✅");
    }
    //flywheel speed
    public void setFlywheelSpeed(double flySpeed){
        shootMotor.setPower(flySpeed);
    }

    //feed balls when button pressed
    public void feedServo(boolean feed){
        if (feed == true){
            feeder.setPower(feedSpeed);
        } else {
            feeder.setPower(0);
        }
    }



}
