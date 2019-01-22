package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;
import frc.robot.commands.PopHatch;


public class HatchMechanism extends Subsystem {

  private Solenoid hatchRelease;
  
  public HatchMechanism(){
    hatchRelease = new Solenoid();
  }

  public void openHatch(){
    hatchRelease.set(true);
  }

  public void closeHatch(){
    hatchRelease.set(false);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
