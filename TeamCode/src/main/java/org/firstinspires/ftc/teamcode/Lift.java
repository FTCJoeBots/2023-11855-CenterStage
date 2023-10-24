package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {

    public static double LIFTSPEED = .3;
    public static int LIFTMAX = 500;

    DcMotor LiftMotor = null;

    public void init () {

        HardwareMap hwmap = null;
        LiftMotor = hwmap.get(DcMotor.class,"Lift_Motor");

        LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //have to initialize motor
        LiftStop();




    }

    public void LiftUp (){
        LiftMotor.setTargetPosition(LIFTMAX);
        LiftMotor.setPower(LIFTSPEED);

    }

    public void LiftDown (){
        LiftMotor.setTargetPosition(0);
        LiftMotor.setPower(LIFTSPEED);

    }

    public void LiftStop (){
        LiftMotor.setPower(0);


    }
}
