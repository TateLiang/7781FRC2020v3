// Robot.java: The main file of the robot
// - handles joystick controls

// Author: Tate Liang
// CO-Author: Bob Xiong
// Date: Jan 22, 2020

package frc.robot;

/* LIBRARY IMPORTS */
//Import functions (written in same directory)
import frc.robot.Function_Drive;
import frc.robot.Function_Climb;
import frc.robot.Function_Intake;
import frc.robot.Function_Wheel;
import frc.robot.Function_Spin;

// Camera server library
import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.Joystick; // Import Joystick module

import edu.wpi.first.wpilibj.TimedRobot; //import timed robot

import java.awt.event.KeyEvent;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;






public class Robot extends TimedRobot{
	WPI_VictorSPX shootMotor = new WPI_VictorSPX(11);
	long servoTime = System.currentTimeMillis();
	
	long autonomousTime = System.currentTimeMillis();

	/* DEFINING FUNCTIONS */
	Function_Drive driveTrain = new Function_Drive();
	Function_Climb climb = new Function_Climb();
	Function_Intake intake = new Function_Intake();
	Function_Wheel wheel = new Function_Wheel();
	Function_Spin spin = new Function_Spin();

	Joystick _gamepad = new Joystick(0);

	int c = 0;

	int targetColor;
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture("epic",0);
		driveTrain.driveSetup();	
		autonomousTime = System.currentTimeMillis();
	}
	@Override
	public void autonomousInit() {
		driveTrain.driveSetup();
		//intake.spinIn(true);
	}

	@Override 
	public void autonomousPeriodic(){
		if(c<150){
			driveTrain.drivePeriodic(0, 0.5);
		}
		c+=1;
		//System.out.println((System.currentTimeMillis()-autonomousTime));
		//intake.spinIn(false);
	}


	@Override
	public void teleopInit(){
		shootMotor.configFactoryDefault();
		climb.armServoSetup();
		intake.troughServoSetup();
		wheel.colourSensorSetup();
		spin.spinSetUp();
		
	}
	
	@Override
	public void teleopPeriodic() {	
		
		
		
		/*---- GAMEPAD ----*/
		int POV = _gamepad.getPOV();

		// trough servo
		boolean changeIntakeState = _gamepad.getRawButton(1);

		// exit color wheel mode
		boolean colorAutomaticOff = _gamepad.getRawButton(2);

		// color wheel rotation control
		boolean rotationControlOn = _gamepad.getRawButton(3);

		// color wheel color control
		boolean positionControlOn = _gamepad.getRawButton(4);

		// pulley arm up
		boolean pullUp = _gamepad.getRawButton(5);
		
		// pulley arm down
		boolean pullDown = _gamepad.getRawButton(6);

		// pull hook down, aka robot up
		boolean raiseUp = _gamepad.getRawButton(7);

		// release hook
		boolean robotDown = _gamepad.getRawButton(8);

		//select color
		boolean redButton = _gamepad.getRawButton(9);
		boolean greenButton = _gamepad.getRawButton(10);
		boolean blueButton = _gamepad.getRawButton(11);
		boolean yellowButton = _gamepad.getRawButton(12);
		
		// sensitivity control
		double sensitivity = 1-( _gamepad.getThrottle() + 1)/2;
		
		
		/*---- DRIVE ----*/
		double forward = -1 * _gamepad.getY(); // Going forwards and backwards by tracking joystick position
		 // Turning by tracking joystick twist angle
		double turn = _gamepad.getX();
		//double turn = (turn1+turn2)/2;
		// Using deadband so minor joystick movements will not pass through and move the robot
		forward = Deadband(forward);
		turn = Deadband(turn);
		forward = sensitivity*forward;
		turn = sensitivity*turn;

		/* Arcade Drive using PercentOutput along with Arbitrary Feed Forward supplied by turn */
		driveTrain.drivePeriodic(turn, forward);

		/*---- COLOUR WHEEL ----*/
		
		// Set color
		if(redButton&&greenButton){
			shootMotor.set(0.6);
			System.out.println("SHOOTING");

		} else if (redButton){
			targetColor = 0;
		} else if(greenButton){
			targetColor = 1;
		} else if(blueButton){
			targetColor = 2;
		} else if (yellowButton){
			targetColor = 3;
		} else{
			shootMotor.set(-0.25);
			System.out.println("no SHOOTING");
		}
		
		// Find color
		String colorDetected = wheel.colourSensorPeriodic();
		
		// Different modes
		if (rotationControlOn){
			wheel.rotationControlPeriodic("start");
		} else if (positionControlOn){
			wheel.positionControlPeriodic("start",-1);
		} else if (colorAutomaticOff){
			wheel.rotationControlPeriodic("stop");
			wheel.positionControlPeriodic("stop", -1);
			System.out.println("stoppping!!!!");
		} else{
			wheel.rotationControlPeriodic(colorDetected);
			wheel.positionControlPeriodic(colorDetected,targetColor);
		}
		
		intake.spinIn(changeIntakeState);
		

	
		
		wheel.spinServo(POV);

		

		
		
		
	
		/*---- CLIMB ----*/

		// Rope pull or release
		if (pullUp){
			climb.pullArm(1);
		} else if(pullDown){
			climb.pullArm(-1);
		} else if(raiseUp){
			climb.raiseRobot(1);
		} else if(robotDown){
			climb.raiseRobot(-1);
		} else {
			climb.pullArm(0);
			climb.raiseRobot(0);
		}
		

		/*---- INTAKE & TROUGH ----*/
		// Trough door
		/*
		intake.changeState(changeServoState);
*/
	}

	/* UTILITY FUNCTIONS */
	/** Deadband 5 percent, used on the gamepad */
	double Deadband(double value) {
		/* Upper deadband */
		if (value >= +0.05) 
			return value;
		/* Lower deadband */
		if (value <= -0.05)
			return value;
		/* Outside deadband */
		return 0;
	}
}