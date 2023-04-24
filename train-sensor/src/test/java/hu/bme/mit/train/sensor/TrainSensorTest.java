package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import hu.bme.mit.train.controller.TrainControllerImpl;
import hu.bme.mit.train.sensor.TrainSensorImpl;
import hu.bme.mit.train.user.TrainUserImpl;


public class TrainSensorTest {
    @Mock 
	TrainControllerImpl controller;
	

    TrainSensor sensor;
	
    @InjectMocks
    TrainUserImpl user;
	
	@Before
	public void before() {
		user = new TrainUserImpl(controller); 
        sensor = new TrainSensorImpl(controller, user);
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
