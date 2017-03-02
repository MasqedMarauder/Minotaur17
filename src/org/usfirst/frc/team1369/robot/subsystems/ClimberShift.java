package org.usfirst.frc.team1369.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimberShift extends MinoDoubleSol {

	public enum Mode {
		DRIVE (Value.kForward),
		CLIMB (Value.kReverse),
		DISABLED (Value.kOff),
		TOGGLE;
		
		private final Value value;
		Mode(Value val) {value = val;}
		Mode() {value = null;}
		public Value value() {return value;}
	}
	
	public ClimberShift() {
		super(scalerShiftForChan, scalerShiftRevChan);
		
		set(Mode.DRIVE);
		smartDashboard();
	}
	
	public void reset() {
		super.reset();
		set(Mode.DRIVE);
		smartDashboard();
	}
	
	public void set(Mode mode) {
		if (mode == Mode.TOGGLE) toggle();
		else set(mode.value);
	}
	
	public boolean isDriveMode() {return getValue() == Mode.DRIVE.value;}
	public boolean isScaleMode() {return getValue() == Mode.CLIMB.value;}
	public boolean isDisabledMode() {return getValue() == Mode.DISABLED.value;}
	
	public void smartDashboard() {
		SmartDashboard.putString("ScalerShift", isDriveMode() ? "Drive" : "Scale");
	}

	public void initDefaultCommand() {}

}
