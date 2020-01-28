package frc.robot;

// Phoenix library
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// Import motor control
//import static org.junit.Assume.assumeTrue;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.*;

public class Function_Drive {
    /* MOTORS*/
	// Setting ID for motors
	// Drive train motors
	// Talons are masters; Victors are slaves

    // Set ID for front left motor as Talon SRX 3
    WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(3);
    // Set ID for front right motor as Talon SRX 6
    WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(6);
    // Set ID for left slave motor as Victor SPX 4
    WPI_VictorSPX _leftSlave1 = new WPI_VictorSPX(4);
    // Set ID for right slave motor as Victor SPX 5
    WPI_VictorSPX _rightSlave1 = new WPI_VictorSPX(5);

    WPI_TalonSRX _colorWheel = new WPI_TalonSRX(1); // Color wheel motor

    DifferentialDrive _drive = new DifferentialDrive(_frontLeftMotor, _frontRightMotor);


    public void driveSetup() {
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
        
        /* Configure output direction */
		_frontLeftMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
		_frontRightMotor.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward

		// Invert direction so gears don't grind each other (death)
		_leftSlave1.setInverted(InvertType.FollowMaster);
		_rightSlave1.setInverted(InvertType.FollowMaster);
		System.out.println("This is Arcade Drive using Arbitrary Feed Forward.");
    }
    public void drivePeriodic(double turn, double forward) {
        _drive.arcadeDrive(turn, forward);
    }
}