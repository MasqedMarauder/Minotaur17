package org.usfirst.frc.team1369.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain;

import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class MoveByGyroDistance extends Command implements Constants {
	
	private static final double p_gain = 0.05;
	private static final double i_gain = 0.00005;
	private static final double kp = 0.05;
	
	private final Direction direction;
	private final double speed;
	private final int targetClicks;
	
	private int clicksRemaining;
	private double inchesRemaining;
	private double angularError;
	private double distIntegral;
	private double power, maxPower;
	private double leftPower, rightPower;
	private double powerAdjustment;
	private long dt, prevTime;
	
	public MoveByGyroDistance(double inches, Direction direction, double speed) {
		requires(Robot.driveTrain);
		targetClicks = (int)(inches * CLICKS_PER_INCH);
		this.direction = direction;
		this.speed = speed;
	}

	
	@Override
	protected void initialize() {
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.resetGyro();
		Robot.driveTrain.setControlMode(TalonControlMode.Speed);
		
		distIntegral = 0;
		prevTime = System.nanoTime();
	}

	@Override
	protected void execute() {
			clicksRemaining = targetClicks - Math.abs(Robot.driveTrain.getRightEncPosition());
			inchesRemaining = clicksRemaining / CLICKS_PER_INCH;
			
			dt = System.nanoTime() - prevTime;
			prevTime = System.nanoTime();
			
			distIntegral += inchesRemaining * dt;
			
			power = p_gain * inchesRemaining + i_gain * distIntegral;
			power *= speed * direction.value;
			power = DriveTrain.clip(power, -speed, speed);
			
			angularError = -Robot.driveTrain.getGyroAngle();
			System.out.println("Angular Error: " + angularError);
			powerAdjustment = kp * angularError;
			
			leftPower = power - powerAdjustment;
			rightPower = power + powerAdjustment;
			
			maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
			if (maxPower > 1.0) {
				leftPower /= maxPower;
				rightPower /= maxPower;
			}
			
			Robot.driveTrain.setTarget(1250 * leftPower, 1250 * rightPower);
			
			/*
				angularIntegral = iDamper * angularIntegral + angularError * dt;
				angularDerivative = (angularError - prevAngularError) / dt;
				prevAngularError = angularError;
				powerAdjustment = kp * angularError + ki * angularIntegral + kd * angularDerivative;
			*/
	}

	@Override
	protected boolean isFinished() {
		return inchesRemaining > 3 || angularError > 2;
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
