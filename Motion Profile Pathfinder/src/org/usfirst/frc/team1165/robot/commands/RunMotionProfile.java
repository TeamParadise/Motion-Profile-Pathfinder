package org.usfirst.frc.team1165.robot.commands;

import org.usfirst.frc.team1165.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunMotionProfile extends Command
{

    private static DriveTrain mDriveTrain = DriveTrain.getInstance();

    public RunMotionProfile()
    {
	requires(mDriveTrain);
    }

    @Override
    protected void initialize()
    {
	mDriveTrain.initializeProfile();
    }

    @Override
    protected void execute()
    {
	mDriveTrain.runProfile();
	
	mDriveTrain.reportProfile();
    }

    @Override
    protected boolean isFinished()
    {
	return mDriveTrain.isFinished();
    }

    @Override
    protected void end()
    {
	mDriveTrain.finishProfile();
    }
}
