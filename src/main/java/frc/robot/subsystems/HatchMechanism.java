package frc.robot.subsystems;

import frc.robot.commands.PopHatch;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;


public class HatchMechanism extends Subsystem {

  private Solenoid hatchRelease;
  
  public HatchMechanism(){
    //instantiates hatchRelease as solenoid
    hatchRelease = new Solenoid(RobotMap.HATCH_EXTENDER);
  }

  @Override
  public void initDefaultCommand() {
    //default command for hatchRelease is PopHatch
    setDefaultCommand(new PopHatch());
  }

  //opens hatch (releases hatch)
  public void openHatch(){
    hatchRelease.set(true);
  }

  //closes hatch (picks up hatch)
  public void closeHatch(){
    hatchRelease.set(false);
  }

  //reports solenoid status in smart dashboard
  public void reportHatchMechanismSensors(){
    SmartDashboard.putBoolean("Hatch Release LEFT", hatchRelease.get());
  }
}
