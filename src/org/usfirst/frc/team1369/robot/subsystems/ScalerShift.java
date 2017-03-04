package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;

import edu.wpi.first.wpilibj.Joystick;
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
		shouldExecute = true;
	}
	
	public void set(Mode mode) {set(mode.value);}
	
	public boolean isDriveMode() {return getValue() == Mode.DRIVE.value;}
	public boolean isScaleMode() {return getValue() == Mode.SCALE.value;}
	public boolean isDisabledMode() {return getValue() == Mode.DISABLED.value;}
	
	public void smartDashboard() {
		SmartDashboard.putString("ScalerShift", isDriveMode() ? "Drive" : "Scale");
	}

	public void teleop(Joystick gamepad) {
		if (gamepad.getRawButton(BTN_START)) {
			if(shouldExecute) {
				shouldExecute = false;
				new Thread(new Runnable() {
					public void run() {
						set(isDriveMode() ? Mode.SCALE : Mode.DRIVE);
						Robot.speedShift.set(isScaleMode() ? SpeedShift.Mode.TORQUE : SpeedShift.Mode.SPEED);
						Utils.sleep(1000);
						shouldExecute = true;
					}
				}).start();
			}
		}
	}
	
	public boolean shouldExecute = true;

}
