package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Bucket;
import org.firstinspires.ftc.teamcode.BucketArm;
import org.firstinspires.ftc.teamcode.JoeyIntake;
import org.firstinspires.ftc.teamcode.Lift;
import org.firstinspires.ftc.teamcode.PullUpArm;
import org.firstinspires.ftc.teamcode.TeleOpMecanum;

@TeleOp(name = "PullUP Test",group="TeleOp")

public class PullTest extends OpMode{

    PullUpArm pullup;
    @Override
    public void init() {

        //pullup = hardwareMap.get(DcMotor.class,"PullUp");
        pullup.Pullup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullup.Pullup.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pullup.Pullup.setPower(0);







    }
        //while(opModeIsActive()&&!isStopRequested()){
 public void loop(){


     if (gamepad1.dpad_down) {
         pullup.Pullup.setPower(0.75);
     }else{
         pullup.Pullup.setPower(0);
     }

     if(gamepad1.dpad_up){
         pullup.Pullup.setPower(-0.75);
     }else{
         pullup.Pullup.setPower(0);
     }



 //    telemetry.addData("Encdoer Pos", pullup.Pullup.getCurrentPosition());
 //    telemetry.update();

     }


 }


