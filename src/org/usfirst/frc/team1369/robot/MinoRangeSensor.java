package org.usfirst.frc.team1369.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class MinoRangeSensor extends AnalogInput implements Constants {

	public MinoRangeSensor(int channel) {
		super(channel);
	}

	public double getDistance() {
		double inches = (getMillimetres() / 25.4) - 6.5;
		return inches > 0 ? inches : -1;
	}

	public double getMillimetres() {
		return getVoltage() / .000996;// VOLTS_PER_MM;
	}

}
