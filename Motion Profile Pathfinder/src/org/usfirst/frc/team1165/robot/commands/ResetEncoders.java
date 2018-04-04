package org.usfirst.frc.team1165.robot.commands;

import org.usfirst.frc.team1165.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetEncoders extends Command
{
    private static DriveTrain mDriveTrain = DriveTrain.getInstance();

    public ResetEncoders()
    {
	requires(mDriveTrain);
    }

    @Override
    protected void initialize()
    {
	mDriveTrain.reset();
    }

    @Override
    protected boolean isFinished()
    {
	return mDriveTrain.isReset();
    }
}
