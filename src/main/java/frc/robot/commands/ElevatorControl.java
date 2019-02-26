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
  
  //Control that is selected
  int controlSelected;

  public ElevatorControl(int buttonSelected) {
    requires(Robot.elevator);

    controlSelected = buttonSelected;
  }

  @Override
  protected void initialize() {
    if(controlSelected == 1){
      //Add gear shift to the elevator
      Robot.elevator.elevatorGearShift(true);
    }
    else if(controlSelected == 2){
      //Remove gear shift from the elevator
      Robot.elevator.elevatorGearShift(false);
    }

    if(controlSelected == 3){
      //Reset robot and collapse the elevator
      Robot.elevator.setPoint(0);
      Robot.elevator.collapseElevator();
    }

    if(controlSelected == 4){
      //Reset robot and raise the elevator
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
