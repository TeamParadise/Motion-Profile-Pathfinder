package org.usfirst.frc.team1165.robot.util;

import org.usfirst.frc.team1165.robot.RobotMap;
import org.usfirst.frc.team1165.robot.commands.ResetEncoders;
import org.usfirst.frc.team1165.robot.commands.RunMotionProfile;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OperatorInterface
{
    private static final OperatorInterface mInstance = new OperatorInterface();

    public static OperatorInterface getInstance()
    {
	return mInstance;
    }

    private Joystick stick = new Joystick(RobotMap.JOYSTICK_PORT);

    protected OperatorInterface()
    {
	SmartDashboard.putData(new RunMotionProfile());
	SmartDashboard.putData(new ResetEncoders());
    }

    // ----- Joystick Values ----- //

    public double getX()
    {
	return dampen(stick.getX());
    }

    public double getY()
    {
	return dampen(-stick.getY());
    }

    public double getTwist()
    {
	return dampen(stick.getTwist());
    }

    public double getThrottle()
    {
	return (1 - stick.getThrottle()) / 2;
    }

    // ----- Input Transform ----- //

    public static double dampen(double input)
    {
	return dampen(input, 3);
    }

    public static double dampen(double input, double dampener)
    {
	return Math.copySign(Math.pow(input, dampener), input);
    }

    public static double constrain(double input, double high, double low)
    {
	return Math.max(low, Math.min(input, high));
    }

    public static double deadband(double input, double deadband)
    {
	return Math.abs(input) > deadband ? input : 0;
    }

    public void report()
    {
	// SmartDashboard.putNumber("OI X", getX());
	// SmartDashboard.putNumber("OI Y", getY());
	// SmartDashboard.putNumber("OI Twist", getTwist());
	// SmartDashboard.putNumber("OI Throttle", getThrottle());
    }
}