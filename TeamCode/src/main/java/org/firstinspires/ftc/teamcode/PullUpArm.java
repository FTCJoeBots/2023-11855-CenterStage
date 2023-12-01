package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.Pose2d;
public class PullUpArm {
    public DcMotor Pullup;

    int pullup_target_position = 0;
    double HANGER_SPEED = 0.75;
    static boolean HangerDown = false;


    public void init(HardwareMap hwMap) {
        Pullup = hwMap.get(DcMotor.class, "PullUp");
        Pullup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Pullup.setTargetPosition(pullup_target_position);
        Pullup.setDirection(DcMotor.Direction.FORWARD);
        Pullup.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Pullup.setPower(0);

    }

    public void contorller() {
        //Pullup.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Pullup.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //Pullup.setPower(0.75);
    }
    public void controller2() {
        Pullup.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Pullup.setPower(-0.85);
    }
/*
    public void raisePullUpManual() {
        lift_target_position = Pullup.getCurrentPosition() + 750;

    }

    public void lowerPullManual() {
        lift_target_position = Pullup.getCurrentPosition() - 750;
    }
*/
    public void stop(){
        Pullup.setPower(0);
    }


    public void resetHanger(){
        pullup_target_position = 1500;
        HangerDown=false;
    }
    public void upHanger(){
        pullup_target_position = 4400;
        HangerDown=true;

    }

    public void PullUp(){
pullup_target_position = Pullup.getCurrentPosition()+2000;
Pullup.setPower(1);
    }

    public void PullDown(){
        pullup_target_position = Pullup.getCurrentPosition()-2000;
        Pullup.setPower(-1);
    }

    public void pulldown(){
        Pullup.setDirection(DcMotorSimple.Direction.REVERSE);
        Pullup.setPower(0.95);
    }
    public void Zero(){
        pullup_target_position = 0;
    }
    public int getHangerTargetPosition(){
        return pullup_target_position;
    }

    public  void toggleHanger(){
        if(HangerDown){
            resetHanger();
        }else{
            upHanger();
        }
    }




   // public void Hcheck() {
     //   Pullup.setTargetPosition{{\]pullup_target_position);
       // hangerM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //hangerM.setPower(HANGER_SPEED);

public void Hcheck(){
        Pullup.setTargetPosition(pullup_target_position);
        Pullup.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Pullup.setPower(HANGER_SPEED);
}}