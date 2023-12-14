
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
@Disabled
@Autonomous(name="LakeviewBlueRight", group="Pushbot")

public class LakeviewAutoBlueRight extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetectorBlueRight OD = new ObjectDetectorBlueRight(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {

        //-71.75
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(-90));
        Pose2d Pose1 = new Pose2d(43, 7, Math.toRadians(95));
        Pose2d Pose2 = new Pose2d(41, -38, Math.toRadians(-90));
        Pose2d Pose3 = new Pose2d(33,6,Math.toRadians(-75));
        Pose2d Pose4 = new Pose2d(62,-14,Math.toRadians(-65));
        Pose2d Pose5 = new Pose2d(21,15,Math.toRadians(0));
        Pose2d CenterSpike = new Pose2d(46,13.5,Math.toRadians(90));
        //32 , 3

        Pose2d UsedPose;

        JoeyIntake intake = new JoeyIntake();
        Bucket Bucket = new Bucket();
        BucketArm BucketArm = new BucketArm();

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive drive1 = new MecanumDrive(hardwareMap, Pose1);
        MecanumDrive drive2 = new MecanumDrive(hardwareMap, Pose2);
        MecanumDrive drive3 = new MecanumDrive(hardwareMap,Pose3);
        MecanumDrive drive4 = new MecanumDrive(hardwareMap,Pose4);
        MecanumDrive drive5 = new MecanumDrive(hardwareMap, Pose5);
        MecanumDrive SpikeCenter = new MecanumDrive(hardwareMap,CenterSpike);


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
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(34,6),Math.toRadians(-75))
                            .build(),
                   intake.inverse(),
                   drive.actionBuilder(drive.pose)
                           .waitSeconds(0.1)
                                   .build(),
                   intake.stop(),
                   drive.actionBuilder(drive3.pose)
                           .waitSeconds(1)
                           .strafeToLinearHeading(new Vector2d(62,5),Math.toRadians(-65))
                           .strafeToLinearHeading(new Vector2d(62,-13),Math.toRadians(-65))
                           .build(),
                   intake.Knock(),
                   drive.actionBuilder(drive.pose)
                           .waitSeconds(2)
                           .build(),
                    intake.start(),
                   drive.actionBuilder(drive4.pose)
                           .strafeToLinearHeading(new Vector2d(62,100),Math.toRadians(-65))
                           .strafeToLinearHeading(new Vector2d(41.5,104.5),Math.toRadians(-78))
                           .build(),
                    Lift.AutoPos1(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(0.75)
                            .build(),
                    BucketArm.BucketDrop(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(2.5)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(3)
                            .build(),
                    lift.LefPos0(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.5)
                            .strafeToLinearHeading(new Vector2d(50,-38.5),Math.toRadians(-110))
                            .build()
                   /*drive.actionBuilder(drive1.pose)
                            .waitSeconds(2)
                            .setTangent(0)
                            .splineToLinearHeading(new Pose2d(40.0,100.0, Math.toRadians(-75)),0)
                            .build()*/
            ));

        } else if (OD.getIntLocation() == 1) { // Center
            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(45.5,13.5),Math.toRadians(90))
                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(SpikeCenter.pose)
                            .waitSeconds(1)
                                    .build(),
                    intake.stop(),
                    drive.actionBuilder(SpikeCenter.pose)
                            .waitSeconds(0.5)
                            .strafeToLinearHeading(new Vector2d(50,13.5),Math.toRadians(90))
                            .strafeToLinearHeading(new Vector2d(50,34),Math.toRadians(90))
                            .strafeToLinearHeading(new Vector2d(-40,36),Math.toRadians(90))
                            .strafeToLinearHeading(new Vector2d(-40,18),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(-71,25),Math.toRadians(-80))
                            .strafeToLinearHeading(new Vector2d(-64.5,36.5),Math.toRadians(-98))
                            .build(),
                    Lift.AutoPos1(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(0.75)
                            .build(),
                    BucketArm.BucketDrop(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.5)
                            .build(),
                    lift.LefPos0(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.5)
                            .strafeToLinearHeading(new Vector2d(50,-38.5),Math.toRadians(-110))
                            .build()
            ));


        } else { // Left
            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(35,3),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(43,6),Math.toRadians(105))
                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(0.5)
                                    .build(),
                    intake.stop(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(1)
                            .strafeToLinearHeading(new Vector2d(38,-36),Math.toRadians(-90))
                            .build(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.5)
                            .strafeToLinearHeading(new Vector2d(41,60),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(2.75,79.5),Math.toRadians(-110))
                            .build(),
                    intake.stop(),
                    Lift.AutoPos1(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(0.75)
                            .build(),
                    BucketArm.BucketDrop(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(2.5)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(3)
                            .build(),
                    lift.LefPos0(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.5)
                            .strafeToLinearHeading(new Vector2d(50,-38.5),Math.toRadians(-110))
                            .build()
            ));

        } if(isStopRequested()) return;
    }
}
