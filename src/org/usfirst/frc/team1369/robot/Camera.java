package org.usfirst.frc.team1369.robot;

import org.usfirst.frc.team1369.robot.Constants;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class Camera {

	private static final int IMG_WIDTH = 640;
	private static final int IMG_HEIGHT = 480;
	private static final double FOCAL_ANG = 64.6;
	
	public VisionThread visionThread;
	private double centerX = 888;
	private final Object imgLock = new Object();
	private double wBoundRect = 0.0;
	
	private UsbCamera cam;
	
	public Camera(){
		cam = CameraServer.getInstance().startAutomaticCapture();
		cam.setResolution(IMG_WIDTH, IMG_HEIGHT);
		
	}

	public void start(){
		
		setExposure(1);
		visionThread = new VisionThread(cam, new GRIP(), gRIP -> {
			SmartDashboard.putString("Pipeline", "Starting it now");
			try {
			if (!gRIP.filterContoursOutput().isEmpty()) {
				SmartDashboard.putString("Pipeline", "In it now" + gRIP.filterContoursOutput());
				Rect r1 = Imgproc.boundingRect(gRIP.filterContoursOutput().get(0));
				Rect r2 = Imgproc.boundingRect(gRIP.filterContoursOutput().get(1));
				
				SmartDashboard.putNumber("r1 x", r1.x);
				SmartDashboard.putNumber("r2.x", r2.x);
				synchronized (imgLock) {
					centerX = ((r1.x + (r1.width / 2)) + (r2.x + (r2.width / 2)))/2;
					if(r1.x < r2.x){
						wBoundRect = Math.abs((r2.x + r2.width)-r1.x);
					}
					else{
						wBoundRect = Math.abs((r1.x + r1.width)-r2.x);
					}
					
				}
				
			}
			else{
				synchronized(imgLock){
					centerX = -999;
					wBoundRect = -999;
				}
			} 
			}
			catch (Exception e)
			{
				
			}
			
			//SmartDashboard.putData(CameraServer.getInstance().putVideo("Video", IMG_WIDTH, IMG_HEIGHT));
		});
		
		visionThread.start();
	}
	
	public double getDistance(){
		
		double wBoundRect;

		synchronized (imgLock) {
			wBoundRect = this.wBoundRect;
		}
		
		return Constants.VISION_TARG_WIDTH * IMG_WIDTH / (2 * wBoundRect * Math.tan(Math.toRadians(FOCAL_ANG)));
	}
	
	public double getAngle(){
		double centerX;
		double wBoundRect;
		
		synchronized (imgLock) {
			centerX = this.centerX;
			wBoundRect = this.wBoundRect;
		}
		
		//double dPixels = getDistance() * wBoundRect / Constants.VISION_TARG_WIDTH;
		double dPixels = Robot.rangeSensor.getDistance() * wBoundRect / Constants.VISION_TARG_WIDTH;
		SmartDashboard.putNumber("CenterX", centerX);
		SmartDashboard.putNumber("wBoundRect", wBoundRect);
		return Math.toDegrees(Math.atan((centerX - (IMG_WIDTH / 2))/dPixels));
	}
	
	
	public void setExposure(int exp)
	{

		cam.setExposureManual(exp);
	}
	
}
