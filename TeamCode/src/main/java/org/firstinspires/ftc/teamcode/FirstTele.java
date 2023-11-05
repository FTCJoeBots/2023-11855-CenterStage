package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Blue TeleOp",group="TeleOp")

public class FirstTele extends LinearOpMode{

    int Statejoey= 0;

    boolean startjoey =false;
    boolean currentjoey;
    boolean previosPressedjoey = false;

    private double strafe = 0;

    private double MAXSPEED = 1;


    public void runOpMode() throws InterruptedException{
        MecanumDrive drive =new MecanumDrive(hardwareMap,new Pose2d(0,0,0));
        JoeyIntake joey = new JoeyIntake();
        Lift lift = new Lift();
        PullUpArm PullUp = new PullUpArm();
        Bucket Bucket = new Bucket();

        joey.init(hardwareMap);
        lift.init(hardwareMap);
        PullUp.init(hardwareMap);
        Bucket.init(hardwareMap);

        telemetry.speak("Hello Divyang");

        waitForStart();

        while(opModeIsActive()&&!isStopRequested()){
            strafe = gamepad1.left_trigger -gamepad1.right_trigger;


            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y*MAXSPEED,
                            strafe*MAXSPEED
                    ),
                    -gamepad1.right_stick_x

            ));

            drive.updatePoseEstimate();

            currentjoey = gamepad1.x;
            if(currentjoey && currentjoey != startjoey){
                if(Statejoey == 1){
                    joey.JimStart();
                }
                else if (Statejoey == 2){
                    joey.JimStop();
                    joey.JimReverse();
                }
                else if (Statejoey ==3){
                    joey.JimStop();
                }

                Statejoey +=1;
                if (Statejoey >3){
                    Statejoey =1;
                }
                previosPressedjoey =true;
            }
            startjoey = currentjoey;

            if(gamepad1.y){
                joey.JimReverse();
            }

            if(gamepad2.dpad_up){
                lift.raiseLiftManual();
                lift.contorller();
            }
            
            if(gamepad2.dpad_down){
                lift.lowerLiftManual();
                lift.contorller();
            }

            if(gamepad2.right_bumper){
                PullUp.ManualPullUp();
                PullUp.contorller();
            }

            if(gamepad2.left_bumper){
                PullUp.ManualPullDown();
                PullUp.contorller();
            }

            if(gamepad2.a){
                lift.RightLift_To_Position(0);
                lift.LeftLift_To_Position(0);
                lift.contorller();
            }

            if(gamepad2.b){
                lift.RightLift_To_Position(1);
                lift.LeftLift_To_Position(1);
                lift.contorller();
            }
            if(gamepad2.x){
                lift.RightLift_To_Position(2);
                lift.LeftLift_To_Position(2);
                lift.contorller();
            }
            if(gamepad2.y){
                lift.RightLift_To_Position(3);
                lift.LeftLift_To_Position(3);
                lift.contorller();
            }

            if(gamepad2.right_trigger>=0.5){
                Bucket.BucketGateOut();
            }else{
                Bucket.BucketGateIn();
            }


            telemetry.addData("Left Lift",lift.LeftLift.getCurrentPosition());
            telemetry.addData("Right Lift",lift.RightLift.getCurrentPosition());

telemetry.update();



        }

    }

}
