
package org.usfirst.frc.team1165.robot;

import org.usfirst.frc.team1165.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * @author Kesav Kadalazhi
 */
public class Robot extends IterativeRobot
{
    private DriveTrain mDriveTrain = DriveTrain.getInstance();

    @Override
    public void robotInit()
    {
    }

    @Override
    public void robotPeriodic()
    {
	mDriveTrain.updateStatus();
	mDriveTrain.report();
    }

    @Override
    public void disabledPeriodic()
    {
	Scheduler.getInstance().run();
    }

    @Override
    public void autonomousPeriodic()
    {
	Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
    }

    @Override
    public void teleopPeriodic()
    {
	Scheduler.getInstance().run();
    }
}
