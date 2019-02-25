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
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class ElevatorOverride extends Command {

  double dPadInput, leftJoystickOperator;
    
  boolean left, right, theAButton, theXButton, theBButton, overrideButton, elevatorShiftLow, elevatorShiftHigh;

  boolean topLimit;
  boolean bottomLimit;

  boolean overrideFinished;

  //if you haven't done an override on the elevator, you can go do that.
  public ElevatorOverride() {
    requires(Robot.elevator); //uses the elevator subsystem
    overrideFinished = false;
  }

  @Override
  protected void initialize() {
    Robot.elevator.setElevatorNeutralMode(NeutralMode.Brake);
  }

  @Override
  protected void execute() {
  
    leftJoystickOperator = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);

    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3);
    
    topLimit = Robot.elevator.getLimitB(); //gets the height limit for the top
    bottomLimit = Robot.elevator.getLimitT(); //gets the height limit for the bottom


    if(overrideButton) { //if L3 is pressed.
      if((bottomLimit == false) && (topLimit == false)){ //if the bottom and top have no limit, then it can just freely go up and down
        if((leftJoystickOperator > RobotMap.AXIS_THRESHOLD) || (leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD))){
          Robot.elevator.overrideElevator(leftJoystickOperator); 
        }
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      else if((bottomLimit == true) && (topLimit == false)){ // if the bottom has a limit and the top doesn't, then you can just go up.
        if(leftJoystickOperator > RobotMap.AXIS_THRESHOLD){
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      else if((topLimit == true) && (bottomLimit == false)){ //if the bottom doesn't have a limit and the top does then you can just go down.
        if(leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD)){
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      else{
        Robot.elevator.overrideElevator(0);
      }
    }
    else if(!overrideButton) { //if L3 isn't pressed then just, you didn't press it. 
      overrideFinished = true;
    }
  }

  @Override
  protected boolean isFinished() {
    return overrideFinished;
  }

  @Override
  protected void end() {
    Robot.elevator.overrideStopped();
  }

  @Override
  protected void interrupted() {
    Robot.elevator.overrideStopped();
  }
}
