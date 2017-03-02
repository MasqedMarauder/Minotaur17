package org.usfirst.frc.team1369.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

/**
 *
 */
public class ShiftSpeed extends Command {
	
	private final Mode mode;
	private boolean isFinished;
	
	public ShiftSpeed(Mode mode) {
		requires(Robot.speedShift);
		this.mode = mode;
	}

	@Override
	protected void initialize() {
		isFinished = false;
	}

	@Override
	protected void execute() {
		Robot.speedShift.set(mode);
		isFinished = true;
	}

	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void end() {
		//Close servo
		//Stop shooter motor
	}

	@Override
	protected void interrupted() {
		//Close servo
		//Stop shooter motor
	}
}
