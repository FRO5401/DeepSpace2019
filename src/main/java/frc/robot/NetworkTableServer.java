package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;

public class NetworkTableServer extends TimedRobot {
    NetworkTableEntry xEntry;
    NetworkTableEntry yEntry;

    public void robotInit() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("data");
        xEntry = table.getEntry("X");
        yEntry = table.getEntry("Y");

    }

    double x = 0;
    double y = 0;

    public void teleopPeriodic() {
        xEntry.setDouble(x);
        yEntry.setDouble(y);
        x += 0.05;
        y += 0.10;
    }

}