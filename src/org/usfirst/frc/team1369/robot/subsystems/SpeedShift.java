package org.usfirst.frc.team1369.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedShift extends MinoDoubleSol {
	
	public enum Mode {
		SPEED (Value.kReverse),
		TORQUE (Value.kForward),
		DISABLED (Value.kOff),
		TOGGLE;
		
		private final Value value;
		Mode(Value val) {value = val;}
		Mode() {value = null;}
		public Value value() {return value;}
	}
	
	public SpeedShift() {
		super(speedShiftForChan, speedShiftRevChan);
		set(Mode.SPEED);
		smartDashboard();
	}
	
	public void reset() {
		super.reset();
		set(Mode.SPEED);
		smartDashboard();
	}
	
	public void set(Mode mode) {
		if (mode == Mode.TOGGLE) toggle();
		else set(mode.value);
	}
	
	public boolean isSpeedMode() {return getValue() == Mode.SPEED.value;}
	public boolean isTorqueMode() {return getValue() == Mode.TORQUE.value;}
	public boolean isDisabledMode() {return getValue() == Mode.DISABLED.value;}
	
	public void smartDashboard() {
		SmartDashboard.putString("SpeedShift", (isSpeedMode() ? "Speed" : "Torque"));
	}
	
	public void initDefaultCommand() {};

}
