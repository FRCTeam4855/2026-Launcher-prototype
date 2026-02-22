package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BangBangSubsystem extends SubsystemBase {
    
    public double setpoint = 0;
    public boolean enableBangBang = false;

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

    public void setBangBangSetpoint(double speed) {
        setpoint = speed;
        System.out.printf("setpoint set to %f", setpoint);
    }
    
    public void BangBangControl(SparkFlex controller, RelativeEncoder encoder) {
        /*if(stop)  //being asked to stop the flywheel
        controller.set(0);
        else {*/
        if(enableBangBang) {
            double encoderVelocity = encoder.getVelocity();
            //if(encoder.getVelocity() < setpoint) //If the flywheel speed is less 
            if(encoderVelocity >= 0 && encoderVelocity < setpoint) //If the flywheel speed is less than the desired speed
                controller.set(1);  //Give the flywheel full power
            else
                controller.set(0);  //Cut the power and let it coast
        }
        
    }
}
