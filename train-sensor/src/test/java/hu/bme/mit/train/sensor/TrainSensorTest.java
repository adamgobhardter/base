package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.interfaces.TrainSystem;
public class TrainSensorTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();

		sensor.overrideSpeedLimit(50);
	}

    @Test
    public void SpeedLimitSmallerThanZero() {
        sensor.overrideSpeedLimit(-2);
        Assert.assertTrue(user.getAlarmState()); 
    }

    @Test
    public void SpeedLimitBiggerThan500() {
        sensor.overrideSpeedLimit(505);
        Assert.assertTrue(user.getAlarmState()); 
    }
    @Test
    public void SpeedLimitBiggerThan500SmallerThanZero() {
        sensor.overrideSpeedLimit(250);
        Assert.assertFalse(user.getAlarmState()); 
    }
    @Test
    public void SpeedLimitSmallerThan50Percentage() {
        controller.setSpeedLimit(400); 
        sensor.overrideSpeedLimit(150);
        Assert.assertTrue(user.getAlarmState()); 
    }
}
