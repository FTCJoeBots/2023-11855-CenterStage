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

@Autonomous(name="Vision Sample red", group="Pushbot")

public class AutoVisionSampleRed extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam2;
    ObjectDetectorRed ODR = new ObjectDetectorRed(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(-73.5, 42, Math.toRadians(-90));
        Pose2d Pose1 = new Pose2d(-30, 65, Math.toRadians(-90));
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

        telemetry.addLine("Press > to Start");
        telemetry.update();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam2 = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam2.setPipeline(ODR);
        webcam2.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                /*
                 * Tell the camera to start streaming images to us! Note that you must make sure
                 * the resolution you specify is supported by the camera. If it is not, an exception
                 * will be thrown.
                 *
                 * Also, we specify the rotation that the camera is used in. This is so that the image
                 * from the camera sensor can be rotated such that it is always displayed with the image upright.
                 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                 * away from the user.
                 */
                webcam2.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
        //telemetry.addData("location:",OD.getIntLocation());
//Put this in a loop to run before we press start (?)
        //  Or do this right after we press start
        //
        while(!isStarted()) {
            if (ODR.getIntLocation() == 2) {
                telemetry.addData("Location", ODR.getIntLocation());
                telemetry.addLine("Center");
                telemetry.update();
                sleep(30);
            } else if (ODR.getIntLocation() == 1) {
                telemetry.addLine("Left");
                telemetry.update();
                sleep(30);

            } else {
                telemetry.addData("Location", ODR.getIntLocation());
                telemetry.addLine("Right");
                telemetry.update();
                sleep(30);
            }
        }
        waitForStart();


        telemetry.addLine("All done.  Press stop.");
        telemetry.update();
        if (ODR.getIntLocation() == 2){ // center

            Actions.runBlocking( new SequentialAction(
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-29.25,47.5),Math.toRadians(-75))
                            .build(),
                  intake.inverse(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(.5)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-6,65),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-6,-275),Math.toRadians(-75))
                            .build()

            ));

        } else if (ODR.getIntLocation() == 1) { // Left
            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-30,65),Math.toRadians(-75))                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(1)
                            .build(),

                    intake.stop(),

                    drive.actionBuilder(drive1.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-6,65),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-6,-275),Math.toRadians(-75))
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
                            .strafeToLinearHeading(new Vector2d(-30,37),Math.toRadians(-75))
                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-30,30),Math.toRadians(-85))
                            .strafeToLinearHeading(new Vector2d(-6,30),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-6,-85),Math.toRadians(-90))
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






