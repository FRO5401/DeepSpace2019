/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
/**
 * Add your docs here.
 */
public class RobotFeet extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  Solenoid leftFoot = new Solenoid(RobotMap.ROBOT_LEFT_FOOT);
  Solenoid rightFoot = new Solenoid(RobotMap.ROBOT_RIGHT_FOOT);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void collapseFeet(){
    leftFoot.set(false);
    rightFoot.set(false);
  }

  public void expandFeet(){
    leftFoot.set(true);
    rightFoot.set(true);
  }

  public boolean returnFeetCollapsed(){
    return leftFoot.get();
  }

  public void reportFootStatus(){
    SmartDashboard.putBoolean("Feet are Expanded", returnFeetCollapsed());
  }
}
