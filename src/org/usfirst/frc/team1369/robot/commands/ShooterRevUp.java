package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.subsystems.Shootaur.Mode;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterRevUp extends Command {

	public ShooterRevUp(){
		this.requires(Robot.shootaur);
	}
	
	@Override
	protected void initialize() {
		Robot.shootaur.set(Mode.IN);
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
		Robot.shootaur.set(Mode.STOP);
	}
	
}
