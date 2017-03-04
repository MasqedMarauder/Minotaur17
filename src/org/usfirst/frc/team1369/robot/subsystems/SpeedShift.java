package org.usfirst.frc.team1369.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

public class SpeedShift extends MinoDoubleSol {
	
	public enum Mode {
		SPEED (Value.kReverse),
		TORQUE (Value.kForward),
		DISABLED (Value.kOff);
		
		private final Value value;
		
		Mode(Value val) {value = val;}
		
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
	
	public void set(Mode mode) {set(mode.value);}
	
	public boolean isSpeedMode() {return getValue() == Mode.SPEED.value;}
	public boolean isTorqueMode() {return getValue() == Mode.TORQUE.value;}
	public boolean isDisabledMode() {return getValue() == Mode.DISABLED.value;}
	
	public void smartDashboard() {
		SmartDashboard.putString("SpeedShift", (isSpeedMode() ? "Speed" : "Torque"));
	}

	public void teleop(Joystick gamepad) {
		if (gamepad.getRawButton(BTN_RB)) {
			toggle();
		}
	}

}
