package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

// Utilizing I2C
// 
import edu.wpi.first.wpilibj.I2C;

// Importing libraries
// Color sensor libraries
/*
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
*/
// Import SmartDashboard
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Function_Wheel {
    WPI_TalonSRX _colorWheel = new WPI_TalonSRX(1); // Color wheel motor

    // Set I2C motor port as kOnboard port
    /*
    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    // Match color sensor to kOnboard port
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();

    //colour ranges
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);        //private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    */
    public void colourSensorSetup() {
        // Initiate color range variables
        /*
		m_colorMatcher.addColorMatch(kBlueTarget);
		m_colorMatcher.addColorMatch(kGreenTarget);
		//m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget); 
        */
    }
    public void colourSensorPeriodic() {
            /**
         * The method GetColor() returns a normalized color value from the sensor and can be
         * useful if outputting the color to an RGB LED or similar. To
         * read the raw color, use GetRawColor().
         * 
         * The color sensor works best when within a few inches from an object in
         * well lit conditions (the built in LED is a big help here!). The farther
         * an object is the more light from the surroundings will bleed into the 
         * measurements and make it difficult to accurately determine its color.
         */
      //Color detectedColor = m_colorSensor.getColor();

        /**
         * Run the color match algorithm on our detected color
         */
     String colorString;
    //ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
/*
        if (match.color == kBlueTarget) {
        colorString = "Blue";
        } //else if (match.color == kRedTarget) {
        //colorString = "Red";
         else if (match.color == kGreenTarget) {
        colorString = "Green";
        } else if (match.color == kYellowTarget) {
        colorString = "Yellow";
        } else {
        colorString = "Unknown";
        }
    */
        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the 
         * sensor.
         */
        
        //SmartDashboard.putNumber("Red", detectedColor.red);
        /*
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);
        System.out.println("Hello world");
        //System.out.println(detectedColor.red);
        System.out.println(detectedColor.green);
        System.out.println(detectedColor.blue);
        System.out.println(match.confidence);	
        */

    }
    public void spinColourWheel(double spin) {
        _colorWheel.set(spin);
        
    }
}