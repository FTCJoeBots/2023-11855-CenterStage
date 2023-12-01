package org.firstinspires.ftc.teamcode;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import androidx.annotation.NonNull;
public class Shooter {
    static Servo Shooter;

    public void init(HardwareMap hardwareMap){
        Shooter = hardwareMap.get(Servo.class,"Shooter");
        Shooter.setPosition(0.8);
    }

    public void shoot(){
        Shooter.setPosition(0.2);
    }
    public void Back(){Shooter.setPosition(0.8);}
}
