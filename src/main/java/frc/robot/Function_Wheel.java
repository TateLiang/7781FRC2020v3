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

import frc.robot.Function_Spin;


// Import SmartDashboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Function_Wheel {
    
    boolean automatic;
    String firstColor;
    String lastColor;
    boolean colorControl;
    int redCount = 0;
    int greenCount = 0;
    int blueCount = 0;
    int yellowCount = 0;
    WPI_TalonSRX _colorWheel = new WPI_TalonSRX(1); // Color wheel motor

    // Set I2C motor port as kOnboard port
    
    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    // Match color sensor to kOnboard port
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();

    //colour ranges
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);        
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    Function_Spin spin = new Function_Spin();
    
    // Color array
   
    
    public void colourSensorSetup() {
        // Initiate color range variables
		m_colorMatcher.addColorMatch(kBlueTarget);
		m_colorMatcher.addColorMatch(kGreenTarget);
		m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget); 
        _colorWheel.configFactoryDefault();

        // Set up motor spinning mechanism
        spin.spinSetUp();
    }
    public String colourSensorPeriodic() {
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
        Color detectedColor = m_colorSensor.getColor();

        /**
         * Run the color match algorithm on our detected color
         */
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
        colorString = "Blue";
        } else if (match.color == kRedTarget) {
        colorString = "Red";
        } else if (match.color == kGreenTarget) {
        colorString = "Green";
        } else if (match.color == kYellowTarget) {
        colorString = "Yellow";
        } else {
        colorString = "Unknown";
        }
    
        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the 
         * sensor.
         */
        /*
        SmartDashboard.putNumber("Red", detectedColor.red);
        
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
       // SmartDashboard.putNumber("yellow", detectedColor.yellow)
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);
        */
        return colorString;
    }

    public void rotationControlPeriodic(String colorString) {
        if (colorString=="start"){
            // Initiate variables
            automatic = true;
            firstColor = colorString;
            lastColor = colorString;
            redCount = 0;
            greenCount = 0;
            blueCount = 0;
            yellowCount = 0;
            System.out.println("rotation control starting!!");
        } else if (colorString == "stop"){
            // Exit rotation control mode
            automatic = false;
            System.out.println("rotation control stopping!!");
        }

  
        if (automatic==true){
            spin.spinMotor(0.3);
           
            if (colorString != lastColor){
                System.out.println("Different color");
                if (colorString=="Red"){
                    redCount+=1;
                } else if(colorString == "Blue"){
                    blueCount+=1;
                } else if(colorString == "Yellow"){
                    yellowCount+=1;
                } else if(colorString =="Green"){
                    greenCount+=1;
                }
            }
            
            if(yellowCount >= 4 || redCount >= 4 || blueCount >= 4 || greenCount >= 4){
                automatic = false;
                System.out.println("Spun the wheel 4 times, stopped spinning");
                
            }
            lastColor = colorString;

        }
    }

    public void positionControlPeriodic(String colorString, int targetColor){
        if (colorString=="start"){
            // Initiate variables
            automatic = true;
            System.out.println("position control starting");
        } else if (colorString == "stop"){
            automatic = false;
            System.out.println("position control emergency stopping");
        } else {
           
            if(automatic == true){
                String[] colorArray = new String[]{"Red","Green","Blue","Yellow"};
                String sensorTarget = colorArray[targetColor+1];
                spin.spinMotor(0.2);
                if(colorString==sensorTarget){
                    automatic=false;
                    System.out.println("color target reached, stopping position control");
                }
            }
        }


    }
}