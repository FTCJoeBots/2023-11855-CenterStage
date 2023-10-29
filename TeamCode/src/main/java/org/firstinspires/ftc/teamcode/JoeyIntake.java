package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class JoeyIntake {

    DcMotor joey;

    public double joeysSpeed = 0.75;
    public double joeysReverseSpeed = -0.25;

    public void init(HardwareMap hwmap){
        joey = hwmap.get(DcMotor.class,"jim");
        joey.setPower(0);
    }
    public void JimStart(){
        joey.setPower(joeysSpeed);
    }

    public void JimStop(){
        joey.setPower(0);
    }

    public void JimReverse(){
        joey.setPower(-joeysReverseSpeed);
    }

}
