/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;
import frc.robot.commands.PanelPneumatic;


public class Panel extends Subsystem {

  private DoubleSolenoid panelPneumatic;
  
  public HatchMechanism(){
    Solenoid hatchRelease = new Solenoid(RobotMap.HATCH_EXTENDER);
  }

  public void openHatch(){
    hatchRelease.set(true);
  }

  public void closeHatch(){
    hatchRelease.set(false);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}