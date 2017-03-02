package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Constants;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem implements Constants {
	
	private final VictorSP intake;
	
	public enum Mode {
		IN (-1),
		OUT (+1),
		STOP (0);
		
		private final int power;
		Mode(int p) {power = p;}
		public int power() {return power;}
	}

	public Intake() {
		intake = new VictorSP(intakePort);
		set(Mode.STOP);
	}
	
	public void set(Mode mode) {intake.set(mode.power());}
	
	public void initDefaultCommand() {
		System.out.println("Minotaur Intake");
	}

}
