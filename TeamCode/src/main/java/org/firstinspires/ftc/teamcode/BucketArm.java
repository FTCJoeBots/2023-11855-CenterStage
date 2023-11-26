package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.*;

public class BucketArm {

   /* static CRServo servo;
    static DcMotor encoder;
    public static int serv = 0;

    public void init(HardwareMap hwMap){
        servo = hwMap.get(CRServo.class,"servo");
        encoder = hwMap.get(DcMotor.class, "encoder");
        servo.setPower(0.0);
    }

    public void moveright(){
        serv=0;
        if(encoder.getCurrentPosition() <= 10000){
            servo.setPower(0.9);
        }

        while(serv< 4000000){
            serv = serv+1;
            if(encoder.getCurrentPosition() > 10000){
                serv = 40000000;
            }
        }
        servo.setPower(0);
    }
    public static void moveleft(){
        serv = 0;
        servo.setPower(-0.9);
    while(serv < 4000000){
        serv = serv+1;
    }
    servo.setPower(0);

    }

    public static void stop(){
        servo.setPower(0.0);
    }

*/

    final double IntakeSide = 0;
    final double OutputSide = 0.5;

    final double ClosedBucket = 0;
    final double OpenBucket = 0.5;

    ///init to .4
    private static final double InBucket = 0;
    private static final double OutBucket = 0.425;

    static boolean BucketInB = false;


    private static final double ClosedBucketGate = 0.8;
    private static final double OpenBucketGate = 0.58;

    static boolean BucketGateClosedB = false;


    static Servo Bucket ;
    static Servo BucketGate ;


    public enum BucketStartPosition {
        //Bucket Points In (towards the intake)
        IN,
        //Bucket Points Out (towards the backboard)

        OUT
    }

    public enum BucketGateStartPosition {
        //Bucket Points In (towards the intake)
        CLOSE,
        //Bucket Points Out (towards the backboard)

        OPEN
    }

    public static void init(HardwareMap hwMap, BucketStartPosition BSP, BucketGateStartPosition BGSP) {
        Bucket = hwMap.get(Servo.class,"servo");
        //  BucketGate = hwMap.get(Servo.class,"BucketGate");
        Bucket.setPosition(InBucket);
        // BucketGate.setPosition(ClosedBucketGate);
        //     if (BSP == BucketStartPosition.IN) {
        //         Bucket.setPosition(IntakeSide);
        //     } else if (BSP == BucketStartPosition.OUT) {
        //         Bucket.setPosition(OutputSide);
        //     }

        //     if (BGSP == BucketGateStartPosition.CLOSE) {
        //         BucketGateIn();
        //     } else if (BGSP == BucketGateStartPosition.OPEN) {
        //         BucketGateOut();
        //     }

    }

    //Bucket

    public void BucketSet(int BucketPos) {

        switch(BucketPos){
            case 2:
                Bucket.setPosition(IntakeSide);
                break;
            case 1: // This is first
                Bucket.setPosition(OutputSide);
                break;

        }
    }

    public static void BucketOut(){
        Bucket.setPosition(OutBucket);
        BucketInB=true;
    }
    public static void BucketIn(){
        Bucket.setPosition(InBucket);
        BucketInB=false;
    }

    public static void ToggleBucket(){
        if(BucketInB){
            BucketIn();
        }else{
            BucketOut();
        }
    }

    public static double getBucketPosition(){ return Bucket.getPosition(); }


    //BucketGate

    public static void BucketGateOut(){
        BucketGate.setPosition(OpenBucketGate);
        BucketGateClosedB=true;
    }
    public static void BucketGateIn(){
        BucketGate.setPosition(ClosedBucketGate);
        BucketGateClosedB=false;
    }

    public static void ToggleBucketGate(){
        if(BucketGateClosedB){
            BucketGateIn();
        }else{
            BucketGateOut();
        }
    }


    public void BucketGate(int BucketGatePos) {

        switch(BucketGatePos){
            case 1:
                BucketGate.setPosition(OpenBucket);
                break;
            case 2:
                BucketGate.setPosition(ClosedBucket);
                break;

        }
    }
    public class BucketOut implements Action {
        public void init() {Bucket.setPosition(OutBucket);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Bucket.setPosition(OutBucket);
            return false;}
    }

    public Action BucketDrop() {
        return new BucketOut();
    }

    public class BucketBack implements Action {
        public void init() {Bucket.setPosition(InBucket);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Bucket.setPosition(InBucket);
            return false;}
    }

    public Action BucketBack() {
        return new BucketBack();
    }

    public static double getGatePosition(){
        return BucketGate.getPosition();
    }
}
