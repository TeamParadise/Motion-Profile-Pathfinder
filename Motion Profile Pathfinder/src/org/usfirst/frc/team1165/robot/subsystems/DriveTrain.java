
package org.usfirst.frc.team1165.robot.subsystems;

import static org.usfirst.frc.team1165.robot.RobotMap.DRIVE_STRAIGHT_LEFT_PID;
import static org.usfirst.frc.team1165.robot.RobotMap.DRIVE_STRAIGHT_RIGHT_PID;
import static org.usfirst.frc.team1165.robot.RobotMap.FRONT_LEFT_DRIVE_TALON;
import static org.usfirst.frc.team1165.robot.RobotMap.FRONT_RIGHT_DRIVE_TALON;
import static org.usfirst.frc.team1165.robot.RobotMap.REAR_LEFT_DRIVE_TALON;
import static org.usfirst.frc.team1165.robot.RobotMap.REAR_RIGHT_DRIVE_TALON;
import static org.usfirst.frc.team1165.robot.RobotMap.TICKS_TO_IN;
import static org.usfirst.frc.team1165.robot.RobotMap.TIMEOUT;

import org.usfirst.frc.team1165.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team1165.robot.util.TrajectoryGenerator;
import org.usfirst.frc.team1165.robot.util.drivers.MasterTalon;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain extends Subsystem
{
    private static final DriveTrain mInstance = new DriveTrain();

    public static DriveTrain getInstance()
    {
	return mInstance;
    }

    private MasterTalon mFrontLeft, mFrontRight;
    private WPI_TalonSRX mRearLeft, mRearRight;
    private DifferentialDrive mDrive;

    private MotionProfileStatus mLeftStatus = new MotionProfileStatus();
    private MotionProfileStatus mRightStatus = new MotionProfileStatus();

    private Notifier mNotifier = new Notifier(() ->
    {
	mFrontLeft.processMotionProfileBuffer();
	mFrontRight.processMotionProfileBuffer();
    });

    private TrajectoryGenerator mGenerator = TrajectoryGenerator.getInstance();

    protected DriveTrain()
    {
	mFrontLeft = new MasterTalon(FRONT_LEFT_DRIVE_TALON, false, false, DRIVE_STRAIGHT_LEFT_PID);
	mFrontRight = new MasterTalon(FRONT_RIGHT_DRIVE_TALON, false, false, DRIVE_STRAIGHT_RIGHT_PID);

	mRearLeft = new WPI_TalonSRX(REAR_LEFT_DRIVE_TALON);
	mRearRight = new WPI_TalonSRX(REAR_RIGHT_DRIVE_TALON);

	mDrive = new DifferentialDrive(mFrontLeft, mFrontRight);

	mRearLeft.follow(mFrontLeft);
	mRearRight.follow(mFrontRight);

	mFrontLeft.changeMotionControlFramePeriod(5);
	mFrontRight.changeMotionControlFramePeriod(5);
	mNotifier.startPeriodic(0.005);
    }

    public void arcadeDrive(double y, double twist)
    {
	mDrive.arcadeDrive(y, twist);
    }

    public void initializeProfile()
    {
	System.out.println("Initialize Profile");
	
	mFrontLeft.clearMotionProfileTrajectories();
	mFrontRight.clearMotionProfileTrajectories();

	fill(mFrontLeft, mGenerator.getLeftTrajectory());
	fill(mFrontRight, mGenerator.getRightTrajectory());
    }

    public void runProfile()
    {
	mFrontLeft.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
	mFrontRight.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
    }

    public boolean isFinished() {
	return mLeftStatus.isLast && mRightStatus.isLast;
    }

    public void finishProfile()
    {
	System.out.println("Finish Profile");
	
	mFrontLeft.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
	mFrontRight.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);

	mFrontLeft.set(ControlMode.PercentOutput, 0);
	mFrontRight.set(ControlMode.PercentOutput, 0);
    }

    public void updateStatus() {
	mFrontLeft.getMotionProfileStatus(mLeftStatus);
	mFrontRight.getMotionProfileStatus(mRightStatus);
    }

    public static void fill(WPI_TalonSRX talon, double[][] points)
    {
	talon.configMotionProfileTrajectoryPeriod(0, TIMEOUT);

	TrajectoryPoint point = new TrajectoryPoint();

	point.headingDeg = 0; // unused
	point.profileSlotSelect0 = 0;
	point.profileSlotSelect1 = 0; // unused
	point.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

	for (int i = 0; i < points.length; i++)
	{
	    point.position = points[i][0];
	    point.velocity = points[i][1];

	    point.zeroPos = false;
	    if (i == 0) point.zeroPos = true;

	    point.isLastPoint = false;
	    if ((i + 1) == points.length) point.isLastPoint = true;

	    talon.pushMotionProfileTrajectory(point);
	}
    }
    
    public void reset()
    {
	mFrontLeft.reset();
	mFrontRight.reset();
    }
    
    public boolean isReset() {
	return mFrontLeft.isReset() && mFrontRight.isReset();
    }

    public void report()
    {
	mFrontLeft.report("Left");
	mFrontRight.report("Right");
    }
    
    public void reportProfile()
    {
	System.out.println(mLeftStatus.activePointValid + "\t" + mRightStatus.activePointValid);
	
	System.out.println(mLeftStatus.btmBufferCnt + "\t" + mRightStatus.btmBufferCnt);
	
	double leftPosition = mFrontLeft.getActiveTrajectoryPosition() * TICKS_TO_IN;
	double leftVelocity = mFrontLeft.getActiveTrajectoryVelocity() * 10 * TICKS_TO_IN;
	
	double rightPosition = mFrontLeft.getActiveTrajectoryPosition() * TICKS_TO_IN;
	double rightVelocity = mFrontLeft.getActiveTrajectoryVelocity() * 10 * TICKS_TO_IN;
	
	System.out.println(leftPosition + "\t" + leftVelocity + "\t\t\t" + rightPosition + "\t" + rightVelocity);
    }

    @Override
    protected void initDefaultCommand()
    {
	setDefaultCommand(new DriveWithJoystick());
    }

}