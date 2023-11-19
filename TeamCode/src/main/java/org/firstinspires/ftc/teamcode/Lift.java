package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.Pose2d;
public class Lift {
    static DcMotor LeftLift;
    static DcMotor RightLift;

    static int LIFTMAXIMUM = -2500;
    static int LIFTMINIMMUM = 30;
    static int lift_target_position=0;

    static int Rightlow = -695;
   static int Rightmed = -1490;
    static int Righthigh = -2246;

    static int RightAuto = -630;
    static int LeftAuto = -630;

   static int LeftLow = -695;

    static int Leftmed = -1490;

   static int Lefthigh = -2246;
    public static double getLiftPositionRight(){ return RightLift.getCurrentPosition();}
    public static double getLiftPositionLeft(){return LeftLift.getCurrentPosition();}

    public static void init(HardwareMap hwMap){
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

    public static void contorller(){
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

    public static void RightLift_To_Position(int LiftPosition) {
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
    public static void LeftLift_To_Position(int LeftLiftPosition) {
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

    public static boolean LeftLiftSafety(){
        if(LeftLift.getCurrentPosition() > -LIFTMAXIMUM){
            return true;
        }else{
            return false;
        }
    }


    public static boolean RightLiftSafety(){
        if(RightLift.getCurrentPosition() > LIFTMAXIMUM){
            return true;
        }else{
            return false;
        }
    }


    public static class AutoliftToOne implements Action {
        public void init() {LeftLift_To_Position(1);
        RightLift_To_Position(1);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            LeftLift_To_Position(1);
            RightLift_To_Position(1);
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


    public class liftToZero implements Action {
        public void init() {LeftLift_To_Position(0);
        RightLift_To_Position(0);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            LeftLift_To_Position(0);
            RightLift_To_Position(0);
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
    public  static Action AutoPos1() {
        return new Lift.AutoliftToOne();
    }
    public Action LefPos0() {
        return new Lift.liftToZero();
    }





}
