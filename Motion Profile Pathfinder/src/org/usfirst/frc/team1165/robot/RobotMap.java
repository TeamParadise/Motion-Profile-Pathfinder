package org.usfirst.frc.team1165.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * @author Kesav Kadalazhi
 */
public class RobotMap
{
    // PORTS

    public static final int JOYSTICK_PORT = 0;

    public static final int FRONT_LEFT_DRIVE_TALON = 23;
    public static final int REAR_LEFT_DRIVE_TALON = 21;
    public static final int FRONT_RIGHT_DRIVE_TALON = 20;
    public static final int REAR_RIGHT_DRIVE_TALON = 22;

    // MOTION CONSTANTS

    public static final double MAX_VELOCITY = 2.804; // 9.2 ft/s
    public static final double MAX_ACCELERATION = 28.04; // 92 ft/s/s
    public static final double MAX_JERK = 60; // 197 ft/s/s/s

    public static final double WHEEL_DIAMETER = 0.1524; // 6 in

    public static final double TICKS_TO_IN = Math.PI * 6 / 4096;
    public static final double IN_TO_TICKS = 4096 / (Math.PI * 6);
    public static final double IN_TO_M = 0.0254;
    public static final double M_TO_IN = 1 / IN_TO_M;

    public static final int TIMEOUT = 0;
    public static final int DRIVE_STRAIGHT_SLOT = 0;

    public static final double LENGTH = 23;
    public static final double WIDTH = 19;

    // public static final int MAX_VELOCITY = 2400; // in native units or 9.2
    // ft/s

    public static final double[] DRIVE_STRAIGHT_LEFT_PID = { 0.025, 0, 0, 0 };
    public static final double[] DRIVE_STRAIGHT_RIGHT_PID = { 0.025, 0, 0, 0 };

    // public static final double[] DRIVE_STRAIGHT_LEFT_PID = { 0.32, 0, 0, 0 };
    // public static final double[] DRIVE_STRAIGHT_RIGHT_PID = { 0.25, 0, 0, 0
    // };

}
