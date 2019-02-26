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

public class FeedCarriage extends Command {
  boolean overrideButton;
  double feedIn;
  double feedOut;

  double carriageUpDown;
  double carriageReset;
  double carriageMiddle;
  double carriageGround;
  
  public FeedCarriage() {
    requires(Robot.carriageinfeed); // Uses CarriageInFeed Subsystem
  }

  @Override
  protected void initialize() {
    Robot.carriageinfeed.carriageSetTalonNeutralMode(NeutralMode.Brake);  // CarriageInFeed Value is set to Neutral Mode; Brake
  }

  @Override
  protected void execute() {  // Buttons for Carriage Control on Operator Controller
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_R3); // R3 for Override
    feedIn = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_TRIGGER);  // Right Trigger for Infeed 
    feedOut = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_TRIGGER);  // Left Trigger for Outfeed

    carriageUpDown = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_Y);  // Y-Axis for Carriage Movement

    if(overrideButton){ // If R3 is Pressed
      // If the Carriage is in between 88 and -43 degrees, then the carriage will move as inputted normally
      if(Robot.carriageinfeed.getCarriageAngle() >= 88 && Robot.carriageinfeed.getCarriageAngle() <= -43){
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }
      else if(Robot.carriageinfeed.getCarriageAngle() >= 88){
        // If the carriage angle is greater than or equal to 88, then only allow downwards movement
        if (carriageUpDown < 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      else if(Robot.carriageinfeed.getCarriageAngle() <= -43){
        // If the carriage angle is less than or equal to -43, then only allow upwards movement
        if (carriageUpDown > 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      else{ // If not, continue as normal with movement as inputted
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }  
    }
      
    if(feedIn > RobotMap.AXIS_THRESHOLD){ 
      // If InFeed is greater than Axis-Threshold, allow cargo to feed into carriage
      Robot.carriageinfeed.feedIn();
    }
    else if(feedOut > RobotMap.AXIS_THRESHOLD){
      // If OutFeed is greater than Axis-Threshold, allow cargo to be expelled from the carriage
      Robot.carriageinfeed.feedOut();
    }
    else{ // Stop if Threshold is not reached or buttons are not pressed
      Robot.carriageinfeed.feedStop();
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.carriageinfeed.feedStop();
    Robot.carriageinfeed.carriageOverrideMove(0);
  }

  @Override
  protected void interrupted() {
    Robot.carriageinfeed.feedStop();
    Robot.carriageinfeed.carriageOverrideMove(0);
  }
}
