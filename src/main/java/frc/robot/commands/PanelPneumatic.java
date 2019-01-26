/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class PanelPneumatic extends Command {
  
  int panelPosition;

  public PanelPneumatic() {
    requires(Robot.panel);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() { // sets the default panelPosition to 1
    panelPosition = 1;
    Robot.panel.position1();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean panelMove = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_Y_OPERATOR);
    //the xbox button is y

    if(panelMove == true && panelPosition == 1){ // sets panel position to 2 which sets L1 and R1 to true
      panelPosition = 2; 
      Robot.panel.position2();
    } else if (panelMove == true && panelPosition == 2) { // sets panel position to 3 which alters L1, R1, L2, and R2 to true
      panelPosition = 3; 
      Robot.panel.position3();
    } else if (panelMove == true && panelPosition == 3){ // sets panel position to 1 which alters L1, R1, L2, and R2 to false
      panelPosition = 1;
      Robot.panel.position1();
    }
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
