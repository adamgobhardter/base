package hu.bme.mit.train.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.intThat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSystemTest {

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
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void emergencyBreakTest() {
				
		user.overrideJoystickPosition(50);
		controller.followSpeed();
		
		user.overrideJoystickPosition(-20);
		controller.followSpeed();
		
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}
	
	@Test
	public void tachoGraphTest() {
		
		user.overrideJoystickPosition(5);
		controller.followSpeed();
		sensor.addValuesToTachoGraph();
		
		user.overrideJoystickPosition(6);
		controller.followSpeed();
		sensor.addValuesToTachoGraph();
		
		user.overrideJoystickPosition(7);
		controller.followSpeed();
		sensor.addValuesToTachoGraph();
		
		Table<LocalTime, Integer, Integer> tachoGraph = sensor.getTachoGraph();
		
		assertFalse("There are no values in the tachograph", tachoGraph.isEmpty());
		
		assertEquals(3, tachoGraph.size());
		
		ArrayList<Integer> values = new ArrayList<>();
		ArrayList<Integer> columns = new ArrayList<>();
		
		for(Cell<LocalTime, Integer, Integer> cell : tachoGraph.cellSet()) {
			values.add(cell.getValue());
			columns.add(cell.getColumnKey());
		}
		
		Assert.assertEquals(5, values.get(0).intValue());
		Assert.assertEquals(11, values.get(1).intValue());
		Assert.assertEquals(18, values.get(2).intValue());

		Assert.assertEquals(5, columns.get(0).intValue());
		Assert.assertEquals(6, columns.get(1).intValue());
		Assert.assertEquals(7, columns.get(2).intValue());
	}
}
