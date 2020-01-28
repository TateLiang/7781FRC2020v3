package frc.robot;

// Import servo library
import edu.wpi.first.wpilibj.Servo;

public class Function_Climb {

    // Servo for arm, PWM 0
	Servo _armServo = new Servo(0);
	// Left servo for trough, PWM 1
	Servo leftServo = new Servo(1);
	// Right servo for trough, PWM 2
	Servo rightServo = new Servo(2);
}