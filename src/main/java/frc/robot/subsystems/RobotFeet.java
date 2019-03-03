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

  DoubleSolenoid leftFoot = new DoubleSolenoid(RobotMap.PCM_ID, );
  DoubleSolenoid rightFoot = new DoubleSolenoid(RobotMap.PCM_ID, );

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  void collapseFeet(){
    leftFoot.set(DoubleSolenoid.Value.kReverse);
    rightFoot.set(DoubleSolenoid.Value.kReverse);
  }

  void expandFeet(){
    leftFoot.set(DoubleSolenoid.Value.kForward);
    rightFoot.set(DoubleSolenoid.Value.kForward);
  }

  boolean returnFeetCollapsed(){
    if((leftFoot.get() == Value.kForward) || (rightFoot.get() == Value.kForward)){
       return true; 
    }
    else if((leftFoot.get() == Value.kReverse) || (rightFoot.get() == Value.kReverse)){
       return false; 
    } 
  }

  void reportFootStatus(){
    SmartDashboard.putBoolean("Feet are Expanded", returnFeetCollapsed());
  }
}
