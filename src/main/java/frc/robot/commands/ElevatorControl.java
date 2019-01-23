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

public class ElevatorControl extends Command {

  double dPadInput;
  boolean left;
  boolean right;
  boolean theAButton;

  public ElevatorControl() {
    dPadInput = Robot.oi.xboxDPad(Robot.oi.xboxOperator);
    left = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER_OPERATOR);
    right = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER_OPERATOR);
    theAButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_A_OPERATOR);
    requires(Robot.elevator);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(left && dPadInput == 0){ //UP //Rocket High Ball

    }else if(right && dPadInput == 0){ //UP //Rocket High Hatch

    }else if(left && dPadInput == 270){ //LEFT //Rocket Mid Ball

    }else if(right && dPadInput == 270){ //LEFT //Rocket Mid Hatch

    }else if(left && dPadInput == 90){ //RIGHT //Rocket Low Ball
      
    }else if(right && dPadInput == 90){ //RIGHT //Hatch Feed Station
      
    }else if(left && right && dPadInput == ){ //GAME RESET
      
    }else if(right && dPadInput == 180){ //DOWN //Cargo Ship Ball
      
    }else if(left && dPadInput == 180){ //DOWN //Ball Infeed Station
      
    }else if(){ //END GAME
      
    }else if(theAButton){ //Ball Infeed Floor
      
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
