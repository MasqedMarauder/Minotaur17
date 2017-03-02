package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.MinoRangeSensor;
import org.usfirst.frc.team1369.robot.RobotMap;
import org.usfirst.frc.team1369.robot.commands.TeleopKajDrive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem implements Constants {
	
	private final CANTalon leftTalon, rightTalon;
	private final CANTalon leftMiddle, rightMiddle;
	private final CANTalon leftSlave, rightSlave;
	
	private final ADXRS450_Gyro gyro;
	private final MinoRangeSensor ultraSonic;
	
	public DriveTrain getInstance() {return this;}
	
	public CANTalon getLeftTalon() {return leftTalon;}
	public CANTalon getRightTalon() {return rightTalon;}
	public ADXRS450_Gyro getGyro() {return gyro;}
	public MinoRangeSensor getRangeSensor() {return ultraSonic;}
	public int getRightEncPosition() {return rightTalon.getEncPosition();}
	public double getUltraSonicInches() {return ultraSonic.getDistance();}

	public DriveTrain() {
		leftTalon = new CANTalon(RobotMap.masterLeftPort);
		leftMiddle = new CANTalon(RobotMap.slaveLeftMidPort);
		leftSlave = new CANTalon(RobotMap.slaveLeftPort);

		rightTalon = new CANTalon(RobotMap.masterRightPort);
		rightMiddle = new CANTalon(RobotMap.slaveRightMidPort);
		rightSlave = new CANTalon(RobotMap.slaveRightPort);

		gyro = new ADXRS450_Gyro(RobotMap.gyroPort);
		ultraSonic = new MinoRangeSensor(RobotMap.ultraSonicPort);

		setupSlaves(leftTalon, leftMiddle, leftSlave, true);
		setupSlaves(rightTalon, rightMiddle, rightSlave, false);

		configTalon(leftTalon, true);
		configTalon(rightTalon, false);
	}
	
	public void initDefaultCommand() {setDefaultCommand(new TeleopKajDrive());}
	
	private void setupSlaves(CANTalon master, CANTalon middle, CANTalon slave, boolean reversed) {
		middle.changeControlMode(TalonControlMode.Follower);
		middle.set(master.getDeviceID());

		slave.changeControlMode(TalonControlMode.Follower);
		slave.set(master.getDeviceID());

		master.reverseOutput(reversed);
		middle.reverseOutput(!reversed);
		slave.reverseOutput(reversed);
	}

	private void configTalon(CANTalon master, boolean reversed) {
		master.changeControlMode(TalonControlMode.PercentVbus);
		master.set(0);

		master.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		master.reverseSensor(reversed);

		master.configNominalOutputVoltage(+NOMINAL_OUTPUT_VOLTAGE, -NOMINAL_OUTPUT_VOLTAGE);
		master.configPeakOutputVoltage(+PEAK_OUTPUT_VOLTAGE, -PEAK_OUTPUT_VOLTAGE);

		master.configEncoderCodesPerRev(ENCODER_PPR);

		master.setAllowableClosedLoopErr(0);
		master.setProfile(0);

		master.setVoltageRampRate(48);

		master.setF(kfDriveTrainVel);
		master.setP(kpDriveTrainVel);
		master.setI(kiDriveTrainVel);
		master.setD(kdDriveTrainVel);
	}
	
	public void setControlMode(TalonControlMode mode) {
		if (leftTalon.getControlMode() != mode) leftTalon.changeControlMode(mode);
		if (rightTalon.getControlMode() != mode) rightTalon.changeControlMode(mode);
	}

	public void setLeftTarget(double target) {leftTalon.set(target);}
	public void setRightTarget(double target) {rightTalon.set(target);}

	public void setTarget(double left, double right) {
		setLeftTarget(left);
		setRightTarget(right);
	}

	public void setTarget(double target) {setTarget(target, target);}

	public void stopDrive() {
		setControlMode(TalonControlMode.PercentVbus);
		setTarget(0);
	}

	public void resetEncoders() {
		stopDrive();
		leftTalon.setEncPosition(0);
		rightTalon.setEncPosition(0);
		try {Thread.sleep(500);} catch (Exception e) {}
	}

	public void resetGyro() {
		gyro.reset();
		try {Thread.sleep(500);} catch (Exception e) {}
	}

	public double getGyroAngle() {return gyro.getAngle();}

	public void driveInMode(TalonControlMode mode, double left, double right) {
		setControlMode(mode);
		setTarget(left, right);
	}

	public void driveInMode(TalonControlMode mode, double target) {
		driveInMode(mode, target, target);
	}

	public void drivePosition(double left, double right) {
		driveInMode(TalonControlMode.Position, left, right);
	}

	public void drivePosition(double target) {
		drivePosition(target, target);
	}

	public void driveVbus(double left, double right) {
		driveInMode(TalonControlMode.PercentVbus, left, right);
	}

	public void driveVbus(double target) {
		driveVbus(target, target);
	}

	public void driveVelocity(double left, double right) {
		if (left == 0 && right == 0) stopDrive();
		else driveInMode(TalonControlMode.Speed, left, right);
	}

	public void driveVelocity(double target) {
		driveVelocity(target, target);
	}
	
	//static methods
	public static double clip(double value, double min, double max) {
		if (value < min) value = min;
		if (value > max) value = max;
		return value;
	}
	
}
