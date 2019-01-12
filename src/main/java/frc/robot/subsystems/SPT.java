
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class SPT extends Subsystem {

  //motors for the top and bottom of the infeed for the ball
    //left motor control bottom, right motor controls top
  private VictorSP leftMotor;
  private VictorSP rightMotor;
  private TalonSRX sptTalon;

  public SPT() {
    leftMotor = new VictorSP();
    rightMotor = new VictorSP();
    sptTalon = new TalonSRX();
  }

  @Override
  public void initDefaultCommand() {
//   setDefaultCommand();
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void infeedMotorsIn(double leftDesired, double rightDesired) {
    leftMotor.set(leftDesired);
    rightMotor.set(rightDesired);
  }
}
  //following method might not be needed
  public void infeedMotorsOut(double leftDesired, double rightDesired) {
    leftMotor.set(leftDesired);
    rightMotor.set(rightDesired);
  }
