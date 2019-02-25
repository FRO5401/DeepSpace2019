package frc.robot.subsystems;

import frc.robot.commands.PopHatch;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;


public class HatchMechanism extends Subsystem {

  private Solenoid hatchRelease; //creates a new solenoid; a solenoid is a single action thing that can open and close with one action
  
  public HatchMechanism(){
    hatchRelease = new Solenoid(RobotMap.HATCH_EXTENDER); //extansiates hatchRelease as a new solenoid
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new PopHatch()); //creates a new instance of the PopHatch command which opens and closes the hatch grabber thinger.
  }

  public void openHatch(){
    hatchRelease.set(true); //sets the solenoid to true, (release hatch)
  }

  public void closeHatch(){
    hatchRelease.set(false); //sets solenoid to false, (grab hatch)
  }

  public void reportHatchMechanismSensors(){ //use smartDashboard to tell the driver what just happened with the hatchRelease mechanism. 
    SmartDashboard.putBoolean("Hatch Release LEFT", hatchRelease.get());
  }
}
