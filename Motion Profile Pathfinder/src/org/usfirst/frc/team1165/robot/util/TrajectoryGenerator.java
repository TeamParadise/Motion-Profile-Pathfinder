
package org.usfirst.frc.team1165.robot.util;

import static org.usfirst.frc.team1165.robot.RobotMap.IN_TO_M;
import static org.usfirst.frc.team1165.robot.RobotMap.IN_TO_TICKS;
import static org.usfirst.frc.team1165.robot.RobotMap.MAX_ACCELERATION;
import static org.usfirst.frc.team1165.robot.RobotMap.MAX_JERK;
import static org.usfirst.frc.team1165.robot.RobotMap.MAX_VELOCITY;
import static org.usfirst.frc.team1165.robot.RobotMap.M_TO_IN;
import static org.usfirst.frc.team1165.robot.RobotMap.WIDTH;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class TrajectoryGenerator
{
    private static final TrajectoryGenerator mInstance = new TrajectoryGenerator();

    public static TrajectoryGenerator getInstance()
    {
	return mInstance;
    }

    private Config mConfig;
    private Trajectory mTrajectory;
    private TankModifier mModifier;
    private double[][] mRightTrajectory, mLeftTrajectory;

    protected TrajectoryGenerator()
    {
	mConfig = new Config(Trajectory.FitMethod.HERMITE_QUINTIC, Config.SAMPLES_HIGH, 0.01, MAX_VELOCITY,
		MAX_ACCELERATION, MAX_JERK);

	mTrajectory = Pathfinder.generate(Points.POINTS, mConfig);

	mModifier = new TankModifier(mTrajectory).modify(WIDTH * IN_TO_M);

	mRightTrajectory = parseTrajectory(mModifier.getRightTrajectory());
	mLeftTrajectory = parseTrajectory(mModifier.getLeftTrajectory());

	System.out.println("----------");
	System.out.println("Right Trajectory");
	System.out.println("----------");
	System.out.println(printTrajectory(mModifier.getRightTrajectory()));
	System.out.println("----------");
	System.out.println("Left Trajectory");
	System.out.println("----------");
	System.out.println(printTrajectory(mModifier.getLeftTrajectory()));
	System.out.println("----------");

    }

    public double[][] getLeftTrajectory()
    {
	return mLeftTrajectory;
    }

    public double[][] getRightTrajectory()
    {
	return mRightTrajectory;
    }

    private double[][] parseTrajectory(Trajectory trajectory)
    {
	double[][] points = new double[trajectory.segments.length][2];

	for (int i = 0; i < trajectory.segments.length; i++)
	{
	    Segment segment = trajectory.segments[i];

	    points[i][0] = segment.position * M_TO_IN * IN_TO_TICKS;
	    points[i][1] = segment.velocity * M_TO_IN * IN_TO_TICKS / 10;
	}

	return points;
    }

    private String printTrajectory(Trajectory trajectory)
    {
	StringBuilder sb = new StringBuilder("Position\tVelocity\n\n");

	for (int i = 0; i < trajectory.segments.length; i++)
	{
	    Segment segment = trajectory.segments[i];

	    sb.append("{");
	    sb.append(round(segment.position * M_TO_IN));
	    sb.append(",");
	    sb.append(round(segment.velocity * M_TO_IN));
	    sb.append("},");
	    sb.append("\n");
	}

	return sb.toString();
    }

    private static double round(double a)
    {
	return Math.round(a * 10000.0) / 10000.0;
    }

}
