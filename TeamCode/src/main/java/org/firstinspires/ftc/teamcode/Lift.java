package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.Pose2d;
public class Lift {
    DcMotor LeftLift;
    DcMotor RightLift;

    int LIFTMAXIMUM = -2500;
    int LIFTMINIMMUM = 30;
    int lift_target_position=0;

    int Rightlow = -783;
    int Rightmed = -1490;
    int Righthigh = -2246;

    int LeftLow = -783;

    int Leftmed = -1490;

    int Lefthigh = -2246;
    public double getLiftPositionRight(){ return RightLift.getCurrentPosition();}
    public double getLiftPositionLeft(){return LeftLift.getCurrentPosition();}

    public void init(HardwareMap hwMap){
        LeftLift = hwMap.get(DcMotor.class,"LeftLift");
        LeftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftLift.setTargetPosition(LIFTMINIMMUM);
        LeftLift.setDirection(DcMotor.Direction.FORWARD);
        LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftLift.setPower(0);
        RightLift = hwMap.get(DcMotor.class,"RightLift");
        RightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightLift.setDirection(DcMotor.Direction.REVERSE);
        RightLift.setTargetPosition(LIFTMINIMMUM);
        RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightLift.setPower(0);
    }

    public void contorller(){
        LeftLift.setTargetPosition(lift_target_position);
        LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftLift.setPower(0.6);
        RightLift.setTargetPosition(lift_target_position);
        RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightLift.setPower(0.6);
    }

    public void raiseLiftManual(){
        lift_target_position = LeftLift.getCurrentPosition()+100;
        lift_target_position = RightLift.getCurrentPosition()-100;
    }

    public void lowerLiftManual(){
        lift_target_position = LeftLift.getCurrentPosition()-100;
        lift_target_position = RightLift.getCurrentPosition()+100;
    }

    public void RightLift_To_Position(int LiftPosition) {
        switch (LiftPosition) {
            case 0:
                lift_target_position = LIFTMINIMMUM;
                break;
            case 1:
                lift_target_position = Rightlow;
                break;
            case 2:
                lift_target_position = Rightmed;
                break;
            case 3:
                lift_target_position = Righthigh;
                break;


        }
    }
    public void LeftLift_To_Position(int LeftLiftPosition) {
        switch (LeftLiftPosition) {
            case 0:
                lift_target_position = LIFTMINIMMUM;
                break;
            case 1:
                lift_target_position = LeftLow;
                break;
            case 2:
                lift_target_position = Leftmed;
                break;
            case 3:
                lift_target_position = Lefthigh;
                break;


        }
    }

    public boolean LeftLiftSafety(){
        if(LeftLift.getCurrentPosition() > -LIFTMAXIMUM){
            return true;
        }else{
            return false;
        }
    }


    public boolean RightLiftSafety(){
        if(RightLift.getCurrentPosition() > LIFTMAXIMUM){
            return true;
        }else{
            return false;
        }
    }


    public class LeftliftToOne implements Action {
        public void init() {LeftLift_To_Position(1);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            LeftLift_To_Position(1);
            contorller();
            return false;}
    }
    public class RightliftToOne implements Action {
        public void init() {RightLift_To_Position(1);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            RightLift_To_Position(1);
            contorller();
            return false;}
    }


    public class LeftliftToZero implements Action {
        public void init() {LeftLift_To_Position(0);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            LeftLift_To_Position(1);
            contorller();
            return false;}
    }
    public class RightliftToZero implements Action {
        public void init() {RightLift_To_Position(0);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            RightLift_To_Position(1);
            contorller();
            return false;}
    }




    public Action RightPos0() {
        return new Lift.RightliftToZero();

    }
    public Action RightPos1() {
        return new Lift.RightliftToOne();
    }
    public Action LeftPos1() {
        return new Lift.LeftliftToOne();
    }
    public Action LefPos0() {
        return new Lift.LeftliftToZero();
    }





}
