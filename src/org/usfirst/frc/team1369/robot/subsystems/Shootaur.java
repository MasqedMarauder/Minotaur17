package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.subsystems.Intake.Mode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;

public class Shootaur  implements Constants, Section  {
	
	private final VictorSP shootaur;
	public enum Mode {
		IN (-0.75),
		OUT (+0.75),
		STOP (0);
		
		private final double power;
		
		Mode(double p) {power = p;}
		
		public double power() {return power;}
	}

	public Shootaur() {
		shootaur = new VictorSP(shootaurPort);
		set(Mode.IN);
	}
	
	public void set(Mode mode) {shootaur.set(mode.power());}
	
	public void teleop(Joystick gamepad) {
		if (gamepad.getRawButton(BTN_Y)) {set(Mode.OUT);}
		else {set(Mode.STOP);}
	}
	
	protected void initDefaultCommand() {
		System.out.println("Minotaur Shootaur");
	}

	@Override
	public void reset() {
		set(Mode.STOP);
	}

}
