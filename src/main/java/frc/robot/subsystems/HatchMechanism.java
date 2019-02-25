package frc.robot.subsystems;

import frc.robot.commands.PopHatch;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;


public class HatchMechanism extends Subsystem {

  //For releasing hatches
  private Solenoid hatchRelease;
  
  public HatchMechanism(){
    hatchRelease = new Solenoid(RobotMap.HATCH_EXTENDER);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new PopHatch());
  }

  public void openHatch(){
    hatchRelease.set(true);
  }

  public void closeHatch(){
    hatchRelease.set(false);
  }

  public void reportHatchMechanismSensors(){
    SmartDashboard.putBoolean("Hatch Release LEFT", hatchRelease.get());
  }
}
