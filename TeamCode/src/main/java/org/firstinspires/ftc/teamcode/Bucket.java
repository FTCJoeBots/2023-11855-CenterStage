//package org.firstinspires.ftc.teamcode;

//public class Bucket {
//}
package org.firstinspires.ftc.teamcode;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.ServoConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class Bucket {
    static  double IntakeSide = 0.4;
    static final double OutputSide = -3.5;

    static boolean BucketInB = false;
    private static final double ClosedBucketGate = 0;
    private static final double OpenBucketGate = 0.5;
    static boolean BucketGateClosedB = false;

    static Servo BucketGate ;

    public void init(HardwareMap hwMap) {

        BucketGate = hwMap.get(Servo.class,"BucketGate");
        //Bucket.setPosition(IntakeSide);
        BucketGate.setPosition(1);

    }



    //Bucket


    public void bucketMore(){

        IntakeSide+=0.1;

    }

    public void bucketLess(){

        IntakeSide-=0.1;

    }



    //BucketGate
    public static void BucketGateOut(){
        BucketGate.setPosition(ClosedBucketGate);
        BucketGateClosedB=true;
    }
    public static void BucketGateIn(){
        BucketGate.setPosition(2.3);
        BucketGateClosedB=false;
    }
    public static void ToggleBucketGate(){
        if(BucketGateClosedB){
            BucketGateIn();
        }else{
            BucketGateOut();
        }
    }
    /* public void BucketGate(int BucketGatePos) {

         switch(BucketGatePos){
             case 1:
                 BucketGate.setPosition(OpenBucket);
                 break;
             case 2:
                 BucketGate.setPosition(ClosedBucket);
                 break;

         }
     }*/

    public class OpenGate implements Action {
        public void init() {BucketGate.setPosition(1);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            BucketGate.setPosition(1);
            return false;}
    }

    public Action GateDrop() {
        return new OpenGate();
    }


    public class closeGate implements Action {
        public void init() {BucketGate.setPosition(0);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            BucketGate.setPosition(0);
            return false;}
    }

    public Action CloseGate() {
        return new closeGate();
    }
    public static double getGatePosition(){ return BucketGate.getPosition(); }}
