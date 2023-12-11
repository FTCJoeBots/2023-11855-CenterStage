
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

/**
 * This is sample code used to explain how to write an autonomous code
 *
 */

@Autonomous(name="StartAutoBlueleft", group="Pushbot")

public class StartStateAutoBlueLeft extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetectorBlueLeft OD = new ObjectDetectorBlueLeft(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {

        //-71.75
        Pose2d startPose = new Pose2d(-71.75, 23, Math.toRadians(-90));
        Pose2d Pose1 = new Pose2d(-25, 25, Math.toRadians(-90));
        Pose2d Pose2 = new Pose2d(-35, 14.5, Math.toRadians(-90));
        Pose2d pose3 = new Pose2d(-20.75,52,Math.toRadians(-90));
        Pose2d CenterBoard = new Pose2d(-25,52,Math.toRadians(-90));
        Pose2d LeftBoard = new Pose2d(-60,51.5,Math.toRadians(-90));
        Pose2d UsedPose;

        JoeyIntake intake = new JoeyIntake();
        Bucket Bucket = new Bucket();
        BucketArm BucketArm = new BucketArm();

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive drive1 = new MecanumDrive(hardwareMap, Pose1);
        MecanumDrive drive2 = new MecanumDrive(hardwareMap, Pose2);
        MecanumDrive drive3 = new MecanumDrive(hardwareMap,pose3);
        MecanumDrive BoardCenter = new MecanumDrive(hardwareMap,CenterBoard);
        MecanumDrive boardLeft = new MecanumDrive(hardwareMap,LeftBoard);
        Lift lift = new Lift();

        Bucket.init(hardwareMap);
        BucketArm.init(hardwareMap, org.firstinspires.ftc.teamcode.BucketArm.BucketStartPosition.OUT, org.firstinspires.ftc.teamcode.BucketArm.BucketGateStartPosition.CLOSE);
        lift.init(hardwareMap);
        intake.init(hardwareMap);

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
                telemetry.addLine("Center");
                telemetry.update();
                sleep(30);
            } else if (OD.getIntLocation() == 1) {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Left");
                telemetry.update();
                sleep(30);

            } else {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Right");
                telemetry.update();
                sleep(30);
            }
        }
        waitForStart();
        telemetry.addLine("Running Auto");
        telemetry.update();

        //

        //sleep(5000);

        if (OD.getIntLocation() == 2){ // center

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-24,27.5),Math.toRadians(-75))
                            .build(),
                   intake.inverse(),
                   drive.actionBuilder(drive1.pose)
                           .waitSeconds(1)
                           .build(),
                    intake.stop(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(0.5)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-45.5,55),Math.toRadians(-97.5))
                                    .build(),
                    Lift.AutoPos1(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1)
                            .build(),
                    BucketArm.BucketDrop(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1.5)
                            .build(),
                    lift.LefPos0(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1)
                            .strafeToLinearHeading(new Vector2d(-110,30),Math.toRadians(-97))
                            .strafeToLinearHeading(new Vector2d(-110,49.5),Math.toRadians(-97))
                            .build()


                   /* drive.actionBuilder(drive1.pose)
                            .waitSeconds(2)
                            .setTangent(0)
                            .splineToLinearHeading(new Pose2d(40.0,100.0, Math.toRadians(-75)),0)
                            .build()*/
            ));

        } else if (OD.getIntLocation() == 1) { // Left
            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-30,45),Math.toRadians(-75))
                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(0.5)
                                    .build(),
                    intake.stop(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-41.5,45.5),Math.toRadians(-100))
                            .build(),
                    Lift.AutoPos1(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1)
                            .build(),
                    BucketArm.BucketDrop(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(0.5)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1.5)
                            .build(),
                    lift.LefPos0(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(2)
                            .strafeToLinearHeading(new Vector2d(-95,30),Math.toRadians(-97))
                            .strafeToLinearHeading(new Vector2d(-95,49.5),Math.toRadians(-97))
                            .build()
                   /* drive.actionBuilder(drive1.pose)d
                            .waitSeconds(2)
                            .setTangent(0)
                            .splineToLinearHeading(new Pose2d(40.0,100.0, Math.toRadians(-75)),0)
                            .build()*/
            ));


        } else { // Right
            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-37.5,18),Math.toRadians(-75))
                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(0.5)
                            .build(),
                    intake.stop(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-21,54.75),Math.toRadians(-97))
                            .build(),
                   Lift.AutoPos1(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1)
                            .build(),
                   BucketArm.BucketDrop(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive3.pose)
                            .waitSeconds(1)
                            .build(),
                    Bucket.CloseGate(),
                   drive.actionBuilder(drive3.pose)
                           .waitSeconds(1)
                           .build(),
                    BucketArm.BucketBack(),
                   drive.actionBuilder(drive3.pose)
                           .waitSeconds(1.5)
                           .build(),
                   lift.LefPos0(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1)
                            .strafeToLinearHeading(new Vector2d(-110,20),Math.toRadians(-97))
                            .strafeToLinearHeading(new Vector2d(-110,50),Math.toRadians(-97))
                            .build()

                   /* drive.actionBuilder(drive1.pose)
                            .waitSeconds(2)
                            .setTangent(0)
                            .splineToLinearHeading(new Pose2d(40.0,100.0, Math.toRadians(-75)),0)
                            .build()*/
            ));

        } if(isStopRequested()) return;
    }
}
