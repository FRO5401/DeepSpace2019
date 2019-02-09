/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class VisionAuto extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  NetworkTableEntry distanceEntry;
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable VisionData;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void visionAlign(){
    VisionData = inst.getTable("VisionData");
    distanceEntry = VisionData.getEntry("distance");

    double distance = distanceEntry.getDouble(0);
    if(distance > 2) {
      // TODO: Update Values
      Robot.drivebase.drive(.3,-.3);
    }
    if(distance < -2) {
      // TODO: Update Values
      Robot.drivebase.drive(-.3,.3);
    }
    if(distance <= 2 || distance >= -2){
      Robot.drivebase.stopMotors();
    } 
  }

  public void visionPlaceHatch(){
    VisionData = inst.getTable("VisionData");
    distanceEntry = VisionData.getEntry("distance");

    double distance = distanceEntry.getDouble(0);
    if(distance > 2) {
      // TODO: Update Values
      Robot.drivebase.drive(.3,-.3);
    }
    if(distance < -2) {
      // TODO: Update Values
      Robot.drivebase.drive(-.3,.3);
    }
    if(distance <= 2 || distance >= -2){
      Robot.drivebase.stopMotors();
    //TODO: add hatch method command here???
    } 
  }
}
