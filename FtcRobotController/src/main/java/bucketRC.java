import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class bucketRC {
    public static double BUCKETSTART = .4;

    public static double BUCKETEND = .6;

    Servo bucketservo = null ;
    public void init (){
        HardwareMap hwMap = null;
        bucketservo=hwMap.get(Servo.class,"bucketservo");
    bucketservo.setPosition(BUCKETSTART);
    }

    public void bucketempty() {
bucketservo.setPosition(BUCKETSTART);
//
    }

    public void bucketfull() {
bucketservo.setPosition(BUCKETEND);

    }









}


