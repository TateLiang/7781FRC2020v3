package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

// Utilizing I2C
// 
import edu.wpi.first.wpilibj.I2C;

// Importing libraries
// Color sensor libraries

import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;



// Import SmartDashboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Function_Spin {

     // Color wheel motor

     WPI_VictorSPX _colorWheel = new WPI_VictorSPX(7);
    public void spinSetUp() {
        _colorWheel.configFactoryDefault();
        
    }

    public void spinMotor(double spin) {
        _colorWheel.set(spin);
        
    }
}