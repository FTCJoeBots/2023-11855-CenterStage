package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ObjectDetectorRed extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();

    public enum Location {
        LEFT,
        RIGHT,
        NOT_FOUND
    }
    private Location location;
    private int IntLocation=-1;

    //draws rectangles(make sure x max is 320 and y is 240(in ObjectDetectAuto webcam.startstreaming))
    //Rectangles for detecting if object of a certain color is in it and what percentage it is at
    static final Rect LEFT_ROI = new Rect(
            new Point(0, 70),
            new Point(90, 245)
            //new Point(0, 120),
            //new Point(130, 300)

    );
    static final Rect RIGHT_ROI = new Rect(
            new Point(350, 70),
            new Point(430, 220));
    // new Point(360, 120),
    //new Point(490, 300));



    //determines what percentage has to fill box
    static double PERCENT_COLOR_THRESHOLD = 0.6;

    public ObjectDetectorRed (Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {
        //color stuff
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        //Scalar lowHSV = new Scalar(23, 50, 70);
        //Scalar highHSV = new Scalar(32, 255, 255);

        Scalar lowHSV = new Scalar(25, 0, 0);
        Scalar highHSV = new Scalar(200, 255, 255);




//160,250
        //V was 150

        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat left = mat.submat(LEFT_ROI);
        Mat right = mat.submat(RIGHT_ROI);


        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;


        left.release();
        right.release();


        //telemtry for values and percents
        //telemetry.addData("Left raw value", (int) Core.sumElems(left).val[0]);
        //telemetry.addData("Right raw value", (int) Core.sumElems(right).val[0]);
        //telemetry.addData("Left percentage", Math.round(leftValue * 100) + "%");
        //telemetry.addData("Right percentage", Math.round(rightValue * 100) + "%");
        //telemetry.addData("Left percentage", Math.round(blueleftValue * 100) + "%");
        //telemetry.addData("Right percentage", Math.round(bluerightValue * 100) + "%");

        //boolean stating that element are either right or left if the box is filled to more than 'PERCENT_COLOR_THRESHOLD'
        boolean elementLeft = leftValue < PERCENT_COLOR_THRESHOLD;
        boolean elementRight = rightValue < PERCENT_COLOR_THRESHOLD;

        //tells location of element(left right or not found(looks for stuff in rectangles))
        if ((!elementLeft && !elementRight) ){
            //if element not found, it say not found
            location = Location.NOT_FOUND;
            IntLocation=0;
            //  telemetry.addData("Element Location", "not found; on left side");
        }
        else if (elementLeft ) {
            //if 40% or more of element in left rectangle, it say left
            location = Location.LEFT;
            IntLocation=1;
            //telemetry.addData("Element Location", "middle");
        }
        else {
            //if 40% or more of element in right rectangle, it say right
            location = Location.RIGHT;
            IntLocation=2;
            // telemetry.addData("Element Location", "right");
        }
        //telemetry.update();

        //turns everything that not the color we look for into black
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        // color of rectangles in camera stream
        Scalar colorStone = new Scalar(255, 0, 0);
        Scalar colorSkystone = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, LEFT_ROI, location == Location.LEFT? colorSkystone:colorStone);
        Imgproc.rectangle(mat, RIGHT_ROI, location == Location.RIGHT? colorSkystone:colorStone);


        return mat;


    }

    //takes location of element and gives it to us
    public Location getLocation() {
        return location;
    }
    public int getIntLocation() {return IntLocation;}
}
