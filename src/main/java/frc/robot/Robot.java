// Robot.java: Basically does everything
// Author: Tate Liang
// CO-Author: Bob Xiong
// Date: Jan 22, 2020

// Importing libraries
package frc.robot;

// Color sensor libraries
//import edu.wpi.first.wpilibj.util.Color;

//import com.revrobotics.ColorSensorV3;
//import com.revrobotics.ColorMatchResult;
//import com.revrobotics.ColorMatch;

// Import SmartDashboard
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Import servo library
import edu.wpi.first.wpilibj.Servo;

// Utilizing I2C
//import edu.wpi.first.wpilibj.I2C;

// Phoenix library
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// Import motor control
//import static org.junit.Assume.assumeTrue;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.*;

// Import Joystick module
import edu.wpi.first.wpilibj.Joystick;

// Timed Robot module(?)
import edu.wpi.first.wpilibj.TimedRobot;

// Timed Robot
public class Robot extends TimedRobot{
	// Setting ID for motors
	// Drive train motors
	// Talons are masters
	// Victors are slaves
	// Set ID for front left motor as Talon SRX 3
	WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(3);
	// Set ID for frong right motor as Talon SRX 6
	WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(6);
	// Set ID for left slave motor as Victor SPX 4
	WPI_VictorSPX _leftSlave1 = new WPI_VictorSPX(4);
	// Set ID for right slave motor as Victor SPX 5
	WPI_VictorSPX _rightSlave1 = new WPI_VictorSPX(5);

	// Color wheel motor
	WPI_TalonSRX _colorWheel = new WPI_TalonSRX(1);

	// Initate joystick object
	Joystick _gamepad = new Joystick(0);
	

	// Set differential drive using front left motor and front right motor
	DifferentialDrive _drive = new DifferentialDrive(_frontLeftMotor, _frontRightMotor);

	// Set I2C motor port as kOnboard port
	//private final I2C.Port i2cPort = I2C.Port.kOnboard;

	// Match color sensor to kOnboard port
	//private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
	//private final ColorMatch m_colorMatcher = new ColorMatch();

	//Servos
	// Servo for arm, PWM 0
	Servo _armServo = new Servo(0);
	// Left servo for trough, PWM 1
	Servo leftServo = new Servo(1);
	// Right servo for trough, PWM 2
	Servo rightServo = new Servo(2);

	// Servo states
	boolean expand = false;
	int angle = 45;
	int troughServoAngle = 0;

	// Climb arm state
	int climbArmState = 0;

	// Color ranges
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

		// Factory reset all motor controllers in case of software corruption
		_frontLeftMotor.configFactoryDefault();
		_frontRightMotor.configFactoryDefault();
		_leftSlave1.configFactoryDefault();
		_rightSlave1.configFactoryDefault();
		
		_colorWheel.configFactoryDefault();

		// Set left back motor and right back motor to be the slaves of left front
		// motor and right front motor, respectively
		_leftSlave1.follow(_frontLeftMotor);
		_rightSlave1.follow(_frontRightMotor);
		
		/* Set Neutral mode */
		_frontLeftMotor.setNeutralMode(NeutralMode.Brake);
		_frontRightMotor.setNeutralMode(NeutralMode.Brake);
		
		_colorWheel.setNeutralMode(NeutralMode.Brake);
		
		/* Configure output direction */
		_frontLeftMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
		_frontRightMotor.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward

		// Invert direction so gears don't grind each other (death)
		_leftSlave1.setInverted(InvertType.FollowMaster);
		_rightSlave1.setInverted(InvertType.FollowMaster);
		System.out.println("This is Arcade Drive using Arbitrary Feed Forward.");

		// Set all servo angles to 0

		leftServo.setAngle(0);
		rightServo.setAngle(0);
		_armServo.setPosition(0.25);
		
	}
	
	@Override
	public void teleopPeriodic() {	
	// Initiate trigger button for the color wheel
	boolean trigger = _gamepad.getRawButton(1);

	// Initiate top button for arm servo
	boolean top = _gamepad.getRawButton(2);

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

	

		/* Gamepad processing */
		// Going forwards and backwards by tracking joystick position
		double forward = -1 * _gamepad.getY();
		// Turning by tracking joystick twist angle
		double turn = _gamepad.getTwist();
		
		// Using dead band so minor joystick movements will not pass through and move the robot
		forward = Deadband(forward);
		// Doing the same for turn/twist
		turn = Deadband(turn);

		// If trigger button is pressed, spin the color wheel
		if (trigger == true){
			_colorWheel.set(1);
			//System.out.println("MOtor spinning");
		}else{
			_colorWheel.set(0);
		}

		// If top button is pressed, expand servo
		System.out.println(_armServo.getPosition());
		System.out.println(expand);
		
		if (top == true){
			

			if (_armServo.getPosition() == 1){
				
				_armServo.setPosition(0.25);
				
			}else if(_armServo.getPosition() == 0.25){
			
				_armServo.setPosition(1);
				
			}

			
		} 
		// If top button is pressed when servo is expanded, then it will retract
		

		// If climb button is pressed, switch servo state by subtracting angle from 90 degrees
		if (troughButton == true){
			troughServoAngle = 90 - troughServoAngle;
			leftServo.setAngle(troughServoAngle);
			rightServo.setAngle(troughServoAngle);
		}


		/* Arcade Drive using PercentOutput along with Arbitrary Feed Forward supplied by turn */
		_drive.arcadeDrive(turn, forward);
		//System.out.print(turn);
		//System.out.print(" ");
		//System.out.println(forward);

	}

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