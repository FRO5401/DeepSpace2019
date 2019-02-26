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
  //booleans for controlling the carriage and the rollers
  boolean overrideButton;
  double feedIn;
  double feedOut;

  //doubles that are used as conditions to set the carriage to a specific angle when the elevator is at a certain height
  double carriageUpDown;
  double carriageReset;
  double carriageMiddle;
  double carriageGround;
  
  public FeedCarriage() {
    requires(Robot.carriageinfeed);
  }

  @Override
  protected void initialize() {
    Robot.carriageinfeed.carriageSetTalonNeutralMode(NeutralMode.Brake);
  }

  @Override
  protected void execute() {
    //Buttons for controlling the carriage
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_R3);
    feedIn = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_TRIGGER);
    feedOut = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_TRIGGER);

    carriageUpDown = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_Y);

    if(overrideButton){//when the elevator is moving
      //allows the normal movement of the carriage as long as the input is within the range
      if(Robot.carriageinfeed.getCarriageAngle() <= 88 && Robot.carriageinfeed.getCarriageAngle() >= -43){
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }

      //If the elevator is above or equal to the max safe angle, it will only be allowed to move down
      else if(Robot.carriageinfeed.getCarriageAngle() >= 88){
        if (carriageUpDown < 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      //If the elevator is above or equal to the max safe angle, it will only be allowed to move down
      else if(Robot.carriageinfeed.getCarriageAngle() <= -43){
        if (carriageUpDown > 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      //else{
        //Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      //}  
    }
    
    //Allows the movement of the rollers inward while pressing down the right trigger if it is greater than the threshold
    if(feedIn > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedIn();
    }
    //Allows the movement of the rollers inward while pressing down the left trigger if it is greater than the threshold
    else if(feedOut > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedOut();
    }
    //If neither are being pressed, or if the axis value is not reached, the feeders will stop
    else{
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
