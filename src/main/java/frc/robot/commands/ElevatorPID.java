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

public class ElevatorPID extends Command {

  double dPadInput;
    
  boolean left, right, theAButton;
 
  double rocketHighBall, rocketHighHatch;
  double rocketMidBall, rocketMidHatch;
  double rocketLowBall, rocketLowHatch;
  double cargoShipBall;
  double cargoBallInfeed;
  double ballInfeedFloor; 
  double endGame;

  boolean topLimit;
  boolean bottomLimit;

  public ElevatorPID() {
    requires(Robot.elevator);

      // Elevator's Setpoint Values for Movement
      rocketHighBall = 0; //TODO: change these to correct setpoints
      rocketHighHatch = 0;
      rocketMidBall = 0;
      rocketMidHatch = 0;
      rocketLowBall = 0;
      rocketLowHatch = 0;
      cargoShipBall = 0;
      cargoBallInfeed = 0;
      ballInfeedFloor = 0;

  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {

    dPadInput = Robot.oi.xboxDPad(Robot.oi.xboxOperator); // Inputs from OI, Operator's D-Pad
      
    left = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER);
    right = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER);
    theAButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_A);

    // Get Limit Switch Values from Elevator
    topLimit = Robot.elevator.getLimitT();
    bottomLimit = Robot.elevator.getLimitB();

    // When the topLimit is false, but the D-Pad (UP) & Left are pressed, move to value of rocketHighBall
    if((topLimit != true) && left && dPadInput == 0){ 
      Robot.elevator.setPoint(rocketHighBall);
    }
    // When the right and D-Pad (UP) are pressed, move to the value of rocketHighHatch
    else if(right && dPadInput == 0){ 
      Robot.elevator.setPoint(rocketHighHatch);
    }
    // When the left and D-Pad (LEFT) are pressed, move to the value of rocketMidBall
    else if(left && dPadInput == 270){ 
      Robot.elevator.setPoint(rocketMidBall);
    }
    // When the right and D-Pad (LEFT) are pressed, move to the value of rocketMidHatch
    else if(right && dPadInput == 270){
      Robot.elevator.setPoint(rocketMidHatch);
    }
    // When the left and D-Pad (RIGHT) are pressed, move to the value of rocketLowBall
    else if(left && dPadInput == 90){ 
      Robot.elevator.setPoint(rocketLowBall);
    }
    // When the right and D-Pad (RIGHT) are pressed, move to the value of rocketLowHatch
    else if(right && dPadInput == 90){ 
      Robot.elevator.setPoint(rocketLowHatch);
    }
    // When the left, right and D-Pad are pressed, move to position zero
    else if(left && right && dPadInput == 135){ 
      Robot.elevator.setPoint(0);
    }
    // When the right and D-Pad (DOWN) are pressed, move to the value of cargoShipBall
    else if(right && dPadInput == 180){ 
      Robot.elevator.setPoint(cargoShipBall);
    }
    // When the left and the D-Pad (DOWN) are pressed, move to the value of cargoBallInFeed
    else if(left && dPadInput == 180){
      Robot.elevator.setPoint(cargoBallInfeed);
    }
    // When the bottomLimit is false, and the A Button is pressed, pick up cargo from the floor
    else if((bottomLimit != true) && theAButton){
      Robot.elevator.setPoint(ballInfeedFloor);
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
