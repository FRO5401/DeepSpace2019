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

  public ElevatorControl(int buttonSelected) { //calls to OI for the value of the button that is selected from the operator controller. si senor. 
    requires(Robot.elevator); //uses the elevator subsytem to run the command

    controlSelected = buttonSelected; //sets controlSelected to the value of buttonSelected.
  }

  @Override
  
  protected void initialize() {
    //if the start button is pressed on the operator controller, then you can shift the elevator gear.
    if(controlSelected == 1){
      Robot.elevator.elevatorGearShift(true);
    }
    //if the back button is pressed on the operator controller, then you can unshift the elevator gear. 
    else if(controlSelected == 2){
      Robot.elevator.elevatorGearShift(false);
    }

    //if the B button is pressed on the operator controller, the elevator collapses or goes down
    if(controlSelected == 3){
      Robot.elevator.setPoint(0);
      Robot.elevator.collapseElevator();
    }

    //if the X button is pressed on the operator controller, the elevator rises or goes up
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
