// Robot.java: The main file of the robot
// - handles joystick controls

// Author: Tate Liang
// CO-Author: Bob Xiong
// Date: Jan 22, 2020

package frc.robot;

// Importing libraries
// Color sensor libraries
//import edu.wpi.first.wpilibj.util.Color;
//import com.revrobotics.ColorSensorV3;
//import com.revrobotics.ColorMatchResult;
//import com.revrobotics.ColorMatch;

// Import SmartDashboard
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Import functions
import frc.robot.Function_Drive;

// Import servo library
import edu.wpi.first.wpilibj.Servo;

// Utilizing I2C
//import edu.wpi.first.wpilibj.I2C;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.*;

// Import Joystick module
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot{

	/* DEFINING FUNCTIONS */
	Function_Drive driveTrain = new Function_Drive();

	WPI_TalonSRX _colorWheel = new WPI_TalonSRX(1); // Color wheel motor
	Joystick _gamepad = new Joystick(0); // Initate joystick object
	// Servo for arm, PWM 0
	Servo _armServo = new Servo(0);
	// Left servo for trough, PWM 1
	Servo leftServo = new Servo(1);
	// Right servo for trough, PWM 2
	Servo rightServo = new Servo(2);

	// Servo states
	boolean expand = false;
	int troughServoAngle = 0;

	// Climb arm state
	int climbArmState = 0;

	// Set I2C motor port as kOnboard port
	//private final I2C.Port i2cPort = I2C.Port.kOnboard;

	// Match color sensor to kOnboard port
	//private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
	//private final ColorMatch m_colorMatcher = new ColorMatch();

	//colour ranges
	//private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
	//private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
	//private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
	//private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

	@Override
	public void robotInit() {	
	}
	
	@Override
	public void teleopInit(){
		// Initiate color range variables
		//m_colorMatcher.addColorMatch(kBlueTarget);
		//m_colorMatcher.addColorMatch(kGreenTarget);
		//m_colorMatcher.addColorMatch(kRedTarget);
		//m_colorMatcher.addColorMatch(kYellowTarget); 

		driveTrain.driveSetup();

		// Set all servo angles to 0
		leftServo.setAngle(0);
		rightServo.setAngle(0);
		_armServo.setPosition(0.25);
	}
	
	@Override
	public void teleopPeriodic() {	
	// Initiate trigger button for the color wheel
	boolean triggerButton = _gamepad.getRawButton(1);

	// Initiate top button for arm servo
	boolean topButton = _gamepad.getRawButton(2);

	// Initiate button 1 (left botton on top) for ball trough
	boolean troughButton = _gamepad.getRawButton(2);
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
  //  Color detectedColor = m_colorSensor.getColor();

    /**
     * Run the color match algorithm on our detected color
     */
  /*  String colorString;
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
*/
    /**
     * Open Smart Dashboard or Shuffleboard to see the color detected by the 
     * sensor.
     */
	
    //SmartDashboard.putNumber("Red", detectedColor.red);
    //SmartDashboard.putNumber("Green", detectedColor.green);
    //SmartDashboard.putNumber("Blue", detectedColor.blue);
    //SmartDashboard.putNumber("Confidence", match.confidence);
    //SmartDashboard.putString("Detected Color", colorString);
	//System.out.println("Hello world");
	//System.out.println(detectedColor.red);
	//System.out.println(detectedColor.green);
	//System.out.println(detectedColor.blue);
	//System.out.println(match.confidence);	

		/* Drive */
		double forward = -1 * _gamepad.getY(); // Going forwards and backwards by tracking joystick position
		double turn = _gamepad.getTwist(); // Turning by tracking joystick twist angle
		
		// Using deadband so minor joystick movements will not pass through and move the robot
		forward = Deadband(forward);
		turn = Deadband(turn);

		/* Arcade Drive using PercentOutput along with Arbitrary Feed Forward supplied by turn */
		driveTrain.drivePeriodic(turn, forward);

		/* Colour wheel */
		// If trigger button is pressed, spin the color wheel
		if (triggerButton){
			_colorWheel.set(1);
		}else{
			_colorWheel.set(0);
		}

		/* Climb */
		// Toggle servo states
		System.out.println(_armServo.getPosition());
		System.out.println(expand);
		
		if (topButton){
			if (_armServo.getPosition() == 1){
				_armServo.setPosition(0.25);
			}else if(_armServo.getPosition() == 0.25){
				_armServo.setPosition(1);
			}
		} 
		
		/* Intake & trough */
		// If trough button is pressed, switch servo state by subtracting angle from 90 degrees
		if (troughButton){
			troughServoAngle = 90 - troughServoAngle;
			leftServo.setAngle(troughServoAngle);
			rightServo.setAngle(troughServoAngle);
		}
	}

	/* UTILITY FUNCTIONS */
	/** Deadband 5 percent, used on the gamepad */
	double Deadband(double value) {
		/* Upper deadband */
		if (value >= +0.075) 
			return value;
		/* Lower deadband */
		if (value <= -0.075)
			return value;
		/* Outside deadband */
		return 0;
	}
}