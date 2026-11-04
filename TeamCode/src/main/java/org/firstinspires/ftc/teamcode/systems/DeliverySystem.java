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
    private ElapsedTime elapsedTime = new ElapsedTime();
    private enum FeedStatus {
        FEED_STOP,
        FEED_FORWARD,
        FEED_BACKWARD
    }

    public FeedStatus feedState = FeedStatus.FEED_STOP;
    public void init(HardwareMap hMap, Telemetry telemetry){
        shootMotor = hMap.get(DcMotorEx.class, "flywheel");
        shootMotor.setDirection(DcMotor.Direction.FORWARD);
        feeder = hMap.get(CRServo.class, "feedservo");
        this.telemetry = telemetry;
        telemetry.addData("Delivery System Initialized","☄✅");
        elapsedTime.startTime();
    }
    //flywheel speed
    public void setFlywheelSpeed(double flySpeed){
        shootMotor.setPower(flySpeed);
        telemetry.addData("Flywheel Velocity:",shootMotor.getVelocity());
    }

    //feed balls when button pressed
    public void feedServo(){
        switch(feedState) {
            case FEED_STOP:
                if (feeder.getPower() == 0) {
                    feeder.setPower(maxFeedSpeed);
                }
                elapsedTime.reset();
                feedState = FeedStatus.FEED_FORWARD;
                telemetry.addData("Feeder:","⏩ Feeding");
                break;
            case FEED_FORWARD:
                if (elapsedTime.milliseconds() > 8.5) {
                    feeder.setPower(0);
                    feedState = FeedStatus.FEED_STOP;
                    telemetry.addData("Feeder:","🛑 Stopping");
                }
                break;
            case FEED_BACKWARD:
                break;
            default:
                break;
        }

    }



}
