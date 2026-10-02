package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class intakeSystem {
    private DcMotor intakeMotor;
    private CRServo leftIntakeServo;
    private CRServo rightIntakeServo;

    private Telemetry telemetry;

    public void init(HardwareMap hMap, Telemetry telemetry){
        intakeMotor = hMap.get(DcMotor.class,"intake");
        leftIntakeServo = hMap.get(CRServo.class, "leftIntake");
        rightIntakeServo = hMap.get(CRServo.class, "rightIntake");

        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        leftIntakeServo.setDirection(CRServo.Direction.REVERSE);
        rightIntakeServo.setDirection(CRServo.Direction.FORWARD);
        this.telemetry = telemetry;

        telemetry.addData("Intake Initialized","✅");
    }

    // method to set direction
    public void reverseIntakeDirection(){

    }

    // method to run intake

    public void runIntake(double intakeSpeed){
        intakeMotor.setPower(intakeSpeed);
        leftIntakeServo.setPower(intakeSpeed);
        rightIntakeServo.setPower(intakeSpeed);
        telemetry.addData("Intake Speed: ", intakeSpeed * 100);
    }

}
