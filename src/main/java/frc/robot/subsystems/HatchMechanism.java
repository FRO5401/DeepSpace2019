package frc.robot.subsystems;

import frc.robot.commands.PopHatch;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;


public class HatchMechanism extends Subsystem {

  private Solenoid hatchRelease;
  
  public HatchMechanism(){  // Instantiate Variable
    hatchRelease = new Solenoid(RobotMap.HATCH_EXTENDER);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new PopHatch());
  }

  public void openHatch(){  // If true, open hatch
    hatchRelease.set(true);
  }

  public void closeHatch(){ // If false, close hatch
    hatchRelease.set(false);
  }

  public void reportHatchMechanismSensors(){
    SmartDashboard.putBoolean("Hatch Release LEFT", hatchRelease.get());
  }
}
