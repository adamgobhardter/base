package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.sensor.TrainSensorImpl;

public class TrainSensorTest {
     
	TrainController controller;
	

    TrainSensorImpl sensor;
	
    
    TrainUser user;
	
	@Before
	public void before() {
		controller = mock(TrainController.class); 
        user = mock(TrainUser.class);
        sensor = new TrainSensorImpl(controller, user); 
        
		sensor.overrideSpeedLimit(50);
	}

    @Test
    public void SpeedLimitSmallerThanZero() {
        sensor.overrideSpeedLimit(-2);
        verify(user, times(1)).getAlarmState();
        }

    @Test
    public void SpeedLimitBiggerThan500() {
        sensor.overrideSpeedLimit(505);
        verify(user, times(1)).getAlarmState();
        }
    @Test
    public void SpeedLimitBiggerThan500SmallerThanZero() {
        sensor.overrideSpeedLimit(250);
        verify(user, times(1)).getAlarmState();
        }
    @Test
    public void SpeedLimitSmallerThan50Percentage() {
        when(controller.getReferenceSpeed()).thenReturn(400); 
        sensor.overrideSpeedLimit(150);
        verify(user, times(1)).getAlarmState(); 
    }
}
