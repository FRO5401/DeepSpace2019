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

//This command is used for manual control of moving the elevator up and down
public class ElevatorOverride extends Command {


  double dPadInput, leftJoystickOperator;
  
  //booleans for controlling the elevaotr manually. Tell the robot what is pressed and control it by using methods if they are true or false
    //boolean left and right are not used in this class
  boolean left, right, theAButton, theXButton, theBButton, overrideButton, elevatorShiftLow, elevatorShiftHigh;

  boolean topLimit;
  boolean bottomLimit;

  boolean overrideFinished;

  //Constructor method, uses the elevator subsystem and makes sure that the override is not finished while it is in use
  public ElevatorOverride() {
    requires(Robot.elevator);
    overrideFinished = false;
  }

  @Override
  protected void initialize() {
    Robot.elevator.setElevatorNeutralMode(NeutralMode.Brake);
  }

  @Override
  protected void execute() {
    //Buttons for controlling physical parts of the elevator
      //Uses specifically the 'Y' axis of the left joystick because this is used to move the elevator up and down
    leftJoystickOperator = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);

    //Works with the 'leftJoystickOperator' to control the elevator
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3);
    
    //Gets the limits from the elevator subsystem to determine when the elevator should stop movement
    topLimit = Robot.elevator.getLimitB();
    bottomLimit = Robot.elevator.getLimitT();

    //Elevator logic for movement
    if(overrideButton){//When the override is activated. Allows the leftJoystickOperator to be used
      if((bottomLimit == false) && (topLimit == false)){
        //If neither limit is reached, and the left joystick's 'Y' axis value is past the threshold, move until no longer true 
        if((leftJoystickOperator > RobotMap.AXIS_THRESHOLD) || (leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD))){
          Robot.elevator.overrideElevator(leftJoystickOperator); 
        }
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      else if((bottomLimit == true) && (topLimit == false)){
        //If the bottom limit is true, only allow for the elevator to move up
          //The code will only allow for positive 'Y' axis values
        if(leftJoystickOperator > RobotMap.AXIS_THRESHOLD){
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      else if((topLimit == true) && (bottomLimit == false)){
        //If the top limit is true, only allow for the elevator to move down
          //The code will only allow for negative 'Y' axis values
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
    //When the user lets go of the 'L3' button, override will stop 
    else if(!overrideButton) {
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
