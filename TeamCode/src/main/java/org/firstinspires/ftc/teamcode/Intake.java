package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public static double INTAKE_SPEED = .6;
    public static double INTAKE_REVERSE = -.4;

    DcMotor intake_motor = null;


    public void init (){
        HardwareMap hwmap = null;
        intake_motor = hwmap.get(DcMotor.class,"intakemotor");
//init motor

        intake_stop();


    }

public void intake_start(){
    intake_motor.setPower(INTAKE_SPEED);
    }

public void intake_stop() {
intake_motor.setPower(0);
    }

public void intake_reverse() {
    intake_motor.setPower(INTAKE_REVERSE);

}


    }

