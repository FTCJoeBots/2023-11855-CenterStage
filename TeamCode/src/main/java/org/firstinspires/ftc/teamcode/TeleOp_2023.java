package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "myTeleOp")
public class TeleOp_2023 extends OpMode{
    HardwareMap hwmap = null;
    Lift l = new Lift();

    public void init() {
        l.init(hwmap);
        telemetry.addLine("Completed");
        telemetry.update();
    }

    public void loop(){
        if (gamepad2.dpad_up){
            l.LiftManualUp();
        }
        if(gamepad2.dpad_down){
          l.LiftManualDown();
        }

    }

}
