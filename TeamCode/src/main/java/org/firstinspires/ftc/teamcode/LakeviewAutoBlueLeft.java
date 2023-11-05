
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

@Autonomous(name="LakeviewBlueRight", group="Pushbot")

public class LakeviewAutoBlueLeft extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetectorBlue OD = new ObjectDetectorBlue(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {

        //-71.75
        Pose2d startPose = new Pose2d(-71.75, 15, Math.toRadians(-90));
        Pose2d Pose1 = new Pose2d(-27.5, 17.5, Math.toRadians(-90));
        Pose2d Pose2 = new Pose2d(-35, 12, Math.toRadians(-90));
        Pose2d UsedPose;

        JoeyIntake intake = new JoeyIntake();
        Bucket Bucket = new Bucket();

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive drive1 = new MecanumDrive(hardwareMap, Pose1);
        MecanumDrive drive2 = new MecanumDrive(hardwareMap, Pose2);

        Lift lift = new Lift();

        Bucket.init(hardwareMap);
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
                            .strafeToLinearHeading(new Vector2d(-27,25),Math.toRadians(-75))
                            .build(),
                   intake.inverse(),
                   drive.actionBuilder(drive1.pose)
                           .setTangent(0)
                           .strafeToLinearHeading(new Vector2d(-71.75,50),Math.toRadians(-75))
                           .strafeToLinearHeading(new Vector2d(-71.75,71.75),Math.toRadians(-75))
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
                            .strafeToLinearHeading(new Vector2d(-30,38.5),Math.toRadians(-75))
                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-71.75,50),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-71.75,71.75),Math.toRadians(-75))
                            .build()
                   /* drive.actionBuilder(drive1.pose)
                            .waitSeconds(2)
                            .setTangent(0)
                            .splineToLinearHeading(new Pose2d(40.0,100.0, Math.toRadians(-75)),0)
                            .build()*/
            ));


        } else { // Right
            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-36,11.5),Math.toRadians(-75))
                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-71.75,30),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-71.75,71.75),Math.toRadians(-75))
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
