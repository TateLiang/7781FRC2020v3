package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class Function_Intake {
    // Left servo for trough, PWM 1
	Servo leftServo = new Servo(1);
	// Right servo for trough, PWM 2
    Servo rightServo = new Servo(2);

    // Servo states
	private int troughServoAngle = 0;
    
    public void troughServoSetup() {
        // Set all servo angles to 0
		leftServo.setAngle(0);
		rightServo.setAngle(0);
    }

    public void toggleTroughServo() {
        troughServoAngle = 90 - troughServoAngle;
		leftServo.setAngle(troughServoAngle);
		rightServo.setAngle(troughServoAngle);
    }
}