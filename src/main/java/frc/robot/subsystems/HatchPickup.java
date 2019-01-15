
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
/**
 * Hatch Pickup Subsystem is used to move the hatch arm.
 */
public class HatchPickup extends Subsystem {

  private DoubleSolenoid hatchArm;
  private Solenoid brake; 
  private TalonSRX hatchArmTalon;

  public HatchPickup() {
    hatchArmTalon = new TalonSRX(0); // Change for data inputs
    brake = new Solenoid(0); //Change for data inputs
    hatchArm = new DoubleSolenoid(0, 0); //Change for data inputs
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void hatchUpDown(int armDirection) {
    if (armDirection) { //still requires a condition/value
      hatchArm.set(DoubleSolenoid.Value.kForward);
    }
    else if(armDirection) { //still requires a condition/value
      hatchArm.set(DoubleSolenoid.Value.kReverse);
    }

  }

}
