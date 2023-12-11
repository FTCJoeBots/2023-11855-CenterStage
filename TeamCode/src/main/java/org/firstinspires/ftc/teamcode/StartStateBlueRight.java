
package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

/**
 * This is sample code used to explain how to write an autonomous code
 *
 */

@Autonomous(name="StateBlueRight", group="Pushbot")

public class StartStateBlueRight extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetectorBlueRight OD = new ObjectDetectorBlueRight(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();

    public class Drive{

    }
    @Override
    public void runOpMode() {


        //-75
        Pose2d startPose = new Pose2d(-75, -48, Math.toRadians(-90));
        Pose2d LeftSpike = new Pose2d(-34, -35, Math.toRadians(90));
        Pose2d StackLEft = new Pose2d(0, 0, Math.toRadians(-90));
        Pose2d Center= new Pose2d(-33,-40,Math.toRadians(-90));
        Pose2d Pose4 = new Pose2d(62,-14,Math.toRadians(-90));
        Pose2d Pose5 = new Pose2d(21,15,Math.toRadians(0));
        Pose2d CenterSpike = new Pose2d(-37,-60,Math.toRadians(90));
        Pose2d RightSpike = new Pose2d(-34,-40,Math.toRadians(-90));
        //32 , 3

        Pose2d UsedPose;

        JoeyIntake intake = new JoeyIntake();
        Bucket Bucket = new Bucket();
        BucketArm BucketArm = new BucketArm();

        MecanumDrive StartPos = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive SpikeLeft = new MecanumDrive(hardwareMap, LeftSpike);
        MecanumDrive LeftStack = new MecanumDrive(hardwareMap, StackLEft);
        MecanumDrive CenterPos = new MecanumDrive(hardwareMap,Center);
        MecanumDrive drive4 = new MecanumDrive(hardwareMap,Pose4);
        MecanumDrive drive5 = new MecanumDrive(hardwareMap, Pose5);
        MecanumDrive SpikeCenter = new MecanumDrive(hardwareMap,CenterSpike);
        MecanumDrive SpikeRight = new MecanumDrive(hardwareMap,RightSpike);



        Lift lift = new Lift();

        Bucket.init(hardwareMap);
        lift.init(hardwareMap);
        intake.init(hardwareMap);
        BucketArm.init(hardwareMap, org.firstinspires.ftc.teamcode.BucketArm.BucketStartPosition.OUT, org.firstinspires.ftc.teamcode.BucketArm.BucketGateStartPosition.CLOSE);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(OD);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);}
            @Override
            public void onError(int errorCode) {}});

        while(!isStarted()) {
            if (OD.getIntLocation() == 2) {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Right");
                telemetry.update();
                sleep(30);
            } else if (OD.getIntLocation() == 1) {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Center");
                telemetry.update();
                sleep(30);

            } else {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Left");
                telemetry.update();
                sleep(30);
            }
        }
        waitForStart();
        telemetry.addLine("Running Auto");
        telemetry.update();

        //

        //sleep(5000);

        if (OD.getIntLocation() == 2){ // Right
            Actions.runBlocking( new SequentialAction (

                    StartPos.actionBuilder(StartPos.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-30,-40),Math.toRadians(-90))
                            .build(),
                    intake.inverse(),
                    SpikeRight.actionBuilder(SpikeRight.pose)
                            .waitSeconds(0.5)
                                    .build(),
                    intake.stop(),
                    SpikeRight.actionBuilder(SpikeRight.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-12,-37.5),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(-0,-36.5),Math.toRadians(-55))
                            .strafeToLinearHeading(new Vector2d(-4,-65),Math.toRadians(-55))
                            .build(),
                    intake.start(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(2.5)
                            .build(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .strafeToLinearHeading(new Vector2d(0,6),Math.toRadians(-90))
                                    .build(),
                    intake.Knock(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-5,94),Math.toRadians(-95))
                            .strafeToLinearHeading(new Vector2d(-27,111),Math.toRadians(-110))
                            .strafeToLinearHeading(new Vector2d(-23,117.75),Math.toRadians(-115))
                            .build(),
                    intake.stop(),
                    Lift.AutoPos1(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(0.25)
                            .build(),
                    BucketArm.BucketDrop(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(2.5)
                            .build(),
                    Bucket.GateDrop(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.CloseGate(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketBack(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(3)
                            .build(),
                    lift.LefPos0(),
                    drive4.actionBuilder(drive4.pose)
                            .waitSeconds(10)
                            .build()


            ));

        } else if (OD.getIntLocation() == 1) { // Center
            Actions.runBlocking( new SequentialAction (
                    StartPos.actionBuilder(StartPos.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-30,-65),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(-37,-60),Math.toRadians(90))
                            .build(),
                    intake.inverse(),
                    SpikeCenter.actionBuilder(SpikeCenter.pose)
                            .waitSeconds(1)
                            .build(),
                    intake.stop(),
                    SpikeCenter.actionBuilder(SpikeCenter.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-40,-65),Math.toRadians(120))
                            .waitSeconds(0.5)
                            .strafeToLinearHeading(new Vector2d(-41,-85),Math.toRadians(120))
                            .waitSeconds(0.5)
                            .strafeToLinearHeading(new Vector2d(-40,-87),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(-45,-87),Math.toRadians(-60))
                            .strafeToLinearHeading(new Vector2d(-50,-90),Math.toRadians(-60))
                            //.strafeToLinearHeading(new Vector2d(-38,-67.5),Math.toRadians(-75))
                            //.strafeToLinearHeading(new Vector2d(-33,-40),Math.toRadians(-45))
                            .build(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(1)
                            .build(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-25,-110),Math.toRadians(-95))
                            .waitSeconds(0.5)
                            .strafeToLinearHeading(new Vector2d(8,-127.5),Math.toRadians(-120))
                            .strafeToLinearHeading(new Vector2d(-8,-130),Math.toRadians(-110))
                            .build(),
                    Lift.AutoPos1(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(0.75)
                            .build(),
                    BucketArm.BucketDrop(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(2.5)
                            .build(),
                    Bucket.GateDrop(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.CloseGate(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketBack(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(3)
                            .build(),
                    lift.LefPos0(),
                    drive4.actionBuilder(drive4.pose)
                            .waitSeconds(10)
                            .build()

            ));


        } else { // Left
            Actions.runBlocking( new SequentialAction (
                    StartPos.actionBuilder(StartPos.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-34,-40),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(-34,-35),Math.toRadians(90))                                                                                    .build(),
                    intake.inverse(),
                    SpikeLeft.actionBuilder(SpikeLeft.pose)
                            .waitSeconds(.5)
                                    .build(),
                    intake.stop(),
                    SpikeLeft.actionBuilder(SpikeLeft.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-50,-35),Math.toRadians(120))
                           .strafeToLinearHeading(new Vector2d(-52,-30),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-28,-40),Math.toRadians(-25))
                            .strafeToLinearHeading(new Vector2d(-28,-48),Math.toRadians(-30))
                         //   .strafeToLinearHeading(new Vector2d(-5,-20),Math.toRadians(-75))
                           // .strafeToLinearHeading(new Vector2d(-7.5,-22.5),Math.toRadians(-75))
                            .build(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(1)
                            .build(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-25,-110),Math.toRadians(-95))
                            .waitSeconds(0.5)
                            .strafeToLinearHeading(new Vector2d(8,-127.5),Math.toRadians(-120))
                            .strafeToLinearHeading(new Vector2d(0,-133),Math.toRadians(-120))
                            .build(),
                    Lift.AutoPos1(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(0.75)
                            .build(),
                    BucketArm.BucketDrop(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(2.5)
                            .build(),
                    Bucket.GateDrop(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.CloseGate(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketBack(),
                    LeftStack.actionBuilder(LeftStack.pose)
                            .waitSeconds(3)
                            .build(),
                    lift.LefPos0(),
                    drive4.actionBuilder(drive4.pose)
                            .waitSeconds(10)
                            .build()

            ));


        } if(isStopRequested()) return;
    }
}
