package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.commands.Shoot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shootaur extends Subsystem implements Constants  {
	
	public Servo indexer;
	
	public enum IndexerMode {
		OPEN (indexerOpen),
		CLOSED (indexerClosed);
		
		private final int position;
		IndexerMode(int p) {position = p;}
		public int position() {return position;}
	}
	
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
		indexer = new Servo(indexerPort);
		
		set(Mode.STOP);
	}
	
	public void set(Mode mode) {
		shootaurI.set(-mode.power());
		shootaurII.set(mode.power());
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new Shoot());
	}

}
