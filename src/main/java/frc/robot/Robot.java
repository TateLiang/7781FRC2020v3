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






public class Robot extends TimedRobot{
	int targetColor;
	

	/* DEFINING FUNCTIONS */
	Function_Drive driveTrain = new Function_Drive();
	Function_Climb climb = new Function_Climb();
	Function_Intake intake = new Function_Intake();
	Function_Wheel wheel = new Function_Wheel();
	Function_Spin spin = new Function_Spin();

	Joystick _gamepad = new Joystick(0);
	
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture("epic",0);
		driveTrain.driveSetup();	
	}
	
	@Override
	public void teleopInit(){
		climb.armServoSetup();
		intake.troughServoSetup();
		wheel.colourSensorSetup();
		spin.spinSetUp();
		
	}
	
	@Override
	public void teleopPeriodic() {	
		
		/*---- GAMEPAD ----*/
		
		boolean topButton = _gamepad.getRawButton(5); // Initiate top button for arm servo
		boolean troughButton = _gamepad.getRawButton(5); // Initiate button 1 (left botton on top) for ball trough
		
		// arm
		boolean pull = _gamepad.getRawButton(1);
		boolean release = _gamepad.getRawButton(2);
		
		// color wheel backup
		boolean backUp1 = _gamepad.getRawButton(7);
		boolean backUp2 = _gamepad.getRawButton(8);

		// color wheel rotation control
		boolean colorWheelOn = _gamepad.getRawButton(3);
		boolean colorWheelOff = _gamepad.getRawButton(4);

		// color wheel color control
		boolean colorControlOn = _gamepad.getRawButton(5);
		boolean colorControlOff = _gamepad.getRawButton(6);

		//select color
		boolean redButton = _gamepad.getRawButton(9);
		boolean greenButton = _gamepad.getRawButton(10);
		boolean blueButton = _gamepad.getRawButton(11);
		boolean yellowButton = _gamepad.getRawButton(12);
		

		// sensitivity control
		double sensitivity = 1-( _gamepad.getThrottle() + 1)/2;


		/*---- DRIVE ----*/
		double forward = -1 * _gamepad.getY(); // Going forwards and backwards by tracking joystick position
		double turn = _gamepad.getTwist(); // Turning by tracking joystick twist angle
		// Using deadband so minor joystick movements will not pass through and move the robot
		forward = Deadband(forward);
		turn = Deadband(turn);
		forward = sensitivity*forward;
		turn = sensitivity*turn;
		//System.out.println(forward+turn);

		/* Arcade Drive using PercentOutput along with Arbitrary Feed Forward supplied by turn */
		driveTrain.drivePeriodic(turn, forward);

		/*---- COLOUR WHEEL ----*/
		
		if (redButton){
			targetColor = 0;
		} else if(greenButton){
			targetColor = 1;
		} else if(blueButton){
			targetColor = 2;
		} else if (yellowButton){
			targetColor = 3;
		} 
		
		String colorDetected = wheel.colourSensorPeriodic();
		if (colorWheelOn){
			wheel.rotationControlPeriodic("start");
		} else if (colorWheelOff){
			wheel.rotationControlPeriodic("stop");
		} else if (colorControlOn){
			wheel.positionControlPeriodic("start",-1);
		}
		else{
			wheel.rotationControlPeriodic(colorDetected);
			wheel.positionControlPeriodic(colorDetected,targetColor);
		}
		
		

		// If trigger button is pressed, spin the color wheel
		if (backUp1){
			spin.spinMotor(-0.1);
		}else if(backUp2){
			spin.spinMotor(0.1);
		}else {
			spin.spinMotor(0);
		}

		/*---- CLIMB ----*/
		// Toggle servo states
		if (topButton){
			climb.toggleArmServo();
		} 

		// Rope pull or release
		if (pull){
			climb.pullArm(0.4);
		} else if(release){
			climb.pullArm(-0.4);
		} else{
			climb.pullArm(0);
		}
		
		/*---- INTAKE & TROUGH ----*/
		// If trough button is pressed, switch servo state by subtracting angle from 90 degrees
		if (troughButton){
			intake.toggleTroughServo();
		}

		//System.out.println("sensitivity"+sensitivity);
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