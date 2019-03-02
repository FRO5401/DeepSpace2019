package frc.robot.subsystems;

import frc.robot.commands.PopHatch;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;


public class HatchMechanism extends Subsystem {

  private Solenoid hatchRelease;
  
  public HatchMechanism(){
    hatchRelease = new Solenoid(RobotMap.HATCH_EXTENDER);
    //creates new instance of the Solenoid class
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new PopHatch());//Links this subsystem with the command PopHatch
  }

  public void openHatch(){
    hatchRelease.set(true);//sets the solenoid to open and release the hatch
  }

  public void closeHatch(){
    hatchRelease.set(false);//sets the solenoid to close and takes in the hatch
  }

  public void reportHatchMechanismSensors(){
    SmartDashboard.putBoolean("Hatch Release LEFT", hatchRelease.get());
    //reports the value of the hatch mechanism, either true or false
  }
}
