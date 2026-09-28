package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
public class driveSystem {

    // Declare Variables for devices
    private DcMotor leftFrontDrive;
    private DcMotor leftRearDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightRearDrive;
    private GoBildaPinpointDriver pinpoint;

    // Set up telemetry and other variables.
    private Telemetry telemetry;
    private double xOffset = 0;
    private double yOffset = 0;

    // Init method, call once on setup in OpMode. sets up all devices
    public void init(HardwareMap hMap, Telemetry telemetry, boolean usePinpoint){
        // Tell the program where to find the devices
        leftFrontDrive = hMap.get(DcMotor.class,"FLDrive");
        leftRearDrive = hMap.get(DcMotor.class,"BLDrive");
        rightFrontDrive = hMap.get(DcMotor.class,"FRDrive");
        rightRearDrive = hMap.get(DcMotor.class,"BRDrive");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftRearDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightRearDrive.setDirection(DcMotor.Direction.FORWARD);

        this.telemetry = telemetry;

        // setup a pinpoint odometry computer.
        if (usePinpoint) {
            pinpoint = hMap.get(GoBildaPinpointDriver.class, "pintpoint");
            configurePinpoint();
        }

        telemetry.addData("Mechanium Drive Initialized","✅");

    }

    private void configurePinpoint(){
        /*
         *  Set the odometry pod positions relative to the point that you want the position to be measured from.
         *
         *  The X pod offset refers to how far sideways from the tracking point the X (forward) odometry pod is.
         *  Left of the center is a positive number, right of center is a negative number.
         *
         *  The Y pod offset refers to how far forwards from the tracking point the Y (strafe) odometry pod is.
         *  Forward of center is a positive number, backwards is a negative number.
         */
        pinpoint.setOffsets(-xOffset, -yOffset, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1

        /*
         * Set the kind of pods used by your robot. If you're using goBILDA odometry pods, select either
         * the goBILDA_SWINGARM_POD, or the goBILDA_4_BAR_POD.
         * If you're using another kind of odometry pod, uncomment setEncoderResolution and input the
         * number of ticks per unit of your odometry pod.  For example:
         *     pinpoint.setEncoderResolution(13.26291192, DistanceUnit.MM);
         */
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

        /*
         * Set the direction that each of the two odometry pods count. The X (forward) pod should
         * increase when you move the robot forward. And the Y (strafe) pod should increase when
         * you move the robot to the left.
         */
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD);

        /*
         * Before running the robot, recalibrate the IMU. This needs to happen when the robot is stationary
         * The IMU will automatically calibrate when first powered on, but recalibrating before running
         * the robot is a good idea to ensure that the calibration is "good".
         * resetPosAndIMU will reset the position to 0,0,0 and also recalibrate the IMU.
         * This is recommended before you run your autonomous, as a bad initial calibration can cause
         * an incorrect starting value for x, y, and heading.
         */
        pinpoint.resetPosAndIMU();
        pinpoint.setPosition(new Pose2D(DistanceUnit.MM, 0,0, AngleUnit.DEGREES, 0));
    }

    public void driveRobot(double move, double strafe, double turn) {
        // Calculate wheel powers.
        double frontLeftPower    =  move - strafe - turn;
        double frontRightPower   =  move + strafe + turn;
        double backLeftPower     =  move + strafe - turn;
        double backRightPower    =  move - strafe + turn;

        // Normalize wheel powers to be less than 1.0
        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        // Send powers to the wheels.
        leftFrontDrive.setPower(frontLeftPower);
        rightFrontDrive.setPower(frontRightPower);
        leftRearDrive.setPower(backLeftPower);
        rightRearDrive.setPower(backRightPower);
    }

    public void driveFieldRelative (double move, double strafe, double turn) {
        double theta = Math.atan2(move,strafe);
        double r = Math.hypot(strafe,move);

        theta = AngleUnit.normalizeRadians(theta - pinpoint.getYawScalar());

        double newMove = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        driveRobot(newMove,newStrafe,turn);

    }

    public Pose2D getPosition(){
        return pinpoint.getPosition();
    }

    public double getHeading(){
        return pinpoint.getHeading(AngleUnit.DEGREES);
    }

    public void updatePinPointPosition(double x, double y, double heading){
        pinpoint.setPosition(new Pose2D(DistanceUnit.MM,x,y,AngleUnit.DEGREES,heading));
    }

    public void updatePinpoint(){
        pinpoint.update();
    }
}
