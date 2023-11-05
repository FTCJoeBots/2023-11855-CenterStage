package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.*;
public class PullUpArm {


    DcMotor pullup;

    int PullUp_target_position=0;

    int initpos = 0;



    public void init(HardwareMap hwmap){
        pullup = hwmap.get(DcMotor.class,"PullUp");
        pullup.setTargetPosition(initpos);
        pullup.setTargetPosition(initpos);
        pullup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullup.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullup.setPower(0);
    }
    public void contorller(){
        pullup.setTargetPosition(PullUp_target_position);
        pullup.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullup.setPower(0.5);
    }

    public void ManualPullUp(){
        PullUp_target_position = pullup.getCurrentPosition()+100;
        pullup.setPower(0.5);
    }

    public void ManualPullDown(){
        PullUp_target_position = pullup.getCurrentPosition()-100;
        pullup.setPower(0.5);
    }


}
