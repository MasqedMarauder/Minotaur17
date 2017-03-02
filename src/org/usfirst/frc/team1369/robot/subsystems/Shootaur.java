package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Constants;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shootaur extends Subsystem implements Constants  {
	
	private final VictorSP shootaurI;
	private final VictorSP shootaurII;
	
	public enum Mode {
		IN (-0.75),
		OUT (+0.75),
		STOP (0);
		
		private final double power;
		Mode(double p) {power = p;}
		public double power() {return power;}
	}

	public Shootaur() {
		shootaurI = new VictorSP(shootaurIPort);
		shootaurII = new VictorSP(shootaurIIPort);
		set(Mode.STOP);
	}
	
	public void set(Mode mode) {
		shootaurI.set(-mode.power());
		shootaurII.set(mode.power());
	}
	
	public void initDefaultCommand() {
		System.out.println("Minotaur Shootaur");
	}

}
