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

    dPadInput = Robot.oi.xboxDPad(Robot.oi.xboxOperator);
      
    
    left = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER);
    right = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER);
    theAButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_A);

    topLimit = Robot.elevator.getLimitT();
    bottomLimit = Robot.elevator.getLimitB();

    //topLimit is false and left is pressed and the dPad is pressed up, set the point to rocketHighBall(does the whole 3rd level ball thing)
    if((topLimit != true) && left && dPadInput == 0){ 
      Robot.elevator.setPoint(rocketHighBall);
    }
    //if right is pressed and the dPad is pressed up, set the point to rocketHighHatch(does the whole 3rd level hatch thing)
    else if(right && dPadInput == 0){ 
      Robot.elevator.setPoint(rocketHighHatch);
    }
    //if left is pressed and the dPad is pressed left, set the point to rocketMidBall(does the whole 2nd level ball thing)
    else if(left && dPadInput == 270){ 
      Robot.elevator.setPoint(rocketMidBall);
    }
    //if right is pressed and dPad is pressed left, set the point to rocketMidHatch(does the whole 2nd level hatch thing)
    else if(right && dPadInput == 270){
      Robot.elevator.setPoint(rocketMidHatch);
    }
    //if left is pressed and the dPad is pressed right, set the point to rocketLowBall(does the whole 1st level ball thing)
    else if(left && dPadInput == 90){ 
      Robot.elevator.setPoint(rocketLowBall);
    }
    //if right is pressed and the dPad is pressed right, set the point to rocketLowHatch(does the whole 1st level hatch thing)
    else if(right && dPadInput == 90){ 
      Robot.elevator.setPoint(rocketLowHatch);
    }
    //if left and right are pressed alongside the dPad being pressed between right and down, set the point to 0 for pit purposes
    else if(left && right && dPadInput == 135){ 
      Robot.elevator.setPoint(0);
    }
    //if right is pressed and the dPad is pressed down, set the point to cargoShipBall(does the whole place the ball in the cargo ship thinger)
    else if(right && dPadInput == 180){ 
      Robot.elevator.setPoint(cargoShipBall);
    }
    //if left is pressed and the dPad is pressed down, set the point to cargoBallInFeed(does the whole grab the ball from the bottom thing)
    else if(left && dPadInput == 180){
      Robot.elevator.setPoint(cargoBallInfeed);
    }
    //if the bottom limit is not true and theAButton(floor button) is pressed, set the point to ballInfeedFloor(does the whole pick up the ball from the floor thing)
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
