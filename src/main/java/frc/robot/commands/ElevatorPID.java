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
  //creates the double to get what the input of the d-pad is based off of the angular values it uses
  double dPadInput;
    
  //'left' and 'right' mean the bumpers
  boolean left, right, theAButton;
 
  //Variables for the setpoint locations
  double rocketHighBall, rocketHighHatch;
  double rocketMidBall, rocketMidHatch;
  double rocketLowBall, rocketLowHatch;
  double cargoShipBall;
  double cargoBallInfeed;
  double ballInfeedFloor; 
  double endGame;

  //limit switch booleans to control elevator
  boolean topLimit;
  boolean bottomLimit;

  public ElevatorPID() {
    requires(Robot.elevator);

      //Values of the setpoints for the locations that the elevator will move to
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

    //gets input from the operator d-pad 
    dPadInput = Robot.oi.xboxDPad(Robot.oi.xboxOperator);
    
    //buttons on the operator controller 
    left = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER);
    right = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER);
    theAButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_A);

    //gets the value of the limit switch to determine if they are pressed or not
    topLimit = Robot.elevator.getLimitT();
    bottomLimit = Robot.elevator.getLimitB();

    //Controlling the movements of the setpoints for the elevator 
    //The left bumper is always used for movement for the cargo, and the right bumper is always used for the movement of the hatches

    //When the top limit is not reached, the left bumper is pressed and d-pad input is up
      //meant so the elevator does not attempt to go to the top again, while it is still there
    if((topLimit != true) && left && dPadInput == 0){ 
      Robot.elevator.setPoint(rocketHighBall);
    }
    //When the right bumper is pressed and d-pad input is up
    else if(right && dPadInput == 0){ 
      Robot.elevator.setPoint(rocketHighHatch);
    }
    //When the left bumper is pressed and d-pad input is left
    else if(left && dPadInput == 270){ 
      Robot.elevator.setPoint(rocketMidBall);
    }
    //When the right bumper is pressed and d-pad input is left
    else if(right && dPadInput == 270){
      Robot.elevator.setPoint(rocketMidHatch);
    }
    //When the left bumper is pressed and d-pad input is right
    else if(left && dPadInput == 90){ 
      Robot.elevator.setPoint(rocketLowBall);
    }
    //When the right bumper is pressed and d-pad is right
    else if(right && dPadInput == 90){ 
      Robot.elevator.setPoint(rocketLowHatch);
    }
    //Not used in game. If both bumpers are pressed, and the d-pad is pressed down on the bottom right, the elevator will move down all the way
    else if(left && right && dPadInput == 135){ 
      Robot.elevator.setPoint(0);
    }
    //When the right bumper is pressed and d-pad is down
    else if(right && dPadInput == 180){ 
      Robot.elevator.setPoint(cargoShipBall);
    }
    //When the left bumper is pressed and d-pad is down
    else if(left && dPadInput == 180){
      Robot.elevator.setPoint(cargoBallInfeed);
    }
    //When the bottom limit is not reached and 'A' button is pressed
      //meant so the elevator does not attempt to go to the bottom again, while it is still there
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
