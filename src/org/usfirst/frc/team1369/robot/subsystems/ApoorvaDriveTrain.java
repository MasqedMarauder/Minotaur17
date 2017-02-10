package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.ApoorvaConstants;
import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.ApoorvaMap;
import org.usfirst.frc.team1369.robot.commands.ApoorvaAutoUtils;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ApoorvaDriveTrain extends Subsystem implements ApoorvaConstants{
	
	
	private CANTalon leftTalon = ApoorvaMap.masterLeft;
	private CANTalon rightTalon = ApoorvaMap.masterRight;
	
	public static ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	
	private int mode = 0;
	
	//a = master
	//b = middle
	//c = other
	public void setupTalon(CANTalon a, CANTalon b, CANTalon c) {
		b.setControlMode(CANTalon.TalonControlMode.Follower.value);
		b.set(a.getDeviceID());
		b.reverseOutput(true);
		
		c.setControlMode(CANTalon.TalonControlMode.Follower.value);
		c.set(a.getDeviceID());
		
		a.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	}
	
	public void configTalon(CANTalon a) {
		a.setEncPosition(0);
		a.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		a.reverseSensor(false);
		
		a.configNominalOutputVoltage(+0.0f,-0.0f);
		a.configPeakOutputVoltage(+10f, -10f);
		
		a.setVoltageCompensationRampRate(24);
		
		a.configEncoderCodesPerRev(256);
		
		a.setAllowableClosedLoopErr(0);
		a.setProfile(0);
		
		 a.setF(10);
		a.setP(0);
		a.setI(0);
		a.setD(0);
		/*a.setF(0);
		a.setP(0);
		a.setI(0);
		a.setD(0);*/	
	}
		
	public static double driveP = 0;
	public static double driveI = 0;
	public static double driveD = 0;
	
	public ApoorvaDriveTrain() {
		gyro.calibrate();
		//setup the slaves 
		setupTalon(leftTalon, ApoorvaMap.slaveLeftMiddle, ApoorvaMap.slaveLeft); // left side reversed
		setupTalon(rightTalon, ApoorvaMap.slaveRightMiddle, ApoorvaMap.slaveRight); // right side not reversed
		
		configTalon(leftTalon); // left side reversed
		configTalon(rightTalon); // right side reversed
rightTalon.reverseSensor(true);
	}
	
	
	
	public void teleop() {
		/*
		 * joystick should control the masters
		 * will set the velocity 
		 */
	
		System.out.println(leftTalon.getEncVelocity());
		System.out.println(rightTalon.getEncVelocity());
		
		
		double l_stick = ApoorvaMap.gamepad.getY();
		double r_stick = ApoorvaMap.gamepad.getThrottle();

		if(!Robot.scalerShift.isScalerMode())
		{ 
			
			 this.setControlMode(TalonControlMode.PercentVbus);
			
			if(mode == 0){
				leftTalon.set(-1 * l_stick);
				rightTalon.set(r_stick);
			}
			
			else if(mode == 1){
				double forward = ApoorvaMap.gamepad.getY();
				double turn = -1 * ApoorvaMap.gamepad.getZ();
				
				double left = forward + turn;
				double right = forward - turn;
				if(left > 1){
					left /= left;
					right /= left;
				}
				else if(right > 1){
					left /= right;
					right /= right;
				}
				leftTalon.set(-1 * left);
				rightTalon.set(right);
			}
			
			if(ApoorvaMap.gamepad.getPOV()== 0){
				mode = 0;
			}
			else if(ApoorvaMap.gamepad.getPOV() == 180){
				mode = 1;
				
			}
			
			 
			//this.setControlMode(TalonControlMode.PercentVbus);
			//leftTalon.set(-1 * l_stick);
			//rightTalon.set(r_stick);
		}
		else {
			
			
			this.setControlMode(TalonControlMode.PercentVbus);
			leftTalon.set(-1 * l_stick);
			rightTalon.set(l_stick);
			 
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
			//figure out the encoder sh*t later
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
		return Math.abs(input) < .15;
	}
	
	public void breakThisRobot(){
		driveVbus(0);
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
	
	public void driveVelocity(double targetRPM, double targetRPMIITheRebirth){
		setControlMode(TalonControlMode.Speed);
		leftTalon.set(-targetRPM);
		rightTalon.set(targetRPMIITheRebirth);
		//rightTalon.set(0);
	}
	
	public void driveDistance(double distanceInches){
		setControlMode(TalonControlMode.Position);
		resetEncoders();
		
		double rotations = distanceInches/(Math.PI * WHEEL_DIA);
		leftTalon.set(-rotations);
		rightTalon.set(rotations);
	}
	
	public void turn(int degrees) {
		setControlMode(TalonControlMode.PercentVbus);
		resetEncoders();
		gyro.reset();
		ApoorvaAutoUtils.sleeper(500);
		double error;
		double kp = 0.01;
		do {
			error = gyro.getAngle() - degrees;
			leftTalon.set(kp * error);
			rightTalon.set(-kp * error);
		} while (Math.abs(error) > 1);
	}
	
	

	@Override
	protected void initDefaultCommand() {
		System.out.println("kill me please");
	}
	
}
