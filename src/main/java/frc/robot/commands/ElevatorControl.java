/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ElevatorControl extends Command { 
  
  int controlSelected;

  public ElevatorControl(int buttonSelected) {  // Calls to OI for value of inputted button by operator

    requires(Robot.elevator); // Uses Elevator subsystem 

    controlSelected = buttonSelected;
  }

  @Override
  protected void initialize() { // buttonSelected is set equal to the value assigned by OI to the button the Operator selected
    if(controlSelected == 1){ // Start Button (Operator Control)
      Robot.elevator.elevatorGearShift(true);
    }
    else if(controlSelected == 2){  // Back Button (Operator Control)
      Robot.elevator.elevatorGearShift(false);
    }

    if(controlSelected == 3){ // B Button; Causes the Descension
      Robot.elevator.setPoint(0);
      Robot.elevator.collapseElevator();
    }

    if(controlSelected == 4){ // X Button; Causes Ascension
      Robot.elevator.setPoint(0);
      Robot.elevator.riseElevator();
    }
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
