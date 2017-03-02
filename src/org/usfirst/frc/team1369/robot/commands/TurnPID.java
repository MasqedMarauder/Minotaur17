package org.usfirst.frc.team1369.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain;

import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class TurnPID extends Command implements Constants {
	
	private static final double kp = 0.05;
	private static final double ki = 0.00003;
	private static final double kd = 0.0;
	private static final double iDamper = 1.0;
	
	private final double targetAngle;
	private final double speed;
	
	private double error, prevError;
	private double integral, derivative;
	private double power;
	private long dt, prevTime;
	
	public TurnPID(double degrees, Direction direction, double speed) {
		requires(Robot.driveTrain);
		targetAngle = degrees * direction.value;
		this.speed = speed;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.resetGyro();
		Robot.driveTrain.setControlMode(TalonControlMode.PercentVbus);
		
		error = targetAngle - Robot.driveTrain.getGyroAngle();
		prevError = error;
		integral = 0;
		prevTime = System.nanoTime();
	}

	@Override
	protected void execute() {
		dt = System.nanoTime() - prevTime;
		prevTime = System.nanoTime();
		
		error = targetAngle - Robot.driveTrain.getGyroAngle();
		integral = iDamper * integral + error * dt;
		derivative = (error - prevError) / dt;
		prevError = error;
		
		power = kp * error + ki * integral + kd * derivative;
		power = DriveTrain.clip(power, -speed, +speed);
		Robot.driveTrain.setTarget(-power, +power);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(error) > 1;
	}

	@Override
	protected void end() {
		Robot.driveTrain.stopDrive();
	}

	@Override
	protected void interrupted() {
		Robot.driveTrain.stopDrive();
	}
}
