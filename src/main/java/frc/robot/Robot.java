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

// Camera server library
import edu.wpi.first.wpilibj.CameraServer;


import edu.wpi.first.wpilibj.Joystick; // Import Joystick module

import edu.wpi.first.wpilibj.TimedRobot; //import timed robot

public class Robot extends TimedRobot{

	/* DEFINING FUNCTIONS */
	Function_Drive driveTrain = new Function_Drive();
	Function_Climb climb = new Function_Climb();
	Function_Intake intake = new Function_Intake();
	Function_Wheel wheel = new Function_Wheel();

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
	}
	
	@Override
	public void teleopPeriodic() {	
		
		/*---- GAMEPAD ----*/
		boolean triggerButton = _gamepad.getRawButton(1); // Initiate trigger button for the color wheel
		boolean topButton = _gamepad.getRawButton(5); // Initiate top button for arm servo
		boolean troughButton = _gamepad.getRawButton(5); // Initiate button 1 (left botton on top) for ball trough
		boolean otherDirection = _gamepad.getRawButton(2);
		boolean pull = _gamepad.getRawButton(3);
		boolean release = _gamepad.getRawButton(4);
		//boolean sensitivity = _gamepad.getThrottle();


		/*---- DRIVE ----*/
		double forward = -1 * _gamepad.getY(); // Going forwards and backwards by tracking joystick position
		double turn = _gamepad.getTwist(); // Turning by tracking joystick twist angle
		// Using deadband so minor joystick movements will not pass through and move the robot
		forward = Deadband(forward);
		turn = Deadband(turn);

		/* Arcade Drive using PercentOutput along with Arbitrary Feed Forward supplied by turn */
		driveTrain.drivePeriodic(turn, forward);

		/*---- COLOUR WHEEL ----*/
		wheel.colourSensorPeriodic();
		// If trigger button is pressed, spin the color wheel
		if (triggerButton){
			wheel.spinColourWheel(-0.4);
		}else if(otherDirection){
			wheel.spinColourWheel(0.4);
		}else {
			wheel.spinColourWheel(0);
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