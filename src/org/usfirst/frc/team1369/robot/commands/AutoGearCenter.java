package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Constants.Direction;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber.Mode;
import org.usfirst.frc.team1369.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoGearCenter extends CommandGroup{
	public AutoGearCenter(){
		this.addSequential(new MoveByGyroDistance(Robot.driveTrain.getUltraSonicInches() - 30, Direction.BACKWARD, 1), 3);
		this.addSequential(new MoveByGyroDistance(Robot.driveTrain.getUltraSonicInches() - 2, Direction.BACKWARD, 1), 3);
		this.addSequential(new ModGearGrabber(Mode.OPEN));
		this.addSequential(new MoveByGyroDistance(20, Direction.FORWARD, 1), 3);
	}
}
