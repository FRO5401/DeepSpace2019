package frc.robot.subsystems;

import frc.robot.commands.PopHatch;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;


public class HatchMechanism extends Subsystem {

  private Solenoid hatchReleaseRight;
  private Solenoid hatchReleaseLeft;
  
  public HatchMechanism(){
    hatchReleaseLeft = new Solenoid(RobotMap.HATCH_EXTENDER_LEFT);
    hatchReleaseRight = new Solenoid(RobotMap.HATCH_EXTENDER_RIGHT);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new PopHatch());
  }

    //OPENS the hatch.
  public void openHatch(){
    hatchReleaseLeft.set(true);
    hatchReleaseRight.set(true);
  }

    //CLOSES the hatch.
  public void closeHatch(){
    hatchReleaseLeft.set(false);
    hatchReleaseRight.set(false);
  }

  public void reportHatchMechanismSensors(){
    SmartDashboard.putBoolean("Hatch Release LEFT", hatchReleaseLeft.get());
    SmartDashboard.putBoolean("Hatch Release RIGHT", hatchReleaseRight.get());
  }
}
