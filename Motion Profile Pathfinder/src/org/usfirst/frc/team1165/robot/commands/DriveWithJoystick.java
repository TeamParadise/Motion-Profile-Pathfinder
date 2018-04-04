package org.usfirst.frc.team1165.robot.commands;

import org.usfirst.frc.team1165.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1165.robot.util.OperatorInterface;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithJoystick extends Command
{
    private static DriveTrain mDriveTrain = DriveTrain.getInstance();
    private static OperatorInterface mOI = OperatorInterface.getInstance();

    public DriveWithJoystick()
    {
	requires(mDriveTrain);
    }

    @Override
    protected void execute()
    {
	mDriveTrain.arcadeDrive(mOI.getY(), mOI.getTwist());
    }

    @Override
    protected boolean isFinished()
    {
	return false;
    }

    @Override
    protected void end()
    {
	mDriveTrain.arcadeDrive(0, 0);
    }
}
