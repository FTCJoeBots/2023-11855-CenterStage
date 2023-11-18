package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "Blue TeleOp",group="TeleOp")

public class FirstTele extends OpMode{

    int Statejoey= 0;

    // DcMotor pullup;

    boolean startjoey =false;
    boolean currentjoey;
    boolean previosPressedjoey = false;

    private double strafe = 0;

    private double MAXSPEED = 0.75;
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

    PullUpArm pullup = new PullUpArm();

    @Override
    public void init() {
        pullup.init(hardwareMap);
        pullup.Pullup.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullup.Pullup.setPower(0);
        //pullup = hardwareMap.get(DcMotor.class,"PullUp");
        //pullup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //pullup.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //pullup


    mecanum.init(hardwareMap);
        joey.init(hardwareMap);
        lift.init(hardwareMap);
        PullUp.init(hardwareMap);

        Bucket.init(hardwareMap);

        bucketArm.init(hardwareMap, BucketArm.BucketStartPosition.OUT, BucketArm.BucketGateStartPosition.CLOSE);

        telemetry.speak("Hello guys. Have a good match.");
        pullup.Pullup.setPower(0);


    }
        //while(opModeIsActive()&&!isStopRequested()){
        @Override
        public void loop() {

            double forward;
            double strafe;
            double rotate;

            strafe = gamepad1.left_trigger - gamepad1.right_trigger;




            forward = gamepad1.left_stick_y * -MAXSPEED;
            strafe = gamepad1.right_trigger*MAXSPEED - gamepad1.left_trigger*MAXSPEED;
            rotate = gamepad1.right_stick_x*MAXSPEED;

            mecanum.driveMecanum(forward, strafe, rotate);


            if(TurtleMode) {
                mecanum.driveMecanum(forward*0.01,strafe*0.01,rotate*0.01);
            }
            if(gamepad1.a&&!Previous1A) {
                TurtleMode=!TurtleMode;
                Previous1A=true;
            }
            if(!gamepad1.a) {
                Previous1A=false;
            }


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
            }


            if (gamepad1.dpad_up) {
                pullup.PullUp();
                pullup.contorller();
            }else{
                pullup.Pullup.setPower(0);
            }

            if(gamepad1.dpad_down){
                pullup.PullDown();
                pullup.contorller();
            }else{
                pullup.Pullup.setPower(0);
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
                bucketArm.BucketOut();
            }


            if (gamepad2.b) {
                bucketArm.BucketIn();
            }

        }

        }




