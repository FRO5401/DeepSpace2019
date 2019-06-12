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
  NetworkTableEntry distanceEntry;
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable VisionData;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

    //Moves the drivebase based on camera input, then stops moving.
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

    //Aligns the drivebase based on camera input, then places a hatch panel.
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
