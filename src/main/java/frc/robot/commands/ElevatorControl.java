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

  //ElevatorControl is created in 'OI' to control the elevaotr shifter
  public ElevatorControl(int buttonSelected) {
    //uses the elevator subsystem object created in 'robot'
    requires(Robot.elevator);

    controlSelected = buttonSelected;
  }

  //Uses
  protected void initialize() {
    //If a certain button is pressed on the operator, 'OI' lists the number that corresponds to that button. 'buttonSelected' is set equal to the value from 'OI'
    
    if(controlSelected == 1){//Xbox Start button
      Robot.elevator.elevatorGearShift(true);
    }
    else if(controlSelected == 2){//Xbox Back button
      Robot.elevator.elevatorGearShift(false);
    }
    if(controlSelected == 3){//Xbox B button
      Robot.elevator.setPoint(0);
      Robot.elevator.collapseElevator();
    }
    if(controlSelected == 4){//Xbox X Button
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
