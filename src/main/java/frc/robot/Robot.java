/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.autonomous.*;
import frc.robot.commands.VisionDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static DriveBase drivebase;
  public static CarriageInfeed carriageinfeed;
  public static Elevator elevator;
  public static HatchMechanism hatchmechanism;
  public static CompressorSubsystem compressorsubsystem;
  public static VisionAuto visionAuto;
  
  //OI is always last.
  public static OI oi;

  Command m_autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    drivebase = new DriveBase();

    carriageinfeed = new CarriageInfeed();
    elevator = new Elevator();
    hatchmechanism = new HatchMechanism();
    compressorsubsystem = new CompressorSubsystem();
    visionAuto = new VisionAuto();
    
    //OI is always last.
    oi = new OI();
    // chooser.addOption("My Auto", new MyAutoCommand());
    chooser.setDefaultOption("DoNothing", new DoNothing());
    chooser.addOption("Drive Straight", new DriveStraight());
    chooser.addOption("Turn Turn Turn", new TurnTurnTurn());
    chooser.addOption("Down and Back", new DownAndBack());
    chooser.addOption("LeftFrontHatch1", new LeftFrontHatch1());
    chooser.addOption("LeftFrontHatch2", new LeftFrontHatch2());
    chooser.addOption("RightFrontHatch1", new RightFrontHatch1());
    chooser.addOption("RightFrontHatch2", new RightFrontHatch2());
    chooser.addOption("RightRocketHatchLow", new RightRocketHatchLow());

    SmartDashboard.putData("Auto mode", chooser);
    

      //TODO: Reset these where deemed necessary.
      //Set to reset vals when robot turns on, might be more useful in
      //either auto, disabled, or teleop init. 
    Robot.drivebase.resetEncoders();
    Robot.drivebase.resetGyro();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
      //DriveBase Reporting
    Robot.drivebase.reportDriveBaseSensors();
    Robot.elevator.reportElevatorSensors();
    Robot.carriageinfeed.reportCarriageInfeedSensors();
    Robot.hatchmechanism.reportHatchMechanismSensors();
    Robot.compressorsubsystem.reportCompressorStatus();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    Robot.hatchmechanism.openHatch();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {;
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
