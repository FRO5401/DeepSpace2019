package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc.team5401.robot.RobotMap;
//import org.usfirst.frc.team5401.robot.commands.(put command that calls this subsystem)


public class HatchMechanism extends Subsystem {

  private DoubleSolenoid hatchRelease;
  
  public HatchMechanism(){
    hatchRelease = new DoubleSolenoid();
  }

  public void openHatch(){
    hatchRelease.set(DoubleSolenoid.Value.kForward);
  }

  public void closeHatch(){
    hatchRelease.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
