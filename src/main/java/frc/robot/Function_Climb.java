package frc.robot;

// Import servo library
import edu.wpi.first.wpilibj.Servo;

public class Function_Climb {

    // Servo for arm, PWM 0
	Servo _armServo = new Servo(0);

	public void armServoSetup() {
		_armServo.setPosition(0.25);
	}

	public void toggleArmServo() {
		System.out.println(_armServo.getPosition());

		if (_armServo.getPosition() == 1){
			_armServo.setPosition(0.25);
		}else if(_armServo.getPosition() == 0.25){
			_armServo.setPosition(1);
		}
	}
}