/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;
import frc.robot.commands.PanelPneumatic;


public class Panel extends Subsystem {
// variables 
  private Solenoid L1;
  private Solenoid L2;
  private Solenoid R1;
  private Solenoid R2;
  
  public Panel(){ // Solenoid extansiation <-- I can't spell
    Solenoid L1 = new Solenoid(RobotMap.LEFT_PANEL_1);
    Solenoid L2 = new Solenoid(RobotMap.LEFT_PANEL_2);
    Solenoid R1 = new Solenoid(RobotMap.RIGHT_PANEL_1);
    Solenoid R2 = new Solenoid(RobotMap.RIGHT_PANEL_2);
  }

  public void position1(){ // sets panels to point upwards
    L1.set(false);
    L2.set(false);
    R1.set(false);
    R2.set(false);
  }

  public void position2(){ // sets panels to be at a right angle
    L1.set(true);
    L2.set(false);
    R1.set(true);
    R2.set(false);
  }

  public void position3(){ // sets panels to face straight forward
    L1.set(true);
    L2.set(true);
    R1.set(true);
    R2.set(true);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}