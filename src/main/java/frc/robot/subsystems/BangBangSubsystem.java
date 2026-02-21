package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BangBangSubsystem extends SubsystemBase {
    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    private static BangBangSubsystem mInstance;
    public static BangBangSubsystem getInstance() {
      if (mInstance == null) {
        mInstance = new BangBangSubsystem();
      }
      return mInstance;
    }
    
    public BangBangSubsystem() {
    }

    public void BangBangControl(SparkFlex controller, RelativeEncoder encoder, double speed, boolean stop) {
        System.out.printf("Entered BangBangControl\n");
        if(stop)  //being asked to stop the flywheel
        controller.set(0);
        else {
        if(encoder.getVelocity() < speed) //If the flywheel speed is less than the desired speed
            controller.set(1);  //Give the flywheel full power
        else
            controller.set(0);  //Cut the power and let it coast
        }
    }
}
