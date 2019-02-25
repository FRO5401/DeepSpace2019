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

  //sets controlSelected to button pressed from OI
  //on operator controller, 1 is start, 2 is back, 3 is B, 4 is X
  public ElevatorControl(int buttonSelected) {
    requires(Robot.elevator);

    controlSelected = buttonSelected;
  }

  @Override
  protected void initialize() {
    //high gear
    if(controlSelected == 1){
      Robot.elevator.elevatorGearShift(true);
    }
    //low gear
    else if(controlSelected == 2){
      Robot.elevator.elevatorGearShift(false);
    }
    //collapses elevator
    if(controlSelected == 3){
      Robot.elevator.setPoint(0);
      Robot.elevator.collapseElevator();
    }
    //rises elevator
    if(controlSelected == 4){
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
