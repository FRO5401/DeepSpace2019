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

/*
 * Command controls the following:
 * - CARGO INFEEDER
 * - CARRIAGE MOVEMENT
 */

public class FeedCarriage extends Command {
  boolean overrideButton;
  boolean limitTop;
  double feedIn;
  double feedOut;

  double carriageUpDown;
  double carriageReset;
  double carriageMiddle;
  double carriageGround;
  
  public FeedCarriage() {
    requires(Robot.carriageinfeed);
  }

  // Called just before this Command runs first time
  @Override
  protected void initialize() {
    Robot.carriageinfeed.carriageSetTalonNeutralMode(NeutralMode.Brake);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      //Read buttons
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_R3);
    feedIn = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_TRIGGER);
    feedOut = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_TRIGGER);

      //Read axis
    carriageUpDown = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_Y);

      //Read sensors
    limitTop = Robot.carriageinfeed.getLimitTop();

    /*** Carriage Override Logic ***/

      //If the override button is pressed. 
    if(overrideButton){
        //If the top limit NOT tripped. 
      if(!limitTop){
          //Allow FREE movement only within the threshold.
        if((carriageUpDown > RobotMap.AXIS_THRESHOLD) || (carriageUpDown < (-1 * RobotMap.AXIS_THRESHOLD))){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
          //Disallow movement outside of threshold. 
        else{
          Robot.carriageinfeed.carriageOverrideMove(0);
        }
      }
        //If the top limit IS tripped. 
      else if(limitTop){
          //Allow DOWN movement only within the threshold.
        if(carriageUpDown > (-1 * RobotMap.AXIS_THRESHOLD)){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
          //Disallow movement outside of threshold. 
        else{
          Robot.carriageinfeed.carriageOverrideMove(0);
        }
      }
    }
      //If the override button is not pressed.
    else{
      Robot.carriageinfeed.carriageOverrideMove(0);
    }
    
    /*** FEEDER LOGIC ***/

      //Don't feed in within the deadzone. 
    if(feedIn > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedIn();
    }
      //Don't feed out within the deadzone.
    else if(feedOut > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedOut();
    }
      //Don't rotate the feeder motors at all. 
    else{
      Robot.carriageinfeed.feedStop();
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
    Robot.carriageinfeed.feedStop();
    Robot.carriageinfeed.carriageOverrideMove(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.carriageinfeed.feedStop();
    Robot.carriageinfeed.carriageOverrideMove(0);
  }
}
