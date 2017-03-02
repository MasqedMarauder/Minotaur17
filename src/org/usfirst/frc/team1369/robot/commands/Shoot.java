package org.usfirst.frc.team1369.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1369.robot.subsystems.Shootaur;

/**
 *
 */
public class Shoot extends CommandGroup {
	
	public Shoot() {
		this.addSequential(new ShooterRevUp());
		this.addSequential(new SleepCommand());
		this.addSequential(new IndexerToggle(Shootaur.IndexerMode.OPEN));
	}

}
