package frc.robot;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

// Import servo library
import edu.wpi.first.wpilibj.Servo;

public class Function_Climb {

    // Servo for arm, PWM 0
	// Servo _armServo = new Servo(0);
	// Motor for the arm

	WPI_TalonSRX _pullUp = new WPI_TalonSRX(1);
	WPI_TalonSRX _raiseRobot = new WPI_TalonSRX(2);
	

	public void armServoSetup() {
		//_armServo.setPosition(0.25);
		_pullUp.configFactoryDefault();
		_raiseRobot.configFactoryDefault();
		
	}

	public void pullArm(double direction) {
		_pullUp.set(direction);
	} 
	public void raiseRobot(double direction){
		_raiseRobot.set(direction);
	}
}