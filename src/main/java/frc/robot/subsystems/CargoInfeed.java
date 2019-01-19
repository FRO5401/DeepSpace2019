/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.FeedCargo;

/**
 * Add your docs here.
 */
public class CargoInfeed extends Subsystem {
  VictorSP cargoFeederRight = new VictorSP(RobotMap.CARGO_INFEED_RIGHT_MOTOR);
  VictorSP cargoFeederLeft = new VictorSP(RobotMap.CARGO_INFEED_LEFT_MOTOR);
  DigitalInput lightSensor = new DigitalInput(RobotMap.LIGHT_SENSOR);

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new FeedCargo());
  }

  public void feedCargo(int feedDirection){
    cargoFeederRight.set(feedDirection * RobotMap.CARGO_FEED_SPEED);
    cargoFeederLeft.set(feedDirection * RobotMap.CARGO_FEED_SPEED);
  }

  public boolean getLightSensor(){
    return lightSensor.get();
  }
}
