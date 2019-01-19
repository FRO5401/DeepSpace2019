/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class FeedCargo extends Command {
  boolean feedIn;
  boolean feedOut;
  boolean lightSensor;

  public FeedCargo() {
    requires(Robot.cargoInfeed);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    /*** Inputs ***/
      //Buttons
    feedIn = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER_OPERATOR);
    feedOut = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER_OPERATOR);
    lightSensor = Robot.cargoInfeed.getLightSensor();

      //If lightsensor is not tripped, allow it to feedIn.
    if(!lightSensor){
      if((feedIn) && (!feedOut)){
        Robot.cargoInfeed.feedCargo(1);
      }
      if((feedOut) && (!feedIn)){
        Robot.cargoInfeed.feedCargo(-1);
      }
    }
      //If lightsensor IS tripped, DON'T allow it to feedIn.
    else if(lightSensor){
      if((feedOut) && (!feedIn)){
        Robot.cargoInfeed.feedCargo(-1);
      }
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
