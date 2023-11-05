package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
public class BucketArm {
    static CRServo BucketArm ;

    public static int serv = 0;

    public void init(HardwareMap hwmap){
        BucketArm = hwmap.get(CRServo.class,"BucketServo");
        BucketArm.setPower(0.0);
    }

    public void BucketRight(){
        serv=0;
        BucketArm.setPower(0.5);
        while(serv<4000000){
            serv = serv+1;
        }
        BucketArm.setPower(0);
    }

    public static void BucketLeft(){
        serv = 0;
        BucketArm.setPower(-0.4);
        while(serv<4000000){
            serv=serv+1;
        }
        BucketArm.setPower(0);
    }
    public void BucketStop(){
        BucketArm.setPower(0);
    }
}
