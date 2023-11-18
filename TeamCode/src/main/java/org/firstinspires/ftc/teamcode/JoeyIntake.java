package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.Pose2d;

public class JoeyIntake {

    DcMotor joey;

    public double joeysSpeed = 0.75;
    public double joeysReverseSpeed = -1;

    public double joeysAutoDrop = 0.25;

    public int JOEY = 0;

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
        joey.setPower(joeysReverseSpeed);
    }

    public void Slow_Start(){ joey.setPower(joeysAutoDrop); }
    public void Slow_Inverse(){ joey.setPower(-joeysAutoDrop); }
    public int joey(){ return joey(); }

    public class ChangeJoey implements Action {
        public void init() {
            Slow_Start();
        }
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
             JOEY+= 1;
            return false;}
    }

    public class EntakeOn implements Action {
        public void init() {
            Slow_Start();
        }
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Slow_Inverse();
            return false;}
    }

    public class EntakeBack implements Action {
        public void init() {Slow_Start();}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Slow_Start();
            return false;}
    }

    public class EntakeNo implements Action {
        public void init() {JimStop();}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            JimStop();
            return false;}
    }


    public Action inverse() {
        return new JoeyIntake.EntakeBack();
    }
    public Action start() {
        return new JoeyIntake.EntakeOn();
    }
    public Action stop() {
        return new JoeyIntake.EntakeNo();
    }

}
