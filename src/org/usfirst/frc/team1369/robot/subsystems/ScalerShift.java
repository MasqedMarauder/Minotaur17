package org.usfirst.frc.team1369.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ScalerShift extends MinoDoubleSol {

	public enum Mode {
		DRIVE (Value.kForward),
		SCALE (Value.kReverse),
		DISABLED (Value.kOff);
		
		private final Value value;
		Mode(Value val) {value = val;}
		public Value value() {return value;}
	}
	
	public ScalerShift() {
		super(scalerShiftForChan, scalerShiftRevChan);
		
		set(Mode.DRIVE);
		smartDashboard();
	}
	
	public void reset() {
		super.reset();
		set(Mode.DRIVE);
		smartDashboard();
	}
	
	public void set(Mode mode) {set(mode.value);}
	
	public boolean isDriveMode() {return getValue() == Mode.DRIVE.value;}
	public boolean isScaleMode() {return getValue() == Mode.SCALE.value;}
	public boolean isDisabledMode() {return getValue() == Mode.DISABLED.value;}
	
	public void smartDashboard() {
		SmartDashboard.putString("ScalerShift", isDriveMode() ? "Drive" : "Scale");
	}

	public void initDefaultCommand() {}

}
