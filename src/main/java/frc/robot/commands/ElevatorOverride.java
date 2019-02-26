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

  //XBox Button Controls
  double dPadInput, leftJoystickOperator;
  boolean left, right, theAButton, theXButton, theBButton, overrideButton, elevatorShiftLow, elevatorShiftHigh;

  //Limit switches to limit the movement
  boolean topLimit;
  boolean bottomLimit;

  boolean overrideFinished;

  public ElevatorOverride() {
    requires(Robot.elevator);
    overrideFinished = false;
  }

  @Override
  protected void initialize() {
    //Makes sure the elevator is stopped with a force
    Robot.elevator.setElevatorNeutralMode(NeutralMode.Brake);
  }

  @Override
  protected void execute() {

    //Left joystick of the XBox Controller
    leftJoystickOperator = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);

    //Override button is the L3 button on the XBox Controller
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3);
    
    //Limit switches
    topLimit = Robot.elevator.getLimitB();
    bottomLimit = Robot.elevator.getLimitT();

    //If the override button is pressed,
    if(overrideButton){
      //If both limit switches are off,
      if((bottomLimit == false) && (topLimit == false)){
        //and the left joystick is farther than the axis threshold in the y-coordinate,
        if((leftJoystickOperator > RobotMap.AXIS_THRESHOLD) || (leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD))){
          //Override the elevator to the left joystick
          Robot.elevator.overrideElevator(leftJoystickOperator); 
        }
        else{
          //Do not override the elevator
          Robot.elevator.overrideElevator(0);
        }
      }
      //If the bottom limit switch is on...
      else if((bottomLimit == true) && (topLimit == false)){
        //and the left joystick is greater than the axis threshold upwards,
        if(leftJoystickOperator > RobotMap.AXIS_THRESHOLD){
          //Override the elevator with the left joystick
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
        else{
          //Do not override the elevator
          Robot.elevator.overrideElevator(0);
        }
      }
      //If the top limit switch is on
      else if((topLimit == true) && (bottomLimit == false)){
        //and the left joystick is greater than the axis threshold downwards,
        if(leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD)){
          //Override the elevator with the right joystick
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
        else{
          //Do not override the elevator
          Robot.elevator.overrideElevator(0);
        }
      }
      else{
        //Do not override the elevator
        Robot.elevator.overrideElevator(0);
      }
    }
    else if(!overrideButton) {
      //Override is indicated to have been finished
      overrideFinished = true;
    }
  }

  //Returns whether or not the override is finished
  @Override
  protected boolean isFinished() {
    return overrideFinished;
  }

  //If the end() or interrupted() method is called, the robot will not get overridden
  @Override
  protected void end() {
    Robot.elevator.overrideStopped();
  }

  @Override
  protected void interrupted() {
    Robot.elevator.overrideStopped();
  }
}
