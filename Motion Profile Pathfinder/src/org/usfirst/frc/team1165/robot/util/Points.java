
package org.usfirst.frc.team1165.robot.util;

import org.usfirst.frc.team1165.robot.RobotMap;

import jaci.pathfinder.Waypoint;

public class Points {
    public static final Waypoint[] POINTS = {
	new Waypoint(RobotMap.IN_TO_M * 0, 0, 0),
	new Waypoint(RobotMap.IN_TO_M * 2 * 12, 0, 0),
	new Waypoint(RobotMap.IN_TO_M * 4 * 12, 0, 0),
	new Waypoint(RobotMap.IN_TO_M * 5 * 12, 0, 0)
    };
}
