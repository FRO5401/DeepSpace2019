/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Robot;

public class VisionDrive extends Command {
  
  NetworkTableEntry distanceEntry;
  
  public VisionDrive() {
    requires(Robot.drivebase);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable VisionData = inst.getTable("VisionData");
    distanceEntry = VisionData.getEntry("distance");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double distance = distanceEntry.getDouble(0);
    if(distance > 2) {
      Robot.drivebase.drive(.3,-.3);
    }
    if(distance < -2) {
      Robot.drivebase.drive(-.3,.3);
    }
    if(distance <= 2 || distance >= -2){
      Robot.drivebase.stopMotors();
    } 
    /* TODO: Add in hatch placing code
     Or do we not want it?
     Just drive up to the space and exit,
     or actually place the hatch?*/
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
