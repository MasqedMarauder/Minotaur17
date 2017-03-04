package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.subsystems.Intake.Mode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;

public class Shootaur implements Constants, Section {

	private final VictorSP shootaurI;
	private final VictorSP shootaurII;

	public enum Mode {
		IN(-0.75), OUT(+0.75), STOP(0);

		private final double power;

		Mode(double p) {
			power = p;
		}

		public double power() {
			return power;
		}
	}

	public Shootaur() {
		shootaurI = new VictorSP(shootaurIPort);
		shootaurII = new VictorSP(shootaurIIPort);
		set(Mode.STOP);
		Robot.indexerServo.setAngle(50);
	}

	public void set(Mode mode) {
		shootaurI.set(-mode.power());
		shootaurII.set(mode.power());
	}

	long previousTime = 0;
	
	public void teleop(Joystick gamepad) {
		if (gamepad.getRawAxis(3) > 0.9) {
			set(Mode.OUT);
			if(System.currentTimeMillis() - previousTime > 3000) {
				Robot.indexerServo.setAngle(30);
			}
		} // RTrigger
		else {
			previousTime = System.currentTimeMillis();
			set(Mode.STOP);
			Robot.indexerServo.setAngle(50);
		}
	}

	protected void initDefaultCommand() {
		System.out.println("Minotaur Shootaur");
	}

	@Override
	public void reset() {
		set(Mode.STOP);
	}

}
