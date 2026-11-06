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
    public void init(HardwareMap hMap, Telemetry telemetry){
        shootMotor = hMap.get(DcMotorEx.class, "flywheel");
        shootMotor.setDirection(DcMotor.Direction.FORWARD);
        feeder = hMap.get(CRServo.class, "feedservo");
        this.telemetry = telemetry;
        telemetry.addData("Delivery System Initialized","☄✅");
    }
    //flywheel speed
    public void setFlywheelSpeed(double flySpeed){
        shootMotor.setPower(flySpeed);
        telemetry.addData("Flywheel Velocity:",shootMotor.getVelocity());
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
