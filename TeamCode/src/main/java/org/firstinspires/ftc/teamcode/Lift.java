package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
public class Lift {
    DcMotor LeftLift;
    DcMotor RightLift;

    int LIFTMAXIMUM = 3000;
    int LIFTMINIMMUM = 0;
    int lift_target_position=0;

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
        lift_target_position = RightLift.getCurrentPosition()+100;
    }

    public void lowerLiftManual(){
        lift_target_position = LeftLift.getCurrentPosition()-100;
        lift_target_position = RightLift.getCurrentPosition()-100;
    }


}
