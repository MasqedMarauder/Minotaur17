package org.usfirst.frc.team1369.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1369.robot.Robot;

import org.usfirst.frc.team1369.robot.subsystems.Intake.Mode;

/**
 *
 */
public class IntakeFuel extends Command {
	
	private final Mode mode;
	
	public IntakeFuel(Mode mode) {
		this.mode = mode;
		requires(Robot.intake);
	}

	@Override
	protected void initialize() {
		Robot.intake.set(mode);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
	
}
