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

public class ElevatorOverride extends Command { // Manual Control of Elevator 

  double dPadInput, leftJoystickOperator;
    
  boolean left, right, theAButton, theXButton, theBButton, overrideButton, elevatorShiftLow, elevatorShiftHigh;

  boolean topLimit;
  boolean bottomLimit;

  boolean overrideFinished;

  public ElevatorOverride() { // Override is On
    requires(Robot.elevator); // Uses Elevator Subsystem 
    overrideFinished = false;
  }

  @Override
  protected void initialize() {
    Robot.elevator.setElevatorNeutralMode(NeutralMode.Brake);
  }

  @Override
  protected void execute() {
  
    leftJoystickOperator = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);
    // Y-Axis of Left Joystick on the Operator Controller to Ascend and Descend

    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3);
    // Left Analogue Pressed Down = Override Button
    
    topLimit = Robot.elevator.getLimitB();  // Limit for the highest it can ascend
    bottomLimit = Robot.elevator.getLimitT(); // Limit for the lowest point it can descend to


    if(overrideButton){ // If the Left Analogue is Pressed
      if((bottomLimit == false) && (topLimit == false)){  // If neither limit is reached, then freely move manually until such limits are reached 
        if((leftJoystickOperator > RobotMap.AXIS_THRESHOLD) || (leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD))){
          Robot.elevator.overrideElevator(leftJoystickOperator); 
        }
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      else if((bottomLimit == true) && (topLimit == false)){  // If the bottom limit is reached, you cannot descend further. Only allow for upwards movement
        if(leftJoystickOperator > RobotMap.AXIS_THRESHOLD){
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      else if((topLimit == true) && (bottomLimit == false)){  // If the top limit is reached, you cannot ascend further, so only allow for downwards movement
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
    else if(!overrideButton) {  // When the Analogue is released, then Manual Override ends
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
