package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Constants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem implements Constants {
	
	private final CANTalon leftTalon, rightTalon;
	private final CANTalon leftMiddle, rightMiddle;
	private final CANTalon leftSlave, rightSlave;
	private final ADXRS450_Gyro gyro;
	
	public enum Direction {
        FORWARD (+1.0),
        BACKWARD (-1.0),
        CLOCKWISE (+1.0),
        COUNTERCLOCKWISE (-1.0);
        public final double value;
        Direction (double value) {this.value = value;}
    }
	
	public enum TeleopDriveModes {
		TANK_DRIVE,
		NEED_4_SPEED
	} private TeleopDriveModes driveMode = TeleopDriveModes.TANK_DRIVE;

	public DriveTrain() {
		leftTalon	= new CANTalon(masterLeftPort);
		leftMiddle	= new CANTalon(slaveLeftMidPort);
		leftSlave	= new CANTalon(slaveLeftPort);
		
		rightTalon	= new CANTalon(masterRightPort);
		rightMiddle	= new CANTalon(slaveRightMidPort);
		rightSlave	= new CANTalon(slaveRightPort);
		
		gyro = new ADXRS450_Gyro(gyroPort);
		
		setupSlaves(leftTalon, leftMiddle, leftSlave, true);
		setupSlaves(rightTalon, rightMiddle, rightSlave, false);
		
		configTalon(leftTalon, true);
		configTalon(rightTalon, false);
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
		
		master.setF(kfDriveTrainVbus);
		master.setP(kpDriveTrainVbus);
		master.setI(kiDriveTrainVbus);
		master.setD(kdDriveTrainVbus);
	}
	
	public void setControlMode(TalonControlMode mode) {
		if (leftTalon.getControlMode() != mode) leftTalon.changeControlMode(mode);
		if (rightTalon.getControlMode() != mode) rightTalon.changeControlMode(mode);
	}
	
	public void setLeftTarget(double target) {leftTalon.set(target);}
	public void setRightTarget(double target) {rightTalon.set(target);}
	public void setTarget(double left, double right) {setLeftTarget(left); setRightTarget(right);}
	public void setTarget(double target) {setTarget(target, target);}
	public void stopDrive() {setControlMode(TalonControlMode.PercentVbus); setTarget(0);}
	
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
	
	public void driveInMode(TalonControlMode mode, double left, double right) {
		setControlMode(mode);
		setTarget(left, right);
	}
	
	public void driveInMode(TalonControlMode mode, double target) {driveInMode(mode, target, target);}
	
	public void drivePosition(double left, double right) {driveInMode(TalonControlMode.Position, left, right);}
	public void drivePosition(double target) {drivePosition(target, target);}
	
	public void driveVbus(double left, double right) {driveInMode(TalonControlMode.PercentVbus, left, right);}
	public void driveVbus(double target) {driveVbus(target, target);}
	
	public void driveVelocity(double left, double right) {driveInMode(TalonControlMode.Speed, left, right);}
	public void driveVelocity(double target) {driveVelocity(target, target);}
	
	public void driveDistance(double distInches) {
		resetEncoders();
		driveInMode(TalonControlMode.Position, distInches * CLICKS_PER_INCH);
	}
	
	private double clip(double value, double min, double max) {
		if (value < min) value = min;
		if (value > max) value = max;
		return value;
	}
	
	public void moveByDistance(double inches, Direction direction, double speed) {
        resetEncoders();
		
        int targetClicks = (int)(inches * CLICKS_PER_INCH);
        int clicksRemaining;
        double inchesRemaining;
        double power;
        double p_gain = 0.05;

        do {
            clicksRemaining = targetClicks - Math.abs(rightTalon.getEncPosition());
            inchesRemaining = clicksRemaining / CLICKS_PER_INCH;
            power = direction.value * speed * inchesRemaining * p_gain;
            setTarget(power);
        } while (inchesRemaining > 0.5);
        stopDrive();
    }
	
	public void turnP(double degrees, Direction direction, double speed) {
        resetGyro();
		
		double error;
        double power;
        double kp = 0.025;

        do {
            error = direction.value * degrees - gyro.getAngle();
            power = kp * error;
            power = clip(power, -speed, +speed);
            setTarget(-power, +power);
        } while (Math.abs(error) > 0.5);
        stopDrive();
    }
	
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
        } while (Math.abs(error) > 0.5 );
        stopDrive();
    }
	
	public void moveByGyroDistance(double inches, Direction direction, double speed) {
        resetEncoders();
		resetGyro();
		
        int targetClicks = (int)(inches * CLICKS_PER_INCH);
        int clicksRemaining;
        double inchesRemaining;
        double angularError;
        double prevAngularError = 0;
        double angularIntegral = 0;
        double angularDerivative;
        double powerAdjustment;
        double power;
        double leftPower;
        double rightPower;
        double maxPower;
        long dt;
        long prevTime = System.nanoTime();
        
        double p_gain = 0.05;
        double kp = 0.05;
        double ki = 0.0;
        double kd = 0.0;
        double iDamper = 1.0;

        do {
        	clicksRemaining = targetClicks - Math.abs(rightTalon.getEncPosition());
            inchesRemaining = clicksRemaining / CLICKS_PER_INCH;
            power = direction.value * speed * inchesRemaining * p_gain;
            power = clip(power, -1.0, +1.0);
            dt = System.nanoTime() - prevTime;
            prevTime = System.nanoTime();
            angularError = -gyro.getAngle();
            angularIntegral = iDamper * angularIntegral + angularError * dt;
            angularDerivative = (angularError - prevAngularError) / dt;
            prevAngularError = angularError;
            powerAdjustment = kp * angularError + ki * angularIntegral + kd * angularDerivative;
            powerAdjustment = clip(powerAdjustment, -1.0, +1.0);
            powerAdjustment *= direction.value;
            leftPower = power - powerAdjustment;
            rightPower = power + powerAdjustment;
            maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
            if (maxPower > 1.0) {
                leftPower /= maxPower;
                rightPower /= maxPower;
            }
            setTarget(leftPower, rightPower);
        } while (inchesRemaining > 0.5 || Math.abs(angularError) > 0.5);
        stopDrive();
    }
	
	public double deadband(double input) {return Math.abs(input) < 0.15 ? 0.0 : input;}
	
	public void teleop(Joystick gamepad) {
		double left_y = deadband(gamepad.getRawAxis(LEFT_Y_AXIS));
		double right_y = deadband(gamepad.getRawAxis(RIGHT_Y_AXIS));
		double right_x = deadband(gamepad.getRawAxis(RIGHT_X_AXIS));
		
		switch(driveMode) {
			case TANK_DRIVE:
				driveVbus(left_y, right_y);
				//driveVelocity(500 * l_stick, 500 * r_stick);
				break;
			case NEED_4_SPEED:
				double leftPower = left_y - right_x;
				double rightPower = left_y + right_x;
				if(Math.abs(leftPower) > 1){
					leftPower /= leftPower;
					rightPower /= leftPower;
				}
				if(Math.abs(rightPower) > 1){
					leftPower /= rightPower;
					rightPower /= rightPower;
				}
				driveVbus(leftPower, rightPower);
				break;
		}
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		System.out.println("Minotaur DriveTrain");
	}

}
