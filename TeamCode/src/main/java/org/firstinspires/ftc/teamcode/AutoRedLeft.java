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

@Autonomous(name="Lakeview red left", group="Pushbot")

public class AutoRedLeft extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam2;
    ObjectDetectorRed ODR = new ObjectDetectorRed(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(-73.5, 42, Math.toRadians(-90));
        Pose2d Pose1 = new Pose2d(-38, 37, Math.toRadians(-90));
        Pose2d Pose2 = new Pose2d(-35, 12, Math.toRadians(-90));
        Pose2d UsedPose;

        JoeyIntake intake = new JoeyIntake();
        Bucket Bucket = new Bucket();
        BucketArm BucketArm = new BucketArm();

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive drive1 = new MecanumDrive(hardwareMap, Pose1);
        MecanumDrive drive2 = new MecanumDrive(hardwareMap, Pose2);

        Lift lift = new Lift();

        BucketArm.init(hardwareMap, org.firstinspires.ftc.teamcode.BucketArm.BucketStartPosition.OUT, org.firstinspires.ftc.teamcode.BucketArm.BucketGateStartPosition.CLOSE);
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
                            .strafeToLinearHeading(new Vector2d(-27,57.5),Math.toRadians(-75))
                            .build(),
                  intake.inverse(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(1)
                            .build(),
                            intake.stop(),
                            drive.actionBuilder(drive1.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-6,50),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-6,-40),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-32,-42),Math.toRadians(105))
                            .strafeToLinearHeading(new Vector2d(-46.5,-23.5),Math.toRadians(95))
                            .build(),
                    Lift.AutoPos1(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketDrop(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(0.75)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.25)
                            .build(),
                    lift.LefPos0(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1)
                            .build()

            ));

        } else if (ODR.getIntLocation() == 1) { // Left
            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-32.5,37),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-30,59),Math.toRadians(-75))
                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(1)
                            .build(),
                            intake.stop(),
                            drive.actionBuilder(drive1.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-8,40),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-8,-60),Math.toRadians(-75))
                            .strafeToLinearHeading(new Vector2d(-20,-68),Math.toRadians(97))
                                    .strafeToLinearHeading(new Vector2d(-22.5,-89),Math.toRadians(105))
                                    .strafeToLinearHeading(new Vector2d(-19,-89),Math.toRadians(105))
                            .build(),
                    Lift.AutoPos1(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketDrop(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(3)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(2)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.25)
                            .build(),
                    lift.LefPos0(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1)
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
                            .strafeToLinearHeading(new Vector2d(-38,39),Math.toRadians(-75))
                            .build(),
                    intake.inverse(),
                    drive.actionBuilder(drive1.pose)
                            .waitSeconds(0.5)
                            .build(),
                    intake.stop(),
                    drive.actionBuilder(drive1.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-40,45),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(-4,60),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(-4,-20),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(-20,-30),Math.toRadians(97))
                            .strafeToLinearHeading(new Vector2d(-21,-10),Math.toRadians(90))
                            .strafeToLinearHeading(new Vector2d(-37,-5.75),Math.toRadians(113.25))
                            .build(),
                    Lift.AutoPos1(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketDrop(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(3)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(2)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(2)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(drive2.pose)
                            .waitSeconds(1.25)
                            .build(),
                    lift.LefPos0(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1)
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






