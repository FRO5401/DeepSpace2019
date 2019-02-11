package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;
import frc.robot.commands.PopHatch;


public class HatchMechanism extends Subsystem {

  private Solenoid hatchReleaseRight;
  private Solenoid hatchReleaseLeft;
  
  public HatchMechanism(){
    Solenoid hatchReleaseLeft = new Solenoid(RobotMap.HATCH_EXTENDER_LEFT);
    Solenoid hatchReleaseRight = new Solenoid(RobotMap.HATCH_EXTENDER_RIGHT);
  }

  public void openHatch(){
    hatchReleaseRight.set(true);
    hatchReleaseLeft.set(true);
  }

  public void closeHatch(){
    hatchReleaseRight.set(false);
    hatchReleaseLeft.set(false);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
