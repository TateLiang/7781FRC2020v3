package frc.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

public class Function_Intake {

    boolean intakeState = false;
    //WPI_VictorSPX _toggleIntake = new WPI_VictorSPX(11);
    WPI_VictorSPX _shootOut = new WPI_VictorSPX(11);
    double speed = 0;
    double shootspeed = 0;
    WPI_VictorSPX _redMotor1 = new WPI_VictorSPX(8); // Color wheel motor

    WPI_VictorSPX _redMotor2 = new WPI_VictorSPX(9);
   
	
    long startTime = System.currentTimeMillis();
    long intakeStateTime = System.currentTimeMillis();
    public void troughServoSetup() {

        

//        _toggleIntake.configFactoryDefault();
        _shootOut.configFactoryDefault();

        _redMotor1.configFactoryDefault();
        _redMotor2.configFactoryDefault();
        // Set all servo angles to 0
     

        startTime = System.currentTimeMillis();
        intakeStateTime = System.currentTimeMillis();
		
    }
    public void spinOut(double motorSpeed){
        _shootOut.set(motorSpeed);
    }

    public void spinIn(boolean state) {

        if ((System.currentTimeMillis()-intakeStateTime)>500 && state==true){
            if(intakeState == true){
                intakeState = false;
                //_toggleIntake.set(-0.1);
                //_shootOut.set(0);
                intakeStateTime = System.currentTimeMillis();
            } else if(intakeState == false){
                intakeState = true;
               // _toggleIntake.set(0.1);
                intakeStateTime = System.currentTimeMillis();
            }

        }

        //_shootOut.set(0);
        if (intakeState == true){
            speed = 0.65;
           // _shootOut.set(-0.3);
            System.out.println("epic spinning");
        
        } else if (intakeState == false){
            speed = 0;
            
            System.out.println("not epic");


        }


        _redMotor1.set(speed);
        _redMotor2.set(-speed);
        //_shootOut.set(speed);
    }


    

}