/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class FeedCargo extends Command {
  boolean overrideButton;
  boolean feedIn;
  boolean feedOut;

  double armUpDown;
  
  public FeedCargo() {
    requires(Robot.cargoinfeed);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.cargoinfeed.armSetTalonNeutralMode(NeutralMode.Brake);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      //Read buttons
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3_OPERATOR);
    feedIn = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER_OPERATOR);
    feedOut = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER_OPERATOR);

      //Read axis
    armUpDown = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);

      //Arm Move logic.
    if(overrideButton){
      Robot.cargoinfeed.armOverrideMove(armUpDown);
    }
      
      //Feeder Logic
    if(feedIn && (!feedOut)){
      Robot.cargoinfeed.feedIn();
    }
    else if(feedOut && (!feedIn)){
      Robot.cargoinfeed.feedOut();
    }
    else{
      Robot.cargoinfeed.feedStop();
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
    Robot.cargoinfeed.feedStop();
    Robot.cargoinfeed.armOverrideMove(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.cargoinfeed.feedStop();
    Robot.cargoinfeed.armOverrideMove(0);
  }
}
