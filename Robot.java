package org.usfirst.frc.team6831.robot;

import java.util.Objects;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
	
	Encoder eHook;
	Encoder eLeft;
	Encoder eRight;
	
	boolean hook = false;
	
	DigitalInput limitSwitchBot, limitSwitchTop;
	AHRS ahrs;
	private Timer timer = new Timer();

	
	public Talon leftDrive = new Talon(0);
	public Talon rightDrive = new Talon(1);
	public Talon grab = new Talon(5);
	public Talon lifty = new Talon(4);
	public int automode;
	public int scaleSide;
	public int switchSide;
	
	public Talon hooky = new Talon(2);
	public Talon winchitty = new Talon(3);
	
	double wiggleTime = 100;
	double maxSpd = 0.75;
	int twist = 2; //2 inits to twisty drive, 0 is leany drive
	int joysticks = 1;
	String station;
	int stationTwo;
	double initialAngle;
	
	public int control;
	public int functionControl = 0;
	double FunctionAngle;
	double initTime;
	double initDistance;
	double functionAngleCorrection;
	double angleDerivativeCorrection;
	double angleDerivative;
	double angleCorrection;
	double functionPositionCorrection;
	double positionCorrection;
	double positionDerivativeCorrection;
	double positionDerivative;
	double functionAccelerationCorrection;
	double accelerationCorrection;
	double DSacceleration;
	double dir = 1;
	double stopTime;
	
	int pos = 0;
	int goal = 0;
	double wiggletime;
	
	double grabDir;
	
	double liftDir = 0;
	
	double angle = 0;
	double anglemultiplier = 0;
	double lastangle = 0;
	
	//Acceleration Variables
	double strRequest;
	double strMax;
	double velRequest;
	double velPresent;
	double aStep = 0.05;
	double dStep = 0.025;
	
	public static Joystick stick1 = new Joystick(0);
	public static Joystick stick2 = new Joystick(1);

	@Override
	public void robotInit() {
		
		eHook = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		
		eLeft = new Encoder(2, 3, false, Encoder.EncodingType.k4X);

		eRight = new Encoder(4, 5, false, Encoder.EncodingType.k4X);
		
		limitSwitchBot = new DigitalInput(9);
		limitSwitchTop = new DigitalInput(8);
        ahrs = new AHRS(SPI.Port.kMXP);
    	ahrs.enableLogging(true);
    	hooky.setInverted(true);
		CameraServer.getInstance().startAutomaticCapture();
		SmartDashboard.putString("DB/String 0", "init");
	}

	@Override
	public void disabledInit() {
		SmartDashboard.putString("DB/String 0", "");
		SmartDashboard.putString("DB/String 1", "");
		SmartDashboard.putString("DB/String 2", "");
		SmartDashboard.putString("DB/String 3", "");
		SmartDashboard.putString("DB/String 4", "");
		SmartDashboard.putString("DB/String 5", "");
		SmartDashboard.putString("DB/String 6", "");
		SmartDashboard.putString("DB/String 7", "");
		SmartDashboard.putString("DB/String 8", "");
		SmartDashboard.putString("DB/String 9", "");
		
		
		timer.start();
	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putString("help", "Functional");
		eLeft.reset();
		eRight.reset();
		SmartDashboard.putNumber("oookookoko", eHook.getDistance());
		Scheduler.getInstance().run();
		station = SmartDashboard.getString("DB/String 0", "7");
		if(Objects.equals(station, "1")) {
			stationTwo = 1;
		}
		if(Objects.equals(station, "2")) {
			stationTwo = 2;
		}
		if(Objects.equals(station, "3")) {
			stationTwo = 3;
		}
		if(Objects.equals(station, "4")) {
			stationTwo = 4;
		}
		if(Objects.equals(station, "5")) {
			stationTwo = 5;
		}
		if(Objects.equals(station, "6")) {
			stationTwo = 6;
		}
		if(Objects.equals(station, "7")) {
			stationTwo = 7;
		}
		if(Objects.equals(station, "8")) {
			stationTwo = 8;
		}
		if(Objects.equals(station, "9")) {
			stationTwo = 9;
		}
		if(Objects.equals(station,  "10")) {
			stationTwo = 10;
		}
		if(stationTwo == 1) {
			SmartDashboard.putString("DB/String 1", "left switch");
		}
		if(stationTwo == 2) {
			SmartDashboard.putString("DB/String 1", "EAT PANT");
		}
		if(stationTwo == 3) {
			SmartDashboard.putString("DB/String 1", "right switch");
		}
		if(stationTwo == 4) {
			SmartDashboard.putString("DB/String 1", "right scale/switch");
		}
		if(stationTwo == 5) {
			SmartDashboard.putString("DB/String 1", "left scale/switch");
		}
		if(stationTwo == 6) {
			SmartDashboard.putString("DB/String 1", "new center");
		}
		if(stationTwo == 7) {
			SmartDashboard.putString("DB/String 1", "test encoder auto");
		}
		if(stationTwo == 8) {
			SmartDashboard.putString("DB/String 1", "Encoder Scale Left");
		}
		if(stationTwo == 9) {
			SmartDashboard.putString("DB/String 1", "Encoder Scale Right");
		}
		if(stationTwo == 10) {
			SmartDashboard.putString("DB/String 1", "Jeff wants three blocks");
		}
/*		Scheduler.getInstance().run();
		if (stick1.getRawButton(7)) pos = 0;
		if (stick1.getRawButton(9)) pos = 1;
		if (stick1.getRawButton(11)) pos = 2;
		if (stick1.getRawButton(8)) goal = 2;
		if (stick1.getRawButton(10)) goal = 1;
		if (stick1.getRawButton(12)) goal = 0;*/
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		updateGyro();
		timer.stop();
		timer.reset();
		timer.start();
		ahrs.reset();
		control = 1;
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
                if(gameData.length() > 0)
                {
		  if(gameData.charAt(0) == 'L')
		  {
			  switchSide = 1;
		  } else {
			  switchSide = 2;
		  }
		  if(gameData.charAt(1) == 'L')
		  {
			  scaleSide = 1;
		  } else {
			  scaleSide = 2;
		  }
                }
	}

	@Override
	public void autonomousPeriodic() {

		SmartDashboard.putNumber("switchSide", switchSide);
		SmartDashboard.putString("help me sing rappers delight?", "I said a hip, hop, a hippie, a hippie, a hip hip hop and we dont stop the rock until the bang bang boogis, say up jump the boogie to the rythm to the boogie the beat");
		SmartDashboard.putString("DB/String 2", Integer.toString(control));
		SmartDashboard.putString("DB/String 4", Integer.toString(functionControl));

		updateGyro();
		if(stationTwo == 1) {
			switch(control) {
			case 1:
				driveStraight(.4, 2.8);
				break;
			case 2:
				if(switchSide == 1) {
					turning(90);
				}
				break;
			case 3:
				lifty.set(-.6);
				driveStraight(0, 3);
				break;
			case 4:
				driveStraight(.2, 1);
				break;
			case 5:
				if(switchSide == 1) {
					grab.set(1);
				}
				driveStraight(0,1);
				break;
			case 6:
				grab.set(0);
				lifty.set(0);
				break;
			
			}
		} else if (stationTwo == 2){
				switch(control) {
				case 1:
					driveStraight(.2, 1);
					break;
				case 2:
					if(switchSide == 1) {
						turning(-50);
					}else {
						turning(50);
					}
					break;
				case 3:
					driveStraight(.4, 1.5);
					break;
				case 4:

					if(switchSide == 1) {
						turning(47);
					}else {
						turning(-47);
					}
					break;
				case 5:
					lifty.set(-.6);
					driveStraight(0, 2);
					break;
				case 6:
					driveStraight(.3, 1);
					break;
				case 7:
					grab.set(1);
					driveStraight(0, 2);
					break;
				case 8:
					grab.set(0);
					lifty.set(0);
					break;
			}

		} else if(stationTwo == 3){
			switch(control) {
			case 1:
				driveStraight(.4, 2.8);
				break;
			case 2:
				if(switchSide == 2) {
					turning(-90);
				}
				break;
			case 3:
				lifty.set(-.6);
				driveStraight(0, 3);
				break;
			case 4:
				driveStraight(.2, 1);
				break;
			case 5:
				if(switchSide == 2) {
					grab.set(1);
				}
				driveStraight(0,1);
				break;
			case 6: 
				grab.set(0);
				lifty.set(0);
				break;
			}
		}
		else if(stationTwo == 4) {
			if(scaleSide == 2) {
				switch(control) {
				case 1:
					driveStraight(.4, 5);
					if(timer.get()>4) {
						lifty.set(-1);
					}
					break;
				case 2:
					while(limitSwitchTop.get()) {
						lifty.set(-1);
					}
					lifty.set(-.1);
					control ++;
					break;
				case 3:
					if(scaleSide == 2) {
						turning(-45);
					}
					break;
				case 4:
					driveStraight(.2, 1);
					break;
				case 5:
					if(scaleSide == 2) {
						grab.set(1);
					}
					driveStraight(-.2,2);
					break;
				case 6: 
					grab.set(0);
					lifty.set(0);
					break;
				}
			}else {
				switch(control) {
				case 1:
					driveStraight(.4, 4);
				}
			}
			
		}		else if(stationTwo == 5) {
			if(scaleSide == 1) {
				switch(control) {
				case 1:
					driveStraight(.4, 5);
					if(timer.get()>4) {
						lifty.set(-1);
					}
					break;
				case 2:
					while(limitSwitchTop.get()) {
						lifty.set(-1);
					}
					lifty.set(-.1);
					control ++;
					break;
				case 3:
					if(switchSide == 1) {
						turning(45);
					}
					break;
				case 4:
					driveStraight(.2, 1);
					break;
				case 5:
					if(switchSide == 1) {
						grab.set(1);
					}
					driveStraight(-.2,2);
					break;
				case 6: 
					grab.set(0);
					lifty.set(0);
					break;
				}
			}else {//will if you see this comment 
				switch(control) {//just say something 
				case 1://weird to me i probably wont 
					driveStraight(.4, 4);//understand 
					break;//it but itll be cool
				}
			}
		} else if(stationTwo == 6) {
			switch(control) {
			case 1:
				driveStraight(-.3,.3);
				break;
			case 2:
				if(switchSide == 1) {;
					turning(-32);
				}else {
					turning(32);
				}
				break;
			case 3:
				driveStraight(-.6, 1.5);
				SmartDashboard.putNumber("DB/String 7", timer.get());
				break;
			}
		}else if(stationTwo == 7) {
			switch(control) {
			case 1:
				if(limitSwitchTop.get()) {
					lifty.set(-1);
				}else {
					control++;
					lifty.set(-.1);
				}
				break;
			}
		}else if(stationTwo == 8) {//encoder scale left
			if(scaleSide == 1) {
				switch(control) {
				case 1:
					driveWithEncoders(283, 1, 0);
					break;
				case 2:
					if(limitSwitchTop.get()) {
						lifty.set(-1);
					}else {
						control++;
						lifty.set(-.1);
					}
					break;
				case 3:
					turning(70);
					break;
				case 4:
					grab.set(1);
					driveStraight(0, 1);
					break;
				case 5:
					grab.set(0);
					driveStraight(-.3, 2);
					break;
				}
			}else {
				switch(control) {
				case 1:
					driveWithEncoders(216.23, 1, 0);
					break;
				case 2:
					turning(90);
					break;
				case 3:
					lifty.set(-.8);
					driveWithEncoders(188, .5, 90);
					//driveWithEncoders(101.44, .5, 90);
					break;
				case 4:
					if(limitSwitchTop.get()) {
						lifty.set(-1);
					}else {
						lifty.set(-.1);
					}
					turning(-90);
					break;
				case 5:
					if(limitSwitchTop.get()) {
						lifty.set(-1);
					}else {
						lifty.set(-.1);
					}
					driveWithEncoders(15, .4, 0);
					break;
				case 6:
					grab.set(.8);
					driveStraight(0,1);
					break;
				case 7:
					driveWithEncoders(-15, .4, 0);
					break;
				case 8:
					
					break;
				}
			}
		}else if(stationTwo == 9) {//encoder scale right
			if(scaleSide == 2) {
				switch(control) {
				case 1:
					driveWithEncoders(283, 1, 0);
					break;
				case 2:
					if(limitSwitchTop.get()) {
						lifty.set(-1);
					}else {
						control++;
						lifty.set(-.1);
					}
					break;
				case 3:
					turning(-70);
					break;
				case 4:
					grab.set(1);
					driveStraight(0, 1);
					break;
				case 5:
					driveStraight(-.3, 2);
					break;
					
				}
			}else {
				switch(control) {
				case 1:
					driveWithEncoders(230, 1, 0);
					break;
				case 2:
					turning(-90);
					break;
				case 3:
					lifty.set(-.5);
					driveWithEncoders(188, .5, -90);
					break;
				case 4:
					if(limitSwitchTop.get()) {
						lifty.set(-1);
					}else {
						lifty.set(-.1);
					}
					turning(90);
					break;
				case 5:
					if(limitSwitchTop.get()) {
						lifty.set(-1);
					}else {
						lifty.set(-.1);
					}
					driveWithEncoders(24, .4, 0);
					break;
				case 6:
					grab.set(.8);
					driveStraight(0,1);
					break;
				case 7:
					driveWithEncoders(-15, .4, 0);
					break;
				case 8:
					
					break;
				}
			}
		}else if(stationTwo == 10) {
				switch(control) {
				case 1:
					driveWithEncoders(200, 1, 0);
					break;
				}
		}
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		ahrs.reset();
		eHook.reset();
		lifty.stopMotor();
		hook = false;
	//	double righttime = timer.get();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		Scheduler.getInstance().run();
//		SmartDashboard.putString("X", Double.toString(ahrs.getRawGyroX()));
//		SmartDashboard.putString("Y", Float.toString(ahrs.getRawGyroY()));
//		SmartDashboard.putString("Z", Float.toString(ahrs.getRawGyroZ()));
//		SmartDashboard.putString("Pitch", Float.toString(ahrs.getPitch()));
//		SmartDashboard.putString("Roll", Float.toString(ahrs.getRoll()));
//		SmartDashboard.putString("Yaw", Float.toString(ahrs.getYaw()));
//		SmartDashboard.putString("Switch", Boolean.toString(limitSwitch.get()));
		//here
		
		updateGyro();
		SmartDashboard.putString("Gyro", Double.toString(angle));
		SmartDashboard.putNumber("Hook Encoder", eHook.getDistance());
		SmartDashboard.putNumber("Left Encoder", eLeft.getDistance()/(54.4));
		SmartDashboard.putNumber("Right Encoder", eRight.getDistance()/(54.4));
	
		
		if (stick1.getRawAxis(3) < 0)
		{
			dir = 1;
		}
		else dir = -1;
		if (stick1.getRawButton(1))
		{
			velRequest = dir * -stick1.getRawAxis(1) * maxSpd * 0.5;
		}
		else
		{
			velRequest =  dir * -stick1.getRawAxis(1) * maxSpd;
		}
		
		
		
		if(Math.abs(stick1.getRawAxis(0)) < Math.abs(stick1.getRawAxis(twist))) {
			strRequest = -stick1.getRawAxis(twist) * 0.4;
		}else {
			strRequest = stick1.getRawAxis(0) * 0.10;
		}
		
		
		if (aStep < velRequest - velPresent) velRequest = velPresent + aStep;
		if (dStep > velRequest - velPresent) velRequest = velPresent - dStep;
		strMax = (maxSpd - (velRequest * 0.5));
		if (Math.abs(strRequest) > strMax) strRequest = strMax * Math.signum(strRequest);
		if (stick1.getRawButton(6) || stick1.getRawButton(2)) strRequest *= 0.5;
		setMotors(velRequest + strRequest, velRequest - strRequest);
		if ((Math.abs(leftDrive.get()) < 0.01) && (Math.abs(rightDrive.get()) < 0.01))
		{
			leftDrive.set(0);
			rightDrive.set(0);
		}
		else
		{
			setMotors(velRequest + strRequest, velRequest - strRequest);
		}
		velPresent = velRequest;
		

		if(stick1.getRawButton(5)) {
			liftDir = 1;
			if (limitSwitchTop.get()) lifty.set(-1);
			else lifty.set(-0.1);
		}else if(stick1.getRawButton(3)) 
			{
				liftDir = -1;
				if (limitSwitchBot.get()) lifty.set(1);
				else lifty.set(0);
			}
		else {
			if (liftDir > 0) lifty.set(-0.1);
			else lifty.set(0);
		}
		if(stick1.getRawButton(2)) {
			grab.set(-1);
		}else if(stick1.getRawButton(1)){
			grab.set(.9);
		}else {
			grab.set(0);
		}
		
		if(stick1.getRawButton(6)) {
			hook = true;
		}
		if (stick1.getRawButton(4)) {
			hooky.set(1);
			hook = false;
		}
		else if (hook == true)
		{
			hooky.set(-(1873-eHook.getDistance())/100);
		}
		else
		{
			hooky.set(0);

		}
		
		//hook liftControls();
		if(stick1.getRawButton(7)) {
			winchitty.set(1);
		}else if(stick1.getRawButton(9) && stick1.getRawButton(10)) {
			winchitty.set(-1);
		}else {
			winchitty.set(0);
		}	//winch lift controls
	
	}
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	public void driveStraight(double DSspeed, double DStime) {
		SmartDashboard.putString("DB/String 3", "Drive Straight");
		switch(functionControl) {
		case 0:
			FunctionAngle = angle;
			initTime = timer.get();
			angleCorrection = 70;
			functionControl += 1;
			break;
		case 1:
			
			SmartDashboard.putNumber("DB/String 5", DStime-(timer.get()-initTime));
			SmartDashboard.putNumber("DB/String 6", functionAngleCorrection);
			SmartDashboard.putNumber("DB/String 7", DSspeed);
			functionAngleCorrection = ((angle - FunctionAngle)/angleCorrection);
			if (functionAngleCorrection > 0.4) functionAngleCorrection = 0.4;
			setMotors(DSspeed + functionAngleCorrection, DSspeed - functionAngleCorrection);
			if(timer.get() - initTime > DStime) {
				functionControl += 1;
				}
			break;
		case 2:
			setMotors(0, 0);
			functionControl = 0;
			control += 1;
		}
	}
	
	public void updateGyro() {
		float yaw = ahrs.getYaw();
		if (lastangle - yaw > 250) {
			anglemultiplier += 1;
		}
		if (lastangle - yaw < -250) { 
			anglemultiplier -= 1;
		}
		angle = yaw + (360 * anglemultiplier);
		lastangle = yaw;
	}
	
	public void setMotors (double power1, double power2){
		leftDrive.set(power1);
		rightDrive.set(power2);
	}

	public void turning(double Tangle) {
		SmartDashboard.putString("DB/String 3", "Turning");
		switch(functionControl) {
		case 0:
			initTime = timer.get();
			FunctionAngle = Tangle+angle;
			angleCorrection = 90;
			functionControl += 1;
			break;
		case 1:
			SmartDashboard.putString("DB/String 5", Double.toString(FunctionAngle-angle));
			SmartDashboard.putString("DB/String 6", Double.toString(FunctionAngle));
			SmartDashboard.putString("DB/String 7", Double.toString(functionAngleCorrection));
			SmartDashboard.putString("DB/String 8", Double.toString(2-(timer.get()-initTime)));
			
			functionAngleCorrection = ((angle - FunctionAngle)/angleCorrection);
			if (functionAngleCorrection > 0.4) functionAngleCorrection = 0.4;
			if (functionAngleCorrection < -0.4) functionAngleCorrection = -0.4;
			
			setMotors(functionAngleCorrection, -functionAngleCorrection);
			/*if(Math.abs(angle-FunctionAngle)<5) {
				functionControl += 1;
				}*/
			if(timer.get()-initTime > 1.7) {
				functionControl += 1;
			}
			break;
		case 2:
			setMotors(0, 0);
			functionControl = 0;
			control += 1;
		}
	}

	public void driveWithEncoders(double distanceInInches, double speed, double FunctionAngle) {
		SmartDashboard.putString("DB/String 3", "Drive With Encoders");
		switch(functionControl) {
		case 0:
			eLeft.reset();
			eRight.reset();
			initDistance = eLeft.getDistance()/54.4;
			initTime = timer.get();
			//FunctionAngle = angle;
			angleCorrection = 90;
			positionCorrection = 50;
			accelerationCorrection = 2;
		positionDerivativeCorrection = 20;  	
			SmartDashboard.putString("DB/String 5", Double.toString((eLeft.getDistance()/54.4 - initDistance)));
			SmartDashboard.putString("DB/String 6", Double.toString((distanceInInches)));
			functionControl = 1;
			functionPositionCorrection = ((distanceInInches)-(eLeft.getDistance()/54.4))/positionCorrection;
			if (functionPositionCorrection > speed) functionPositionCorrection = speed;
			if (functionPositionCorrection < -speed) functionPositionCorrection = -speed;
			break;
		case 1:
			SmartDashboard.putString("DB/String 5", Double.toString((eLeft.getDistance()/54.4 - initDistance)));
			SmartDashboard.putString("DB/String 6", Double.toString((distanceInInches)));
			SmartDashboard.putString("DB/String 7", Double.toString(positionDerivative));
			SmartDashboard.putString("DB/String 8", Double.toString(functionPositionCorrection));
			SmartDashboard.putString("DB/String 9", Double.toString((functionPositionCorrection+positionDerivative+.2+functionAngleCorrection)*functionAccelerationCorrection));
			
			
			functionAccelerationCorrection = (timer.get()-initTime)/accelerationCorrection;
			if (functionAccelerationCorrection > 1) functionAccelerationCorrection = 1;
			if (functionAccelerationCorrection < -1) functionAccelerationCorrection = -1;
			
			
			positionDerivative = functionPositionCorrection;
			functionPositionCorrection = ((distanceInInches)-((eLeft.getDistance()/54.4)-initDistance))/positionCorrection;
			if (functionPositionCorrection > speed) functionPositionCorrection = speed;
			if (functionPositionCorrection < -speed) functionPositionCorrection = -speed;
			positionDerivative = (functionPositionCorrection - positionDerivative)*positionDerivativeCorrection;
			
			
			functionAngleCorrection = ((angle - FunctionAngle)/angleCorrection);
			if (functionAngleCorrection > speed) functionAngleCorrection = speed;
			if (functionAngleCorrection < -speed) functionAngleCorrection = -speed;
			
			setMotors((functionPositionCorrection+positionDerivative+(distanceInInches/Math.abs(distanceInInches))*.2+functionAngleCorrection)*functionAccelerationCorrection, (functionPositionCorrection+positionDerivative+(distanceInInches/Math.abs(distanceInInches))*.2-functionAngleCorrection)*functionAccelerationCorrection);
			
			
			if(Math.abs((eLeft.getDistance()/54.4 - initDistance)-(distanceInInches)) < 1) {
				functionControl ++;
			}
			break;
		case 2:
			setMotors(0,0);
			control ++;
			functionControl = 0;
			eLeft.reset();
			eRight.reset();
			break;
		}
	}

}



