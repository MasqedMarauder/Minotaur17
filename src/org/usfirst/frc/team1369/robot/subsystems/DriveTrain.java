package org.usfirst.frc.team1369.robot.subsystems;

import java.util.Set;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem implements Constants, Section {

	private final CANTalon leftTalon, rightTalon;
	private final CANTalon leftMiddle, rightMiddle;
	private final CANTalon leftSlave, rightSlave;
	private final ADXRS450_Gyro gyro;

	public static class AutoInterruptedException extends Exception{}

	public CANTalon getLeftTalon() {
		return this.leftTalon;
	}

	public CANTalon getRightTalon() {
		return this.rightTalon;
	}

	public enum Direction {
		FORWARD(+1.0), BACKWARD(-1.0), CLOCKWISE(+1.0), COUNTERCLOCKWISE(-1.0);
		public final double value;

		Direction(double value) {
			this.value = value;
		}
	}

	public enum TeleopDriveModes {
		TANK_DRIVE, NEED_4_SPEED
	}

	private TeleopDriveModes driveMode = TeleopDriveModes.NEED_4_SPEED;
	private int clicksRemaining;

	public DriveTrain() {
		leftTalon = new CANTalon(masterLeftPort);
		leftMiddle = new CANTalon(slaveLeftMidPort);
		leftSlave = new CANTalon(slaveLeftPort);

		rightTalon = new CANTalon(masterRightPort);
		rightMiddle = new CANTalon(slaveRightMidPort);
		rightSlave = new CANTalon(slaveRightPort);

		gyro = new ADXRS450_Gyro(gyroPort);

		setupSlaves(leftTalon, leftMiddle, leftSlave, true);
		setupSlaves(rightTalon, rightMiddle, rightSlave, false);

		configTalon(leftTalon, true);
		configTalon(rightTalon, false);
	}

	@Override
	public void reset() {
		leftTalon.set(0);
		rightTalon.set(0);
	}

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

		master.setF(kfDriveTrainVbus);
		master.setP(kpDriveTrainVbus);
		master.setI(kiDriveTrainVbus);
		master.setD(kdDriveTrainVbus);
	}

	public void setControlMode(TalonControlMode mode) {
		if (leftTalon.getControlMode() != mode)
			leftTalon.changeControlMode(mode);
		if (rightTalon.getControlMode() != mode)
			rightTalon.changeControlMode(mode);
	}

	public void setLeftTarget(double target) {
		leftTalon.set(target);
	}

	public void setRightTarget(double target) {
		rightTalon.set(target);
	}

	public void setTarget(double left, double right) {
		setLeftTarget(left);
		setRightTarget(right);
	}

	public void setTarget(double target) {
		setTarget(target, target);
	}

	public void stopDrive() {
		setControlMode(TalonControlMode.PercentVbus);
		setTarget(0);
	}

	public void resetEncoders() {
		stopDrive();
		leftTalon.setEncPosition(0);
		rightTalon.setEncPosition(0);
		try {
			Thread.sleep(500);
		} 
		catch (Exception e) {
			
		}
	}

	public void resetGyro() {
		gyro.reset();
		try {
			Thread.sleep(500);
		} catch (Exception e) {
		}
	}

	public double getGyroAngle() {
		return gyro.getAngle();
	}

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
		this.setControlMode(TalonControlMode.Speed);
		if (left == 0 && right == 0) {
			this.setControlMode(TalonControlMode.PercentVbus);
			this.leftTalon.set(0);
			this.rightTalon.set(0);
		} else {
			this.leftTalon.set(left);
			this.rightTalon.set(right);
		}
		// driveInMode(TalonControlMode.Speed, left, right);
	}

	public void driveVelocity(double target) {
		driveVelocity(target, target);
	}

	public void driveDistance(double distInches) {
		resetEncoders();
		double poop = distInches * CLICKS_PER_INCH;
		this.driveInMode(TalonControlMode.Position, -poop, poop);
		// driveInMode(TalonControlMode.Position, distInches * CLICKS_PER_INCH);
	}

	private double clip(double value, double min, double max) {
		if (value < min)
			value = min;
		if (value > max)
			value = max;
		return value;
	}

	public void moveByDistance(double inches, Direction direction, double speed) {
		resetEncoders();

		int targetClicks = (int) (inches * CLICKS_PER_INCH);
		int clicksRemaining;
		double inchesRemaining;
		double power;
		double p_gain = 0.3;

		do {
			clicksRemaining = targetClicks - Math.abs(rightTalon.getEncPosition());
			inchesRemaining = clicksRemaining / CLICKS_PER_INCH;
			power = direction.value * speed * inchesRemaining * p_gain;
			setTarget(power);
			if (Robot.isTeleop)
				break;
		} while (inchesRemaining > 0.5);
		stopDrive();
	}

	public double inchesToApporvas(double inches) {
		return inches / (4 * Math.PI);
	}

	public boolean memeMove(double inches, int speed, int allowableError) throws AutoInterruptedException {
		inches = inches * 256 / (4 * Math.PI);

		SmartDashboard.putNumber("Laft Target", inches);
		
		resetEncoders();
		Utils.sleep(1000);
		
		setControlMode(TalonControlMode.PercentVbus);
		
		double error = 0;
		double previousError = 0;
		
		while(Math.abs(leftTalon.getEncPosition()) < inches) {
			error = inches - leftTalon.getEncPosition();
			
			SmartDashboard.putNumber("Laft Error", error);
			
			leftTalon.set(-error * 0.03);
			rightTalon.set(error * 0.03);
			
			previousError = error;
			
			if(Robot.isDisabled) {
				throw new AutoInterruptedException();
			}
		}
		
		this.stopDrive();
		
		double kP = 0.3;
		
		
		
		return true;
	}
	
	public boolean moveInches(double inches, Direction direction, int allowableError) throws AutoInterruptedException{
		//resetEncoders();
		setControlMode(TalonControlMode.Position);
		leftTalon.setProfile(1);
		rightTalon.setProfile(1);
		Utils.sleep(100);
		
		//0.25 - Varun's P
		
		/*
		leftTalon.setP(SmartDashboard.getNumber("Left PID Constant P", kpDriveTrainVbus));
		leftTalon.setI(SmartDashboard.getNumber("Left PID Constant I", kiDriveTrainVbus));
		leftTalon.setD(SmartDashboard.getNumber("Left PID Constant D", kdDriveTrainVbus));
		leftTalon.setF(SmartDashboard.getNumber("Left PID Constant F", kfDriveTrainVbus));

		rightTalon.setP(SmartDashboard.getNumber("Right PID Constant P", kpDriveTrainVbus));
		rightTalon.setI(SmartDashboard.getNumber("Right PID Constant I", kiDriveTrainVbus));
		rightTalon.setD(SmartDashboard.getNumber("Right PID Constant D", kdDriveTrainVbus));
		rightTalon.setF(SmartDashboard.getNumber("Right PID Constant F", kfDriveTrainVbus));
		*/

		setTarget(inchesToApporvas(inches) * direction.value);
		int count = 0;
		do {
			setTarget(inchesToApporvas(inches) * direction.value);
			
			if(Robot.isDisabled){
				throw new AutoInterruptedException();
			}
		} while (Math.abs(leftTalon.getError()) > allowableError || Math.abs((rightTalon.getError())) > allowableError) ;
		
		System.out.println("Broke out of the loop");
		
		leftTalon.setProfile(0);
		rightTalon.setProfile(0);
		stopPositionDrive();
		return true;
	}

	private void stopPositionDrive() {
		SmartDashboard.putString("Stopped", "dude");
		stopDrive();
		leftTalon.setProfile(0);
		leftTalon.setProfile(0);

	}
	public void turnP(double degrees, Direction direction, double speed, double allowableError) throws AutoInterruptedException{
		resetGyro();
		setControlMode(TalonControlMode.PercentVbus);
		double error;
		double power;
		double kp = 0.04;
		double integral = 0;
		double ki = 0.00005;
		double prevTime = System.currentTimeMillis();

		double sTime = System.currentTimeMillis();
		
		int count = 0;
		do {
			
			if(Robot.isTeleop || Robot.isDisabled){
				throw new AutoInterruptedException();
			}
			
			else{
				error = direction.value * degrees - gyro.getAngle();
				integral += error * (System.currentTimeMillis() - prevTime);
				prevTime = System.currentTimeMillis();
				power = kp * error + ki * integral;
  				power = clip(power, -speed, +speed);
				setTarget(power, power);
				
				if(Math.abs(error) < allowableError){
					count++;
				}
				else{
					count = 0;
				}
			}
			System.out.println("turning: " + degrees + ":" + direction.toString() + ":" + speed);
		} while (count < 500);
		System.out.println("Completed");
		stopDrive();
	}

	public void turnP(double degrees, Direction direction, double speed, double allowableError, double killTime) throws AutoInterruptedException{
		resetGyro();
		setControlMode(TalonControlMode.PercentVbus);
		double error;
		double power;
		double kp = 0.05;
		double integral = 0;
		double ki = 0.00003;
		double prevTime = System.currentTimeMillis();

		double sTime = System.currentTimeMillis();
		
		int count = 0;
		System.out.println("turning");
		do {
			
			if(Robot.isTeleop || Robot.isDisabled){
				throw new AutoInterruptedException();
			}
			
			else{
				error = direction.value * (Math.abs(degrees) > 60 ? 0 : Math.abs(degrees)) - gyro.getAngle();
				integral += error * (System.currentTimeMillis() - prevTime);
				prevTime = System.currentTimeMillis();
				power = kp * error + ki * integral;
  				power = clip(power, -speed, +speed);
				setTarget(power, power);
				
				if(Math.abs(error) < allowableError){
					count++;
				}
				else{
					count = 0;
				}
			}
		} while (count < 500 && System.currentTimeMillis() - sTime < killTime);
		System.out.println("Completed");
		stopDrive();
	}

	public static boolean robotStop = false;

	public void turnPID(double degrees, Direction direction, double speed) {
		resetGyro();

		double error = direction.value * degrees - gyro.getAngle();
		double prevError = error;
		double integral = 0;
		double derivative;
		double power;
		long dt;
		long prevTime = System.nanoTime();

		double kp = 0.025;
		double ki = 0.0;
		double kd = 0.0;
		double iDamper = 1.0;

		do {
			dt = System.nanoTime() - prevTime;
			prevTime = System.nanoTime();
			error = direction.value * degrees - gyro.getAngle();
			integral = iDamper * integral + error * dt;
			derivative = (error - prevError) / dt;
			prevError = error;
			power = kp * error + ki * integral + kd * derivative;
			power = clip(power, -speed, +speed);
			setTarget(-power, +power);
		} while (Math.abs(error) > 0.5);
		stopDrive();
	}

	public void moveByGyroDistance(double inches, Direction direction, double speed, double allowableError, double timeKill) throws AutoInterruptedException {
	stopDrive();
	}
		
		
	public void moveByGyroDistance(double inches, Direction direction, double speed) throws Exception {	
	
		resetEncoders();
		resetGyro();
		
		setControlMode(TalonControlMode.Speed);

		int targetClicks = (int) (inches * CLICKS_PER_INCH);
		int clicksRemaining;
		double inchesRemaining;
		double angularError;
		double dIntegral = 0;
		double powerAdjustment;
		double power;
		double leftPower;
		double rightPower;
		double maxPower;
		long dt;
		long prevTime = System.nanoTime();

		double p_gain = 0.05;
		double i_gain = 0.00005;
		double kp = 0.05;
		//double ki = 0.00005;
		//double kd = 0.0;
		//double iDamper = 1.0;
		
		//int count = 0;
		//double time = System.currentTimeMillis();
		System.out.println("moving");
		do {
			
			System.out.println("David 1");
			Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
			for(int i = 0; i < threadArray.length; i++) {
				System.out.println(threadArray[i]);
			}
			System.out.println("isDisabled = " + Robot.isDisabled);
			if(Robot.isDisabled || Robot.isTeleop) {
				System.out.println("David 2");
				throw new Exception();
			}
			
			clicksRemaining = targetClicks - Math.abs(rightTalon.getEncPosition());
			inchesRemaining = clicksRemaining / CLICKS_PER_INCH;
			
			dt = System.nanoTime() - prevTime;
			prevTime = System.nanoTime();
			
			dIntegral += inchesRemaining * dt;
			
			power = inchesRemaining * p_gain + dIntegral * i_gain;
			power *= speed * direction.value;
			power = clip(power, -speed, speed);
			
			angularError = -gyro.getAngle();
			System.out.println("Angular Error: " + angularError);
			powerAdjustment = kp * angularError;
			powerAdjustment *= direction.value;
			powerAdjustment = clip(powerAdjustment, -1.0, +1.0);
			
			if(direction == Direction.BACKWARD){
				leftPower = power + powerAdjustment;
				rightPower = power - powerAdjustment;
			}
			else{
				leftPower = power - powerAdjustment;
				rightPower = power + powerAdjustment;
			}
			maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
			if (maxPower > 1.0) {
				leftPower /= maxPower;
				rightPower /= maxPower;
			}
			
			/*
				angularIntegral = iDamper * angularIntegral + angularError * dt;
				angularDerivative = (angularError - prevAngularError) / dt;
				prevAngularError = angularError;
				powerAdjustment = kp * angularError + ki * angularIntegral + kd * angularDerivative;
			*/
			setTarget(1250 * leftPower, 1250 * rightPower);
			/*if(inchesRemaining < allowableError){
				count ++;
			}
			else{
				count = 0;
			}*/
			
			
			
		} while(inchesRemaining > 3 || angularError > 2);
		//while (count < 500 && System.currentTimeMillis() - time < timeKill);
		System.out.println("finished");
		stopDrive();
	}

	public double deadband(double input) {
		return Math.abs(input) < 0.15 ? 0.0 : input;
	}

	public void teleop(Joystick gamepad) {
		double left_y = deadband(gamepad.getRawAxis(LEFT_Y_AXIS));
		double right_y = deadband(gamepad.getRawAxis(RIGHT_Y_AXIS));
		double right_x = deadband(gamepad.getRawAxis(RIGHT_X_AXIS));

		if (gamepad.getRawButton(BTN_BACK))
			driveMode = TeleopDriveModes.TANK_DRIVE;
		if (gamepad.getRawButton(BTN_START))
			driveMode = TeleopDriveModes.NEED_4_SPEED;

		if (!Robot.scalerShift.isScaleMode()) {
			// this.leftTalon.set(left_y * 500);
			// this.rightTalon.set(right_y * 500);
			double leftPower = left_y - right_x;
			double rightPower = left_y + right_x;

			if (Math.abs(leftPower) > 1) {
				leftPower /= Math.abs(leftPower);
				rightPower /= Math.abs(leftPower);
			}
			if (Math.abs(rightPower) > 1) {
				leftPower /= Math.abs(rightPower);
				rightPower /= Math.abs(rightPower);
			}
			driveVelocity(1250 * leftPower, 1250 * rightPower);
			/*
			 * switch(driveMode) { case TANK_DRIVE: //driveVbus(left_y * 0.8,
			 * right_y * 0.8); driveVelocity(-1250 * left_y, 1250 * right_y);
			 * break; case NEED_4_SPEED: double leftPower = left_y - right_x;
			 * double rightPower = left_y + right_x;
			 * 
			 * if (Math.abs(leftPower) > 1) { leftPower /= Math.abs(leftPower);
			 * rightPower /= Math.abs(leftPower); } if (Math.abs(rightPower) >
			 * 1) { leftPower /= Math.abs(rightPower); rightPower /=
			 * Math.abs(rightPower); }
			 * 
			 * 
			 * 
			 * driveVelocity(1250 * leftPower, 1250 * rightPower); break; }
			 */
		} else {
			driveVbus(-left_y, left_y);
		}
	}

	@Override
	protected void initDefaultCommand() {
		System.out.println("Minotaur DriveTrain");
	}

}
