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

        joey.init(hardwareMap);
        lift.init(hardwareMap);

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

            telemetry.addData("Left Lift",lift.LeftLift.getCurrentPosition());
            telemetry.addData("Right Lift",lift.RightLift.getCurrentPosition());
telemetry.update();



        }

    }

}
