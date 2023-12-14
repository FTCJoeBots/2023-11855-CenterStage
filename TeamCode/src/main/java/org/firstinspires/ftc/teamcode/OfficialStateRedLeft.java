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

@Autonomous(name="Offical State Red Left", group="0")

public class OfficialStateRedLeft extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam2;
    ObjectDetectorRed ODR = new ObjectDetectorRed(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(-73.5, 42, Math.toRadians(-90));
        Pose2d SpikeRight = new Pose2d(-34, 39, Math.toRadians(-90));
        Pose2d RightStack = new Pose2d(0, 0, Math.toRadians(90));
        Pose2d SpikeCenter = new Pose2d(-21.5, 57.5, Math.toRadians(-90));
        Pose2d SpikeLeft = new Pose2d(-30, 63, Math.toRadians(-90));


        Pose2d UsedPose;

        JoeyIntake intake = new JoeyIntake();
        Bucket Bucket = new Bucket();
        BucketArm BucketArm = new BucketArm();

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive LeftSpike = new MecanumDrive(hardwareMap, SpikeLeft);
        MecanumDrive CenterSpike = new MecanumDrive(hardwareMap,SpikeCenter);
        MecanumDrive RightSpike = new MecanumDrive(hardwareMap, SpikeRight);
        MecanumDrive StackRight = new MecanumDrive(hardwareMap, RightStack);

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
                            .strafeToLinearHeading(new Vector2d(-19.25,57.5),Math.toRadians(-75))
                            .build(),
                  intake.inverse(),
                    CenterSpike.actionBuilder(CenterSpike.pose)
                            .waitSeconds(1)
                            .build(),
                            intake.stop(),
                            CenterSpike.actionBuilder(CenterSpike.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-6,60),Math.toRadians(-75))
                                    .strafeToLinearHeading(new Vector2d(-7,57),Math.toRadians(90))
                                    .strafeToLinearHeading(new Vector2d(-7,65),Math.toRadians(110))
                                    .strafeToLinearHeading(new Vector2d(-15,61),Math.toRadians(120))
                                    .strafeToLinearHeading(new Vector2d(-20.5,61),Math.toRadians(135))
                            .build(),
                    intake.Knock(),
                    StackRight.actionBuilder(StackRight.pose)
                            .waitSeconds(2)
                            .strafeToLinearHeading(new Vector2d(-5,-2),Math.toRadians(90))
                            .build(),
                    intake.Knock(),
                    StackRight.actionBuilder(StackRight.pose)
                            .waitSeconds(2)
                            .strafeToLinearHeading(new Vector2d(-100,17),Math.toRadians(90))
                            .strafeToLinearHeading(new Vector2d(-115,50),Math.toRadians(100))
                            .strafeToLinearHeading(new Vector2d(-129.25,55),Math.toRadians(100))
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
                            .waitSeconds(1)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(StackRight.pose)
                            .waitSeconds(1)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(StackRight.pose)
                            .waitSeconds(2)
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
                            .strafeToLinearHeading(new Vector2d(-30,63),Math.toRadians(-75))
                            .build(),
                    intake.inverse(),
                    LeftSpike.actionBuilder(LeftSpike.pose)
                            .waitSeconds(1)
                            .build(),
                            intake.stop(),
                            LeftSpike.actionBuilder(LeftSpike.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-15,63),Math.toRadians(-75))
                                    .strafeToLinearHeading(new Vector2d(-0,45),Math.toRadians(90))
                                    .strafeToLinearHeading(new Vector2d(-3,60),Math.toRadians(110))
                                    .strafeToLinearHeading(new Vector2d(-22.5,52.5),Math.toRadians(110))
                                    .build(),
                    intake.Knock(),
                    StackRight.actionBuilder(StackRight.pose)
                            .waitSeconds(2)
                            .strafeToLinearHeading(new Vector2d(-5,-2),Math.toRadians(90))
                            .build(),
                    intake.Knock(),
                    StackRight.actionBuilder(StackRight.pose)
                            .waitSeconds(2)
                            .strafeToLinearHeading(new Vector2d(-100,17),Math.toRadians(90))
                            .strafeToLinearHeading(new Vector2d(-115,35),Math.toRadians(100))
                            .strafeToLinearHeading(new Vector2d(-127,47.5),Math.toRadians(95))
                            .build(),
                    Lift.AutoPos1(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1)
                            .build(),
                    BucketArm.BucketDrop(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1.5)
                            .build(),
                    Bucket.GateDrop(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(StackRight.pose)
                            .waitSeconds(1)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(StackRight.pose)
                            .waitSeconds(1)
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
                            .strafeToLinearHeading(new Vector2d(-34,39),Math.toRadians(-75))
                            .build(),
                    intake.inverse(),
                    RightSpike.actionBuilder(RightSpike.pose)
                            .waitSeconds(0.5)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-5,50),Math.toRadians(-90))
                            .strafeToLinearHeading(new Vector2d(0,45),Math.toRadians(75))
                            .strafeToLinearHeading(new Vector2d(-25,50),Math.toRadians(110))
                            .strafeToLinearHeading(new Vector2d(-28.5,48),Math.toRadians(110))
                            .build(),
                    intake.Knock(),
                    StackRight.actionBuilder(StackRight.pose)
                            .waitSeconds(2)
                            .strafeToLinearHeading(new Vector2d(-5,-2),Math.toRadians(90))
                            .build(),
                    intake.Knock(),
                    StackRight.actionBuilder(StackRight.pose)
                            .waitSeconds(2)
                            .strafeToLinearHeading(new Vector2d(-100,17),Math.toRadians(90))
                            .strafeToLinearHeading(new Vector2d(-115,65),Math.toRadians(100))
                            .strafeToLinearHeading(new Vector2d(-126,59.75),Math.toRadians(105))
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
                            .waitSeconds(1)
                            .build(),
                    Bucket.CloseGate(),
                    drive.actionBuilder(StackRight.pose)
                            .waitSeconds(1)
                            .build(),
                    BucketArm.BucketBack(),
                    drive.actionBuilder(StackRight.pose)
                            .waitSeconds(2)
                            .build(),
                    lift.LefPos0(),
                    drive.actionBuilder(drive.pose)
                            .waitSeconds(1)
                            .build()

            ));


        } if(isStopRequested()) return;
    }
}






