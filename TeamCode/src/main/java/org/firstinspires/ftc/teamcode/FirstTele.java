package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "Blue TeleOp",group="TeleOp")

public class FirstTele extends OpMode{

    int Statejoey= 0;

    boolean startjoey =false;
    boolean currentjoey;
    boolean previosPressedjoey = false;

    private double strafe = 0;

    private double MAXSPEED = 1;
    double forward;
    double clockwise;
    double right;
    double k;
    double power0;
    double power1;
    double power2;
    double power3;
    double max;

    int XButtonCounter = 0;
    int BButtonCounter= 1;

    boolean previoiusPressedIntake = false;
    boolean currentStateX;
    boolean previousStateX;
    boolean currentStateLB;
    boolean previousStateLB;
    boolean RbButtonCounter;
    boolean currentStateRB;
    boolean previousStateRB;
    boolean YButtonCounter;
    boolean currentStateY;
    boolean previousStateY;
    boolean AButtonCounter;
    boolean currentStateA;
    boolean previousStateA;
    boolean currentStateDpadUp;
    boolean previousStateDpadUp;
    boolean currentStateDpadDown;
    boolean previousStateDpadDown;
    boolean currentStateB;
    boolean previousStateB;
    boolean aPressedInit;
    boolean aPrev = false;
    boolean bPrev = false;
    boolean yprev = false;
    boolean TurtleMode=false;
    boolean Previous1A=false;
    protected int driveStyle = 1;
    private double[] distances;


    // Set Drive Motor Limits for Driver Training
    static final double FORWARD_K = 1;
    static final double RIGHT_K = 1;
    static final double CLOCKWISE_K = 1;

    Bucket Bucket = new Bucket();
    TeleOpMecanum mecanum = new TeleOpMecanum();
    JoeyIntake joey = new JoeyIntake();
    Lift lift = new Lift();
    PullUpArm PullUp = new PullUpArm();
    BucketArm bucketArm = new BucketArm();
    @Override
    public void init() {


    mecanum.init(hardwareMap);
        joey.init(hardwareMap);
        lift.init(hardwareMap);
        PullUp.init(hardwareMap);

        Bucket.init(hardwareMap);

        bucketArm.init(hardwareMap);

        telemetry.speak("Hello Divyang");

    }
        //while(opModeIsActive()&&!isStopRequested()){
        @Override
        public void loop() {

            double forward;
            double strafe;
            double rotate;

            strafe = gamepad1.left_trigger - gamepad1.right_trigger;




            forward = gamepad1.left_stick_y * -1;
            strafe = gamepad1.right_trigger - gamepad1.left_trigger;
            rotate = gamepad1.right_stick_x;

            mecanum.driveMecanum(forward, strafe, rotate);

            currentjoey = gamepad1.x;
            if (currentjoey && currentjoey != startjoey) {
                if (Statejoey == 1) {
                    joey.JimStart();
                } else if (Statejoey == 2) {
                    joey.JimStop();
                    joey.JimReverse();
                } else if (Statejoey == 3) {
                    joey.JimStop();
                }

                Statejoey += 1;
                if (Statejoey > 3) {
                    Statejoey = 1;
                }
                previosPressedjoey = true;
            }
            startjoey = currentjoey;

            if (gamepad1.y) {
                joey.JimReverse();
            }

            if (gamepad2.right_bumper) {
                lift.raiseLiftManual();
                lift.contorller();
            }

            if (gamepad2.left_bumper) {
                lift.lowerLiftManual();
                lift.contorller();
            }

            if (gamepad1.right_bumper) {
                PullUp.ManualPullUp();
                PullUp.contorller();
            }

            if (gamepad1.left_bumper) {
                PullUp.ManualPullDown();
                PullUp.contorller();
            }

            if (gamepad2.dpad_down) {
                lift.RightLift_To_Position(0);
                lift.LeftLift_To_Position(0);
                lift.contorller();
            }

            if (gamepad2.dpad_left) {
                lift.RightLift_To_Position(1);
                lift.LeftLift_To_Position(1);
                lift.contorller();
            }
            if (gamepad2.dpad_right) {
                lift.RightLift_To_Position(2);
                lift.LeftLift_To_Position(2);
                lift.contorller();
            }
            if (gamepad2.dpad_up) {
                lift.RightLift_To_Position(3);
                lift.LeftLift_To_Position(3);
                lift.contorller();
            }

            if(gamepad2.right_trigger>=0.5){
                Bucket.BucketGateOut();
            }else {
                Bucket.BucketGateIn();
            }

            if (gamepad2.a) {
                bucketArm.BucketRight();
            }


            if (gamepad2.b) {
                bucketArm.BucketLeft();
            }

            if (gamepad2.y) {
                bucketArm.BucketStop();
            }

            if(gamepad1.b){
                forward*=0.5;
                strafe*=0.5;
                rotate*=0.5;
            }

            if(gamepad1.y){
                forward*=1;
                strafe*=1;
                rotate*=1;
            }


        }

        }




