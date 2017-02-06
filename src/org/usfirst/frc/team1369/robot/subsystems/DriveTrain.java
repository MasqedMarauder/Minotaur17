package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem implements Constants {

	private Joystick gamepad = RobotMap.gamepad;
	
	private CANTalon leftTalon = RobotMap.masterLeft;
	private CANTalon rightTalon = RobotMap.masterRight;
	
	//a = master
	//b = middle
	//c = other
	public void setupTalon(CANTalon a, CANTalon b, CANTalon c, boolean reversed) {
		a.reverseOutput(reversed);
		b.setControlMode(CANTalon.TalonControlMode.Follower.value);
		b.set(a.getDeviceID());
		b.reverseOutput(!reversed);
		
		c.setControlMode(CANTalon.TalonControlMode.Follower.value);
		c.set(a.getDeviceID());
	}
	
	public void configTalon(CANTalon a, boolean reversed) {
		a.setEncPosition(0);
		a.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		a.reverseSensor(reversed);
		
		a.configNominalOutputVoltage(+NOMINAL_OUTPUT_VOLTAGE,-NOMINAL_OUTPUT_VOLTAGE);
		a.configPeakOutputVoltage(+PEAK_OUTPUT_VOLTAGE, -PEAK_OUTPUT_VOLTAGE);
		
		a.configEncoderCodesPerRev(PPR);
		
		a.setAllowableClosedLoopErr(0);
		a.setProfile(0);
		a.setF(0.0);
		a.setP(0.1);
		a.setI(0);
		a.setD(0);
	}
	
	public DriveTrain() {
		//setup the slaves 
		setupTalon(leftTalon, RobotMap.slaveLeftMiddle, RobotMap.slaveLeft, true); // left side reversed
		setupTalon(rightTalon, RobotMap.slaveRightMiddle, RobotMap.slaveRight, false); // right side not reversed
		
		configTalon(leftTalon, true); // left side reversed
		configTalon(rightTalon, false); // right side reversed
	
	}
	
	public void teleop() {
		/*
		 * joystick should control the masters
		 * will set the velocity 
		 */
		
		double l_stick = gamepad.getY();
		double r_stick = gamepad.getThrottle();

		if(!Robot.scalerShift.isScalerMode())
		{ 
			//leftTalon.set(!deadband(l_stick) ? l_stick : 0);
			//rightTalon.set(!deadband(r_stick) ? r_stick : 0);
			
			
			this.stickCode(leftTalon, l_stick, l_stick);
			this.stickCode(rightTalon, r_stick, -r_stick);
		}
		else 
		{
			/*
			 * the scaler is engaged
			 * the robot should not be able to turn
			 */
			//for safety, there will be no code for now, Varun.
			//for safety, there will be code to stop the robot, Cheenar.

			this.stickCode(leftTalon, 0, 0);
			this.stickCode(rightTalon, 0, 0);
		}
	}
	
	/**
	 * talon 
	 * @param t talon 
	 * @param h hold var
	 * @param j joystick
	 * @param s speed
	 */
	public void stickCode(CANTalon t, double j, double s) {
		if(!deadband(j)) {
			//figure out the encoder shit later
			//resetEncoders();
			
			setControlMode(TalonControlMode.PercentVbus);
			t.set(s);  //we are using speed but might switch to percent vbus
		}
		else {
			setControlMode(TalonControlMode.PercentVbus);
			t.set(0);
		}
	}
	
	public boolean deadband(double input) {
		return Math.abs(input) < 15;
	}
	
	public void setControlMode(TalonControlMode mode) {
		if(leftTalon.getControlMode() != mode) 
			leftTalon.changeControlMode(mode);
		if(rightTalon.getControlMode() != mode) 
			rightTalon.changeControlMode(mode);
	}
	
	public void resetEncoders() {
		leftTalon.set(0);
		rightTalon.set(0);
		leftTalon.setEncPosition(0);
		rightTalon.setEncPosition(0);
	}
	
	public void drivePosition(double target) {
		setControlMode(TalonControlMode.Position);
		leftTalon.set(target);
		rightTalon.set(target);
	}
	
	public void driveVbus(double targetRPM) {
		setControlMode(TalonControlMode.PercentVbus);
		leftTalon.set(targetRPM);
		rightTalon.set(targetRPM);
	}
	
	public void driveVelocity(double targetRPM){
		setControlMode(TalonControlMode.Speed);
		leftTalon.set(targetRPM);
		rightTalon.set(targetRPM);
	}
	
	public void driveDistance(double distanceInches){
		setControlMode(TalonControlMode.Position);
		resetEncoders();
		double rotations = distanceInches/(Math.PI*WHEEL_DIA);
		leftTalon.set(rotations);
		rightTalon.set(rotations);

	}
	
	

	@Override
	protected void initDefaultCommand() {
		
	}
	
}
